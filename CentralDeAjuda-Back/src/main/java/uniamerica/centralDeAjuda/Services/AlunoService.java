package uniamerica.centralDeAjuda.Services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import uniamerica.centralDeAjuda.DTO.AlunoDTO;
import uniamerica.centralDeAjuda.DTO.MensagemDTO;
import uniamerica.centralDeAjuda.Entity.Aluno;
import uniamerica.centralDeAjuda.Entity.Auditoria;
import uniamerica.centralDeAjuda.Entity.Ticket;
import uniamerica.centralDeAjuda.Repository.AlunoRepository;
import uniamerica.centralDeAjuda.Repository.AuditoriaRepository;
import uniamerica.centralDeAjuda.Repository.TicketRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlunoService {
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private AuditoriaRepository auditoriaRepository;
    @Autowired
    private TicketRepository ticketRepository;

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

    public MensagemDTO cadastrarAluno(AlunoDTO alunoDTO, String userCreacao) throws Exception {
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

        Auditoria auditoria = new Auditoria();
        auditoria.setAluno(aluno);
        auditoria.setDataHoraCriacao(new Timestamp(System.currentTimeMillis()));
        auditoria.setUserCriacao(userCreacao);
        auditoriaRepository.save(auditoria);

        return new MensagemDTO("Aluno cadastrado com sucesso!", HttpStatus.CREATED);
    }

    public MensagemDTO editarAluno(Long id, AlunoDTO alunoDTO, String userAlteracao) throws Exception {
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

        Auditoria auditoria = new Auditoria();
        auditoria.setAluno(aluno);
        auditoria.setDataHoraAlteracao(new Timestamp(System.currentTimeMillis()));
        auditoria.setUserAlteracao(userAlteracao);
        auditoriaRepository.save(auditoria);

        return new MensagemDTO("Aluno atualizado com sucesso!", HttpStatus.CREATED);
    }

    public MensagemDTO deletar(Long id, String userExclusao) throws Exception{
        Aluno alunoBanco = alunoRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Aluno com ID "+id+" nao existe!"));

        List<Ticket> alunoTicketsAtivos = ticketRepository.findTicketsAbertosPorAluno(alunoBanco);

        if (!alunoTicketsAtivos.isEmpty()){
            throw new Exception("Não é possível excluir esse aluno, pois existem ticket ativos associados a ele.");
        } else {
            desativarAluno(alunoBanco);

            Auditoria auditoria = new Auditoria();
            auditoria.setAluno(alunoBanco);
            auditoria.setDataHoraExclusao(new Timestamp(System.currentTimeMillis()));
            auditoria.setUserExclusao(userExclusao);
            auditoriaRepository.save(auditoria);
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