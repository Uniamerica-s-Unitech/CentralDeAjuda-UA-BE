package uniamerica.centralDeAjuda.Services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import uniamerica.centralDeAjuda.DTO.TicketDTO;
import uniamerica.centralDeAjuda.Entity.Ticket;
import uniamerica.centralDeAjuda.Repository.AlunoRepository;
import uniamerica.centralDeAjuda.Repository.NotebookRepository;
import uniamerica.centralDeAjuda.Repository.TicketRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private NotebookRepository notebookRepository;

    public List<TicketDTO> listar(){
        List<Ticket> tickets = ticketRepository.findAll();
        return tickets.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TicketDTO> buscarHistoricoPorDataDevolucao(LocalDateTime dataDevolucao) {
        List<Ticket> tickets = ticketRepository.findHistoricoByDataDevolucao(dataDevolucao);
        return tickets.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TicketDTO cadastrar(TicketDTO ticketDTO){
        Ticket ticket = new Ticket();
        BeanUtils.copyProperties(ticketDTO,ticket);

        Assert.notNull(ticket.getAlunoId(), "Aluno inválido");
        if (!alunoRepository.existsById(ticket.getAlunoId().getId()) || !ticketRepository.findByAluno(ticket.getAlunoId()).isEmpty()) {
            throw new IllegalArgumentException("ID de aluno inválido ou já possui um ticket ativo");
        }

        Assert.notNull(ticket.getAlunoId(), "Notebook invalido");
        if (!notebookRepository.existsById(ticket.getNotebookId().getId()) || !ticketRepository.findByNotebook(ticket.getNotebookId()).isEmpty()) {
            throw new IllegalArgumentException("ID de notebook inválido ou já possui um ticket ativo");
        }

        Assert.notNull(ticket.getDataEntrega(),"Data de entrega invalida");

        ticket = ticketRepository.save(ticket);
        return convertToDTO(ticket);
    }

    public TicketDTO editar(Long id,TicketDTO ticketDTO){
        if (ticketRepository.existsById(id)){
            Ticket ticket = new Ticket();
            BeanUtils.copyProperties(ticketDTO,ticket);

            Assert.notNull(ticket.getAlunoId(), "Aluno inválido");
            if (!alunoRepository.existsById(ticket.getAlunoId().getId()) || !ticketRepository.findByAluno(ticket.getAlunoId()).isEmpty()) {
                throw new IllegalArgumentException("ID de aluno inválido ou já possui um ticket ativo");
            }

            Assert.notNull(ticket.getAlunoId(), "Notebook invalido");
            if (!notebookRepository.existsById(ticket.getNotebookId().getId()) || !ticketRepository.findByNotebook(ticket.getNotebookId()).isEmpty()) {
                throw new IllegalArgumentException("ID de notebook inválido ou já possui um ticket ativo");
            }

            Assert.notNull(ticket.getDataEntrega(),"Data de entrega invalida");

            ticket = ticketRepository.save(ticket);
            return convertToDTO(ticket);
        }
        return null;
    }
    private TicketDTO convertToDTO(Ticket ticket){
        TicketDTO ticketDTO = new TicketDTO();
        BeanUtils.copyProperties(ticket, ticketDTO);
        return ticketDTO;
    }
}
