package uniamerica.centralDeAjuda.Services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public AlunoDTO findById(Long id) {
        Aluno aluno = alunoRepository.findById(id).orElse(null);
        return convertToDTO(aluno);
    }

    public AlunoDTO cadastrar(AlunoDTO alunoDTO) {
        Aluno aluno = new Aluno();
        BeanUtils.copyProperties(alunoDTO, aluno);
        aluno = alunoRepository.save(aluno);
        return convertToDTO(aluno);
    }

    public AlunoDTO editar(Long id, AlunoDTO alunoDTO) {
        if (alunoRepository.existsById(id)) {
            Aluno aluno = new Aluno();
            BeanUtils.copyProperties(alunoDTO, aluno);
            aluno.setId(id);
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
