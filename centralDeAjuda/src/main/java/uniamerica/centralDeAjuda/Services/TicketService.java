package uniamerica.centralDeAjuda.Services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import uniamerica.centralDeAjuda.DTO.TicketDTO;
import uniamerica.centralDeAjuda.Entity.Aluno;
import uniamerica.centralDeAjuda.Entity.Notebook;
import uniamerica.centralDeAjuda.Entity.Ticket;
import uniamerica.centralDeAjuda.Repository.AlunoRepository;
import uniamerica.centralDeAjuda.Repository.NotebookRepository;
import uniamerica.centralDeAjuda.Repository.TicketRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class
TicketService {
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

    public List<TicketDTO> buscarTicketsAbertos() {
        List<Ticket> ticketsAbertos = ticketRepository.findTicketsAbertos();
        return ticketsAbertos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TicketDTO> buscarHistoricoPorDataDevolucao() {
        LocalDateTime dataDevolucao = LocalDateTime.now(); // Ajuste isso conforme necessário
        List<Ticket> tickets = ticketRepository.findHistoricoByDataDevolucao(dataDevolucao);
        return tickets.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public TicketDTO cadastrar(TicketDTO ticketDTO){
        Ticket ticket = new Ticket();
        BeanUtils.copyProperties(ticketDTO,ticket);

        if (ticket.getDataDevolucao() != null) {
            ticket.setAtivo(false);
        }

        Assert.notNull(ticket.getAlunoId(), "Aluno inválido");
        Aluno aluno = alunoRepository.findById(ticketDTO.getAlunoId().getId())
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));
        if (!ticketRepository.findByAluno(aluno).isEmpty()) {
            throw new IllegalArgumentException("O aluno já possui um ticket ativo");
        }

        Assert.notNull(ticket.getAlunoId(), "Notebook invalido");
        Notebook notebook = notebookRepository.findById(ticketDTO.getNotebookId().getId())
                .orElseThrow(()-> new IllegalArgumentException("Notebook não encontrado"));
        if (!ticketRepository.findByNotebook(notebook).isEmpty()) {
            throw new IllegalArgumentException("O notebook já possui um ticket ativo");
        }

        Assert.notNull(ticket.getDataEntrega(),"Data de entrega invalida");

        ticket = ticketRepository.save(ticket);
        return convertToDTO(ticket);
    }

    public TicketDTO editar(Long id,TicketDTO ticketDTO){
        if (ticketRepository.existsById(id)){
            Ticket ticket = ticketRepository.findById(id).orElse(null);
            if (ticket != null){
                BeanUtils.copyProperties(ticketDTO,ticket);

                if (ticket.getDataDevolucao() != null) {
                    ticket.setAtivo(false);
                }

                Assert.notNull(ticket.getAlunoId(), "Aluno inválido");
                Aluno aluno = alunoRepository.findById(ticketDTO.getAlunoId().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));
                if (!ticketRepository.findByAlunoPut(aluno,id).isEmpty()) {
                    throw new IllegalArgumentException("O aluno já possui um ticket ativo");
                }

                Assert.notNull(ticket.getAlunoId(), "Notebook invalido");
                Notebook notebook = notebookRepository.findById(ticketDTO.getNotebookId().getId())
                        .orElseThrow(()-> new IllegalArgumentException("Notebook não encontrado"));
                if (!ticketRepository.findByNotebookPut(notebook,id).isEmpty()) {
                    throw new IllegalArgumentException("O notebook já possui um ticket ativo");
                }

                Assert.notNull(ticket.getDataEntrega(),"Data de entrega invalida");

                ticket = ticketRepository.save(ticket);
                return convertToDTO(ticket);
            }
        }else {
            throw new IllegalArgumentException("Ticket não encontrado com o ID fornecido: " + id);
        }
        return null;
    }
    private TicketDTO convertToDTO(Ticket ticket){
        TicketDTO ticketDTO = new TicketDTO();
        BeanUtils.copyProperties(ticket, ticketDTO);
        return ticketDTO;
    }
}
