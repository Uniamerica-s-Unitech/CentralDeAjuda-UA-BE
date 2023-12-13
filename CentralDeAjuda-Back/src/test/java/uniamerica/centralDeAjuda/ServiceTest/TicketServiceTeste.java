//package uniamerica.centralDeAjuda.ServiceTest;
//
//import jakarta.persistence.EntityNotFoundException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//import uniamerica.centralDeAjuda.DTO.*;
//import uniamerica.centralDeAjuda.Entity.*;
//import uniamerica.centralDeAjuda.Repository.*;
//import uniamerica.centralDeAjuda.Repository.TicketRepository;
//import uniamerica.centralDeAjuda.Services.TicketService;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//public class TicketServiceTeste {
//    @InjectMocks
//    private TicketService ticketService;
//
//    @Mock
//    private TicketRepository ticketRepository;
//
//    @Mock
//    private AlunoRepository alunoRepository;
//
//    @Mock
//    private NotebookRepository notebookRepository;
//
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void findTicketByIdTest() {
//        Ticket ticket = new Ticket();
//        ticket.setId(1L);
//
//        Notebook notebook = new Notebook();
//        notebook.setId(1L);
//
//        Modelo modelo = new Modelo();
//        modelo.setId(1L);
//
//        notebook.setModeloId(modelo);
//
//        Aluno aluno = new Aluno();
//        aluno.setId(1L);
//
//        ticket.setNotebookId(notebook);
//        ticket.setAlunoId(aluno);
//
//        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
//
//        TicketDTO ticketDTO = ticketService.findTicketById(1L);
//
//        assertNotNull(ticketDTO);
//        assertEquals(1L, ticketDTO.getId());
//        assertNotNull(ticketDTO.getAlunoId());
//        assertNotNull(ticketDTO.getNotebookId());
//    }
//
//    @Test
//    void findTicketByIdNULLTeste(){
//        when(ticketRepository.findById(2L)).thenReturn(Optional.empty());
//
//        assertThrows(EntityNotFoundException.class,()-> ticketService.findTicketById(2L));
//    }
//
//    @Test
//    void listarTicketsAbertosTest() {
//        List<Ticket> tickets = new ArrayList<>();
//        Ticket ticket = new Ticket();
//        ticket.setId(1L);
//        ticket.setAtivo(true);
//        ticket.setDataEntrega(LocalDateTime.now());
//
//        Notebook notebook = new Notebook();
//        notebook.setId(1L);
//
//        Modelo modelo = new Modelo();
//        modelo.setId(1L);
//
//        notebook.setModeloId(modelo);
//
//        Aluno aluno = new Aluno();
//        aluno.setId(1L);
//
//        ticket.setAlunoId(aluno);
//        ticket.setNotebookId(notebook);
//
//        tickets.add(ticket);
//
//        when(ticketRepository.findTicketsAbertos()).thenReturn(tickets);
//
//        List<TicketDTO> ticketDTOS = ticketService.buscarTicketsAbertos();
//
//        assertNotNull(ticketDTOS);
//        assertEquals(tickets.size(), ticketDTOS.size());
//
//        TicketDTO ticketDTO = ticketDTOS.get(0);
//        assertEquals(ticket.getId(), ticketDTO.getId());
//        assertEquals(ticket.getAtivo(), ticketDTO.getAtivo());
//        assertEquals(ticket.getDataEntrega(), ticketDTO.getDataEntrega());
//        assertEquals(ticket.getAlunoId().getId(), ticketDTO.getAlunoId().getId());
//        assertEquals(ticket.getNotebookId().getId(), ticketDTO.getNotebookId().getId());
//    }
//
//    @Test
//    void listarTicketsHistoricoTest() {
//        List<Ticket> tickets = new ArrayList<>();
//        Ticket ticket = new Ticket();
//        ticket.setId(1L);
//        ticket.setAtivo(true);
//        ticket.setDataEntrega(LocalDateTime.now());
//        ticket.setDataDevolucao(LocalDateTime.now());
//
//        Notebook notebook = new Notebook();
//        notebook.setId(1L);
//
//        Modelo modelo = new Modelo();
//        modelo.setId(1L);
//
//        notebook.setModeloId(modelo);
//
//        Aluno aluno = new Aluno();
//        aluno.setId(1L);
//
//        ticket.setAlunoId(aluno);
//        ticket.setNotebookId(notebook);
//
//        tickets.add(ticket);
//
//        when(ticketRepository.findHistoricoByDataDevolucao()).thenReturn(tickets);
//
//        List<TicketDTO> ticketDTOS = ticketService.buscarHistoricoPorDataDevolucao();
//
//        assertNotNull(ticketDTOS);
//        assertEquals(tickets.size(), ticketDTOS.size());
//
//        TicketDTO ticketDTO = ticketDTOS.get(0);
//        assertEquals(ticket.getId(), ticketDTO.getId());
//        assertEquals(ticket.getAtivo(), ticketDTO.getAtivo());
//        assertEquals(ticket.getDataEntrega(), ticketDTO.getDataEntrega());
//        assertEquals(ticket.getDataDevolucao(), ticketDTO.getDataDevolucao());
//        assertEquals(ticket.getAlunoId().getId(), ticketDTO.getAlunoId().getId());
//        assertEquals(ticket.getNotebookId().getId(), ticketDTO.getNotebookId().getId());
//    }
//
//    @Test
//    void cadastrarTicketTeste() {
//        TicketDTO ticketDTO = new TicketDTO();
//        ticketDTO.setDataEntrega(LocalDateTime.now());
//        ticketDTO.setAlunoId(new AlunoDTO(1L,true,"Hisham","123123"));
//        ticketDTO.setNotebookId(new NotebookDTO(1L,true,"741",new ModeloDTO()));
//
//        Aluno aluno = new Aluno();
//        aluno.setId(1L);
//        aluno.setAtivo(true);
//        aluno.setNome("Hisham");
//        aluno.setRa("123123");
//
//        Notebook notebook = new Notebook();
//        notebook.setId(1L);
//        notebook.setAtivo(true);
//        notebook.setPatrimonio("741");
//
//        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
//        when(notebookRepository.findById(1L)).thenReturn(Optional.of(notebook));
//
//        String mensagem = ticketService.cadastrarTicket(ticketDTO);
//
//        assertEquals("Ticket cadastrado com sucesso!",mensagem);
//
//        Ticket ticketCadastrado = ticketService.toTicket(ticketDTO);
//
//        assertEquals(ticketDTO.getDataEntrega(),ticketCadastrado.getDataEntrega());
//        assertEquals(ticketDTO.getAlunoId().getId(),ticketCadastrado.getAlunoId().getId());
//        assertEquals(ticketDTO.getNotebookId().getId(),ticketCadastrado.getNotebookId().getId());
//    }
//
//    @Test
//    void editarTicketTeste(){
//        Long ticketid = 1L;
//        TicketDTO ticketDTO = new TicketDTO();
//        ticketDTO.setId(ticketid);
//        ticketDTO.setAtivo(true);
//        ticketDTO.setDataEntrega(LocalDateTime.now());
//        ticketDTO.setAlunoId(new AlunoDTO(2L,true,"homam","654654"));
//        ticketDTO.setNotebookId(new NotebookDTO(2L,true,"0000",new ModeloDTO()));
//
//
//        Ticket ticket = new Ticket();
//        ticket.setId(ticketid);
//        ticket.setAtivo(true);
//        ticket.setDataEntrega(LocalDateTime.now());
//        ticket.setAlunoId(new Aluno(2L,true,"homam","654654"));
//        ticket.setNotebookId(new Notebook(2L,true,"0000",new Modelo()));
//
//
//        when(ticketRepository.existsById(ticketid)).thenReturn(true);
//        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);
//        when(alunoRepository.findById(2L)).thenReturn(Optional.of(new Aluno(2L, true, "homam","654654")));
//        when(notebookRepository.findById(2L)).thenReturn(Optional.of(new Notebook(2L, true, "0000",new Modelo())));
//
//        String mensagem = ticketService.editarTicket(ticketid,ticketDTO);
//
//        assertEquals("Ticket atualizado com sucesso!",mensagem);
//
//        ArgumentCaptor<Ticket> ticketCaptor = ArgumentCaptor.forClass(Ticket.class);
//        verify(ticketRepository).save(ticketCaptor.capture());
//
//        Ticket ticketEditado = ticketCaptor.getValue();
//        assertEquals(ticketDTO.getId(),ticketEditado.getId());
//        assertEquals(ticketDTO.getAtivo(), ticketEditado.getAtivo());
//        assertEquals(ticketDTO.getDataEntrega(),ticketEditado.getDataEntrega());
//        assertEquals(ticketDTO.getAlunoId().getId(),ticketEditado.getAlunoId().getId());
//        assertEquals(ticketDTO.getNotebookId().getId(),ticketEditado.getNotebookId().getId());
//    }
//
//    @Test
//    void editarTicketNaoExisteTest() {
//        Long ticketId = 1L;
//        TicketDTO ticketDTO = new TicketDTO();
//        ticketDTO.setId(ticketId);
//
//        when(ticketRepository.existsById(ticketId)).thenReturn(false);
//
//        assertThrows(IllegalArgumentException.class, () -> ticketService.editarTicket(ticketId, ticketDTO));
//    }
//}
