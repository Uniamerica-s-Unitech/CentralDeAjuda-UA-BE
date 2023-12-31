package uniamerica.centralDeAjuda.Services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import uniamerica.centralDeAjuda.DTO.AlunoDTO;
import uniamerica.centralDeAjuda.DTO.MensagemDTO;
import uniamerica.centralDeAjuda.Entity.Aluno;
import uniamerica.centralDeAjuda.Entity.Ticket;
import uniamerica.centralDeAjuda.Repository.AlunoRepository;
import uniamerica.centralDeAjuda.Repository.TicketRepository;

import java.util.List;

@Service
public class AlunoService {
    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private TicketRepository ticketRepository;
    private Aluno aluno;

    public AlunoDTO findAlunoById(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado!"));
        return alunoToDTO(aluno);
    }

    public List<AlunoDTO> listar() {
        return alunoRepository.findAlunoByAtivo().stream().map(this::alunoToDTO).toList();
    }

    public List<AlunoDTO> listarSemVinculo()  {
        return alunoRepository.findAlunoByAtivoSemVinculo().stream().map(this::alunoToDTO).toList();
    }

    public MensagemDTO cadastrarAluno(AlunoDTO alunoDTO) throws Exception {
        Aluno aluno = toAluno(alunoDTO);

        Assert.notNull(aluno.getNome(),"Nome inválido!");

        Assert.notNull(aluno.getRa(),"RA inválido!");
        if (!aluno.getRa().matches("\\d{6}")) {
            throw new Exception("Formato do RA inválido. Deve conter 6 dígitos numéricos!");
        }

        if (!alunoRepository.findByRA(aluno.getRa()).isEmpty()){
            throw new Exception("Esse RA ja existe!");
        }

        alunoRepository.save(aluno);
        return new MensagemDTO("Aluno cadastrado com sucesso!", HttpStatus.CREATED);
    }

    public MensagemDTO editarAluno(Long id, AlunoDTO alunoDTO) throws Exception {
        Aluno aluno = toAluno(alunoDTO);

        Assert.notNull(aluno.getNome(), "Nome inválido!");

        Assert.notNull(aluno.getRa(), "RA inválido!");
        if (!aluno.getRa().matches("\\d{6}")) {
            throw new Exception("Formato do RA inválido. Deve conter 6 dígitos numéricos!");
        }

        if (!alunoRepository.findByRaEditar(aluno.getRa(),id).isEmpty()){
            throw new Exception("Esse RA ja existe!");
        }

        alunoRepository.save(aluno);
        return new MensagemDTO("Aluno atualizado com sucesso!", HttpStatus.CREATED);
    }

    public MensagemDTO deletar(Long id) throws Exception{
        Aluno alunoBanco = alunoRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Aluno com ID "+id+" nao existe!"));

        List<Ticket> alunoTicketsAtivos = ticketRepository.findTicketsAbertosPorAluno(alunoBanco);

        if (!alunoTicketsAtivos.isEmpty()){
            throw new Exception("Não é possível excluir esse aluno, pois existem ticket ativos associados a ele.");
        } else {
            desativarAluno(alunoBanco);
        }
        return new MensagemDTO("Aluno deletado com sucesso!", HttpStatus.CREATED);
    }

    private void desativarAluno(Aluno aluno) {
        aluno.setAtivo(false);
        alunoRepository.save(aluno);
    }

    public AlunoDTO alunoToDTO(Aluno aluno){
    AlunoDTO alunoDTO = new AlunoDTO();

    alunoDTO.setId(aluno.getId());
    alunoDTO.setNome(aluno.getNome());
    alunoDTO.setRa(aluno.getRa());

    return alunoDTO;
    }

    public Aluno toAluno(AlunoDTO alunoDTO){
        Aluno novoAluno = new Aluno();

        novoAluno.setId(alunoDTO.getId());
        novoAluno.setNome(alunoDTO.getNome());
        novoAluno.setRa(alunoDTO.getRa());

        return novoAluno;
    }
}