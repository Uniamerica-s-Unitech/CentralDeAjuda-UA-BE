package uniamerica.centralDeAjuda.Services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import uniamerica.centralDeAjuda.DTO.AlunoDTO;
import uniamerica.centralDeAjuda.DTO.ModeloDTO;
import uniamerica.centralDeAjuda.DTO.NotebookDTO;
import uniamerica.centralDeAjuda.DTO.TicketDTO;
import uniamerica.centralDeAjuda.Entity.Aluno;
import uniamerica.centralDeAjuda.Entity.Notebook;
import uniamerica.centralDeAjuda.Entity.Ticket;
import uniamerica.centralDeAjuda.Repository.AlunoRepository;
import uniamerica.centralDeAjuda.Repository.NotebookRepository;
import uniamerica.centralDeAjuda.Repository.TicketRepository;

import java.util.List;

@Service
public class
TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private NotebookRepository notebookRepository;


    public TicketDTO findTicketByid(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket não encontrado!"));
        return ticketToDTO(ticket);
    }

    public List<TicketDTO> buscarTicketsAbertos() {
        return ticketRepository.findTicketsAbertos().stream().map(this::ticketToDTO).toList();
    }

    public List<TicketDTO> buscarHistoricoPorDataDevolucao() {
        return ticketRepository.findHistoricoByDataDevolucao().stream().map(this::ticketToDTO).toList();
    }

    @Transactional
    public String cadastrarTicket(TicketDTO ticketDTO) {
        Ticket ticket = toTicket(ticketDTO);

        Assert.notNull(ticket.getAlunoId(),"Aluno inválido!");
        Assert.isTrue(!alunoRepository.findById
                (ticket.getAlunoId().getId()).isEmpty(),"Aluno não existe!");

        Assert.notNull(ticket.getNotebookId(),"Notebook inválido!");
        Assert.isTrue(!notebookRepository.findById
                (ticket.getNotebookId().getId()).isEmpty(),"Notebook não existe!");

        Assert.notNull(ticket.getDataEntrega(),"Data de entrega inválida!");

        List<Ticket> alunoTicketsAtivos = ticketRepository.findTicketsAbertosPorAluno(ticket.getAlunoId());

        List<Ticket> notebookTicketsAtivos = ticketRepository.findTicketsAbertosPorNotebook(ticket.getNotebookId());

        if (!alunoTicketsAtivos.isEmpty()){
            return "O aluno possui um tickey ativo";
        }else if(!notebookTicketsAtivos.isEmpty()){
            return "O notebook possui um tickey ativo";
        }else
        {
            ticketRepository.save(ticket);
            return "Ticket cadastrado com sucesso!";
        }
    }

    public String editarTicket(Long id, TicketDTO ticketDTO) {
        if (ticketRepository.existsById(id)) {
            Ticket ticket = toTicket(ticketDTO);

            Assert.notNull(ticket.getAlunoId(),"Aluno inválido!");
            Assert.isTrue(!alunoRepository.findById
                    (ticket.getAlunoId().getId()).isEmpty(),"Aluno não existe!");

            Assert.notNull(ticket.getNotebookId(),"Notebook inválido!");
            Assert.isTrue(!notebookRepository.findById
                    (ticket.getNotebookId().getId()).isEmpty(),"Notebook não existe!");

            Assert.notNull(ticket.getDataEntrega(),"Data de entrega inválida!");

            /*List<Ticket> alunoTicketsAtivos = ticketRepository.findTicketsAbertosPorAluno(ticket.getAlunoId());

            List<Ticket> notebookTicketsAtivos = ticketRepository.findTicketsAbertosPorNotebook(ticket.getNotebookId());

            if (!alunoTicketsAtivos.isEmpty()){
                return "O aluno possui um tickey ativo";
            }else if(!notebookTicketsAtivos.isEmpty()){
                return "O notebook possui um tickey ativo";
            }else
            {
                ticketRepository.save(ticket);
                return "Ticket cadastrado com sucesso!";
            }*/

            ticketRepository.save(ticket);
            return "Ticket atualizado com sucesso!";

        }else {
            throw new IllegalArgumentException("Ticket não encontrado com o ID fornecido: " + id);
        }
    }

    public TicketDTO ticketToDTO(Ticket ticket){
        TicketDTO ticketDTO = new TicketDTO();

        ticketDTO.setId(ticket.getId());
        ticketDTO.setAtivo(ticket.getAtivo());
        ticketDTO.setDataDevolucao(ticket.getDataDevolucao());
        ticketDTO.setDataEntrega(ticket.getDataEntrega());

            AlunoDTO alunoDTO = new AlunoDTO();

            alunoDTO.setId(ticket.getAlunoId().getId());
            alunoDTO.setAtivo(ticket.getAlunoId().getAtivo());
            alunoDTO.setNome(ticket.getAlunoId().getNome());
            alunoDTO.setRa(ticket.getAlunoId().getRa());


            NotebookDTO notebookDTO = new NotebookDTO();
            notebookDTO.setId(ticket.getNotebookId().getId());
            notebookDTO.setAtivo(ticket.getNotebookId().getAtivo());
            notebookDTO.setPatrimonio(ticket.getNotebookId().getPatrimonio());

                ModeloDTO modeloDTO = new ModeloDTO();

                modeloDTO.setId(ticket.getNotebookId().getModeloId().getId());

                notebookDTO.setModeloId(modeloDTO);

            notebookDTO.setModeloId(modeloDTO);


        ticketDTO.setAlunoId(alunoDTO);
        ticketDTO.setNotebookId(notebookDTO);
        return ticketDTO;
    }

    public Ticket toTicket(TicketDTO ticketDTO){
        Ticket novoTicket = new Ticket();

        novoTicket.setId(ticketDTO.getId());
        novoTicket.setAtivo(ticketDTO.getAtivo());
        novoTicket.setDataDevolucao(ticketDTO.getDataDevolucao());
        novoTicket.setDataEntrega(ticketDTO.getDataEntrega());

        Aluno aluno = new Aluno();
        aluno.setId(ticketDTO.getAlunoId().getId());

        Notebook notebook = new Notebook();
        notebook.setId(ticketDTO.getNotebookId().getId());
        novoTicket.setAlunoId(aluno);
        novoTicket.setNotebookId(notebook);

        return novoTicket;
    }
}