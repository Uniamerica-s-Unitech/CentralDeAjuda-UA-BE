package uniamerica.centralDeAjuda.Services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import uniamerica.centralDeAjuda.DTO.*;
import uniamerica.centralDeAjuda.Entity.Aluno;
import uniamerica.centralDeAjuda.Entity.Auditoria;
import uniamerica.centralDeAjuda.Entity.Notebook;
import uniamerica.centralDeAjuda.Entity.Ticket;
import uniamerica.centralDeAjuda.Repository.AlunoRepository;
import uniamerica.centralDeAjuda.Repository.AuditoriaRepository;
import uniamerica.centralDeAjuda.Repository.NotebookRepository;
import uniamerica.centralDeAjuda.Repository.TicketRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private NotebookRepository notebookRepository;
    @Autowired
    private AuditoriaRepository auditoriaRepository;

    public TicketDTO findTicketById(Long id) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Ticket não encontrado!"));
        return ticketToDTO(ticket);
    }

    public List<TicketDTO> buscarTicketsAbertos() {
        return ticketRepository.findTicketsAbertos().stream().map(this::ticketToDTO).toList();
    }

    public List<TicketDTO> buscarHistoricoPorDataDevolucao() {
        return ticketRepository.findHistoricoByDataDevolucao().stream().map(this::ticketToDTO).toList();
    }

    public MensagemDTO cadastrarTicket(TicketDTO ticketDTO, String userCreacao) throws Exception {
        Ticket ticket = toTicket(ticketDTO);

        Assert.notNull(ticket.getAlunoId(), "Aluno inválido!");

        Assert.notNull(ticket.getNotebookId(), "Notebook inválido!");

        Assert.notNull(ticket.getDataEntrega(), "Data de entrega inválida!");

        List<Ticket> alunoTicketsAtivos = ticketRepository.findTicketsAbertosPorAluno(ticket.getAlunoId());

        List<Ticket> notebookTicketsAtivos = ticketRepository.findTicketsAbertosPorNotebook(ticket.getNotebookId());

        if (!alunoTicketsAtivos.isEmpty()) {
            throw new Exception("O aluno possui um tickey ativo");
        } else if (!notebookTicketsAtivos.isEmpty()) {
            throw new Exception("O notebook possui um tickey ativo");
        } else {
            ticketRepository.save(ticket);

            Auditoria auditoria = new Auditoria();
            auditoria.setTicket(ticket);
            auditoria.setDataHoraCriacao(new Timestamp(System.currentTimeMillis()));
            auditoria.setUserCriacao(userCreacao);
            auditoriaRepository.save(auditoria);

            return new MensagemDTO("Ticket cadastrado com sucesso!", HttpStatus.CREATED);
        }
    }

    public MensagemDTO editarTicket(Long id, TicketDTO ticketDTO,String userAlteracao ,String userFinalizacao) {
        Ticket ticket = toTicket(ticketDTO);

        Assert.notNull(ticket.getAlunoId(), "Aluno inválido!");

        Assert.notNull(ticket.getNotebookId(), "Notebook inválido!");

        Assert.notNull(ticket.getDataEntrega(), "Data de entrega inválida!");
        ticketRepository.save(ticket);
        if (ticket.getDataDevolucao() == null) {

            Auditoria auditoria = new Auditoria();
            auditoria.setTicket(ticket);
            auditoria.setDataHoraAlteracao(new Timestamp(System.currentTimeMillis()));
            auditoria.setUserAlteracao(userAlteracao);
            auditoriaRepository.save(auditoria);

            return new MensagemDTO("Ticket atualizado com sucesso!", HttpStatus.CREATED);

        } else {

            Auditoria auditoria = new Auditoria();
            auditoria.setTicket(ticket);
            auditoria.setDataHoraFinalizacao(new Timestamp(System.currentTimeMillis()));
            auditoria.setUserFinalizacao(userAlteracao);
            auditoriaRepository.save(auditoria);

            return new MensagemDTO("Ticket finalizado com sucesso!", HttpStatus.CREATED);

        }
    }

    public MensagemDTO cancelar(Long id, String userExclusao) {
        Ticket ticketBanco = ticketRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Notebook com ID " + id + " nao existe!"));

        desativarTicket(ticketBanco);

        Auditoria auditoria = new Auditoria();
        auditoria.setTicket(ticketBanco);
        auditoria.setDataHoraExclusao(new Timestamp(System.currentTimeMillis()));
        auditoria.setUserExclusao(userExclusao);
        auditoriaRepository.save(auditoria);

        return new MensagemDTO("Ticket cancelado com sucesso!", HttpStatus.CREATED);
    }

    private void desativarTicket(Ticket ticket) {
        ticket.setAtivo(false);
        ticketRepository.save(ticket);
    }

    public TicketDTO ticketToDTO(Ticket ticket) {
        TicketDTO ticketDTO = new TicketDTO();

        ticketDTO.setId(ticket.getId());
        ticketDTO.setDataDevolucao(ticket.getDataDevolucao());
        ticketDTO.setDataEntrega(ticket.getDataEntrega());
        ticketDTO.setObservacao(ticket.getObservacao());

        AlunoDTO alunoDTO = new AlunoDTO();

        alunoDTO.setId(ticket.getAlunoId().getId());
        alunoDTO.setNome(ticket.getAlunoId().getNome());
        alunoDTO.setRa(ticket.getAlunoId().getRa());


        NotebookDTO notebookDTO = new NotebookDTO();
        notebookDTO.setId(ticket.getNotebookId().getId());
        notebookDTO.setPatrimonio(ticket.getNotebookId().getPatrimonio());

        ModeloDTO modeloDTO = new ModeloDTO();

        modeloDTO.setId(ticket.getNotebookId().getModeloId().getId());

        notebookDTO.setModeloId(modeloDTO);

        notebookDTO.setModeloId(modeloDTO);


        ticketDTO.setAlunoId(alunoDTO);
        ticketDTO.setNotebookId(notebookDTO);
        return ticketDTO;
    }

    public Ticket toTicket(TicketDTO ticketDTO) {
        Ticket novoTicket = new Ticket();

        novoTicket.setId(ticketDTO.getId());
        novoTicket.setDataDevolucao(ticketDTO.getDataDevolucao());
        novoTicket.setDataEntrega(ticketDTO.getDataEntrega());
        novoTicket.setObservacao(ticketDTO.getObservacao());

        Aluno aluno = new Aluno();
        aluno.setId(ticketDTO.getAlunoId().getId());

        Notebook notebook = new Notebook();
        notebook.setId(ticketDTO.getNotebookId().getId());
        novoTicket.setAlunoId(aluno);
        novoTicket.setNotebookId(notebook);

        return novoTicket;
    }
}