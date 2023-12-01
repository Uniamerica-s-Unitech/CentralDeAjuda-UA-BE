//AuthenticationService.java
package uniamerica.centralDeAjuda.Services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uniamerica.centralDeAjuda.Config.JwtServiceGenerator;
import uniamerica.centralDeAjuda.DTO.LoginDTO;
import uniamerica.centralDeAjuda.DTO.MensagemDTO;
import uniamerica.centralDeAjuda.DTO.UserDTO;
import uniamerica.centralDeAjuda.Entity.User;
import uniamerica.centralDeAjuda.Repository.LoginRepository;

import java.util.List;


@Service
public class LoginServices {

    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private JwtServiceGenerator jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public UserDTO logar(LoginDTO loginDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(),
                        loginDTO.getPassword()
                )
        );
        User user = loginRepository.findByUsername(loginDTO.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        return toUserDTO(user, jwtToken);
    }

    public List<UserDTO> listar() {
        return loginRepository.findUserByAtivo().stream().map(this::userToDTO).toList();
    }

    public MensagemDTO cadastrarUser(UserDTO userDTO) {
        User user = toUser(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        loginRepository.save(user);
        return new MensagemDTO("User cadastrado com sucesso!", HttpStatus.CREATED);
    }
    public MensagemDTO editarUser(Long id, UserDTO userDTO) {
        User user = toUser(userDTO);
        String senha= loginRepository.findSenhaById(user.getId());
        user.setPassword(senha);
        loginRepository.save(user);
        return new MensagemDTO("User atualizado com sucesso!", HttpStatus.CREATED);
    }

    public MensagemDTO deletar(Long id) {
        User userBanco = loginRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User com ID " + id + " não existe!"));
        desativarUser(userBanco);

        return new MensagemDTO("User deletado com sucesso!", HttpStatus.CREATED);
    }

    private void desativarUser(User user) {
        user.setAtivo(false);
        loginRepository.save(user);
    }

    public UserDTO userToDTO(User user){
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setAtivo(user.getAtivo());
        userDTO.setUsername(user.getUsername());
        // userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());

        return userDTO;
    }
    public User toUser(UserDTO userDTO){
        User novoUser = new User();

        novoUser.setId(userDTO.getId());
        novoUser.setAtivo(userDTO.getAtivo());
        novoUser.setUsername(userDTO.getUsername());
        novoUser.setPassword(userDTO.getPassword());
        novoUser.setRole(userDTO.getRole());

        return novoUser;
    }


    private UserDTO toUserDTO(User user, String token) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setRole(user.getRole());
        userDTO.setPassword(token);
        userDTO.setUsername(user.getUsername());
        return userDTO;
    }

}