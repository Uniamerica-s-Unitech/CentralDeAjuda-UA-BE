package uniamerica.centralDeAjuda.Services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import uniamerica.centralDeAjuda.DTO.AlunoDTO;
import uniamerica.centralDeAjuda.Entity.Aluno;
import uniamerica.centralDeAjuda.Repository.AlunoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlunoService {
    @Autowired
    private AlunoRepository alunoRepository;

    public List<AlunoDTO> listar() {
        List<Aluno> alunos = alunoRepository.findAll();
        return alunos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AlunoDTO cadastrar(AlunoDTO alunoDTO) {
        Aluno aluno = new Aluno();
        BeanUtils.copyProperties(alunoDTO, aluno);

        Assert.notNull(aluno.getNome(),"Nome invalido");
        if (!alunoRepository.findByNome(aluno.getNome()).isEmpty()){
            throw new IllegalArgumentException("Esse nome ja existe");
        }

        /*Assert.notNull(aluno.getRA(),"RA invalido");
        if (!aluno.getRA().matches("\\d{6}")) {
            throw new IllegalArgumentException("Formato do RA inválido. Deve conter 6 dígitos numéricos.");
        }*/

        if (!alunoRepository.findByRA(aluno.getRA()).isEmpty()){
            throw new IllegalArgumentException("Esse RA ja existe");
        }

        aluno = alunoRepository.save(aluno);
        return convertToDTO(aluno);
    }

    public AlunoDTO editar(Long id, AlunoDTO alunoDTO) {
        if (alunoRepository.existsById(id)) {
            Aluno aluno = new Aluno();
            BeanUtils.copyProperties(alunoDTO, aluno);

            Assert.notNull(aluno.getNome(),"Nome invalido");
            if (!alunoRepository.findByNome(aluno.getNome()).isEmpty()){
                throw new IllegalArgumentException("Esse nome ja existe");
            }

            Assert.notNull(aluno.getRA(),"RA invalido");
            if (!aluno.getRA().matches("\\d{6}")) {
                throw new IllegalArgumentException("Formato do RA inválido. Deve conter 6 dígitos numéricos.");
            }

            if (!alunoRepository.findByRA(aluno.getRA()).isEmpty()){
                throw new IllegalArgumentException("Esse RA ja existe");
            }

            aluno = alunoRepository.save(aluno);
            return convertToDTO(aluno);
        }
        return null;
    }

    public void deletar(Long id) {
        alunoRepository.deleteById(id);
    }

    private AlunoDTO convertToDTO(Aluno aluno) {
        AlunoDTO alunoDTO = new AlunoDTO();
        BeanUtils.copyProperties(aluno, alunoDTO);
        return alunoDTO;
    }
}
