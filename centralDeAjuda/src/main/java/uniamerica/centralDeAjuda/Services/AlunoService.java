package uniamerica.centralDeAjuda.Services;

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
    private AlunoRepository repository;

    public List<AlunoDTO> listar() {
        List<Aluno> alunos = repository.findAll();
        return alunos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public AlunoDTO findById(Long id) {
        Aluno aluno = repository.findById(id).orElse(null);
        return aluno != null ? convertToDTO(aluno) : null;
    }

    public AlunoDTO cadastrar(AlunoDTO alunoDTO) {
        Aluno aluno = convertToEntity(alunoDTO);
        aluno = repository.save(aluno);
        return convertToDTO(aluno);
    }

    public AlunoDTO editar(Long id, AlunoDTO alunoDTO) {
        Aluno aluno = repository.findById(id).orElse(null);

        if (aluno != null) {
            updateEntityFromDTO(aluno, alunoDTO);
            aluno = repository.save(aluno);
            return convertToDTO(aluno);
        }

        return null;
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    private AlunoDTO convertToDTO(Aluno aluno) {
        return new AlunoDTO(aluno.getNome(), aluno.getRA());
    }

    private Aluno convertToEntity(AlunoDTO alunoDTO) {
        Aluno aluno = new Aluno();
        aluno.setNome(alunoDTO.getNome());
        aluno.setRA(alunoDTO.getRA());
        return aluno;
    }

    private void updateEntityFromDTO(Aluno aluno, AlunoDTO alunoDTO) {
        aluno.setNome(alunoDTO.getNome());
        aluno.setRA(alunoDTO.getRA());
    }
}
