//package uniamerica.centralDeAjuda.ControllerTest;
//
//import jakarta.persistence.EntityNotFoundException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import uniamerica.centralDeAjuda.Controller.TicketController;
//import uniamerica.centralDeAjuda.DTO.AlunoDTO;
//import uniamerica.centralDeAjuda.DTO.NotebookDTO;
//import uniamerica.centralDeAjuda.DTO.TicketDTO;
//import uniamerica.centralDeAjuda.Services.TicketService;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//public class TicketControllerTeste {
//    @InjectMocks
//    private TicketController ticketController;
//
//    @Mock
//    private TicketService ticketService;
//
//    @BeforeEach
//    void setUp(){
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void buscarByIdTeste(){
//        Long ticketId = 1L;
//        TicketDTO ticketDTO = new TicketDTO();
//
//        when(ticketService.findTicketById(ticketId)).thenReturn(ticketDTO);
//
//        ResponseEntity<TicketDTO> response = ticketController.buscarPorId(ticketId);
//
//        assertEquals(HttpStatus.OK,response.getStatusCode());
//        assertEquals(ticketDTO, response.getBody());
//    }
//
//    @Test
//    void buscarByIdTesteNaoExiste(){
//        Long ticketId = 1L;
//
//        when(ticketService.findTicketById(ticketId)).thenReturn(null);
//
//        ResponseEntity<TicketDTO> response = ticketController.buscarPorId(ticketId);
//
//        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
//        assertNull(response.getBody());
//    }
//
//    @Test
//    void listarTicketsAbertosTeste() {
//        List<TicketDTO> tickets = new ArrayList<>();
//
//        when(ticketService.buscarTicketsAbertos()).thenReturn(tickets);
//
//        List<TicketDTO> ticketDTOList = ticketController.buscarTicketsAbertos();
//
//        assertEquals(tickets.size(),ticketDTOList.size());
//    }
//
//    @Test
//    void listarTicketsHistoricosTeste() {
//        List<TicketDTO> tickets = new ArrayList<>();
//
//        when(ticketService.buscarHistoricoPorDataDevolucao()).thenReturn(tickets);
//
//        List<TicketDTO> ticketDTOList = ticketController.buscarHistoricoPorDataDevolucao();
//
//        assertEquals(tickets.size(),ticketDTOList.size());
//    }
//
//    @Test
//    void cadastrarTicketTeste() {
//        TicketDTO ticketDTO = new TicketDTO();
//        ticketDTO.setId(1L);
//        ticketDTO.setAtivo(true);
//
//        AlunoDTO alunoDTO = new AlunoDTO();
//        ticketDTO.setAlunoId(alunoDTO);
//
//        NotebookDTO notebookDTO = new NotebookDTO();
//        ticketDTO.setNotebookId(notebookDTO);
//
//        ticketDTO.setDataEntrega(LocalDateTime.now());
//
//        when(ticketService.cadastrarTicket(ticketDTO)).thenReturn("Ticket cadastrado com sucesso!");
//
//        ResponseEntity<String> response = ticketController.cadastrarTicket(ticketDTO);
//
//        assertEquals(HttpStatus.OK,response.getStatusCode());
//        assertEquals("Ticket cadastrado com sucesso!",response.getBody());
//    }
//
//    @Test
//    void cadastrarTicketTesteAlunoNaoExiste() {
//        TicketDTO ticketDTO = new TicketDTO();
//        ticketDTO.setId(1L);
//        ticketDTO.setAtivo(true);
//
//        NotebookDTO notebookDTO = new NotebookDTO();
//        ticketDTO.setNotebookId(notebookDTO);
//
//        ticketDTO.setDataEntrega(LocalDateTime.now());
//
//        when(ticketService.cadastrarTicket(ticketDTO)).thenThrow(EntityNotFoundException.class);
//
//        ResponseEntity<String> response = ticketController.cadastrarTicket(ticketDTO);
//
//        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
//        assertNull(response.getBody());
//    }
//
//    @Test
//    void cadastrarTicketTesteNotebookNaoExiste() {
//        TicketDTO ticketDTO = new TicketDTO();
//        ticketDTO.setId(1L);
//        ticketDTO.setAtivo(true);
//
//        AlunoDTO alunoDTO = new AlunoDTO();
//        ticketDTO.setAlunoId(alunoDTO);
//
//        ticketDTO.setDataEntrega(LocalDateTime.now());
//
//        when(ticketService.cadastrarTicket(ticketDTO)).thenThrow(EntityNotFoundException.class);
//
//        ResponseEntity<String> response = ticketController.cadastrarTicket(ticketDTO);
//
//        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
//        assertNull(response.getBody());
//    }
//
//    @Test
//    void cadastrarTicketTesteDataDeEntregaNULL() {
//        TicketDTO ticketDTO = new TicketDTO();
//        ticketDTO.setId(1L);
//        ticketDTO.setAtivo(true);
//
//        AlunoDTO alunoDTO = new AlunoDTO();
//        ticketDTO.setAlunoId(alunoDTO);
//
//        NotebookDTO notebookDTO = new NotebookDTO();
//        ticketDTO.setNotebookId(notebookDTO);
//
//        when(ticketService.cadastrarTicket(ticketDTO)).thenThrow(EntityNotFoundException.class);
//
//        ResponseEntity<String> response = ticketController.cadastrarTicket(ticketDTO);
//
//        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
//        assertNull(response.getBody());
//    }
//
//    @Test
//    void editarTicketTeste() {
//        Long ticketId = 1L;
//        TicketDTO ticketDTO = new TicketDTO();
//        ticketDTO.setId(1L);
//        ticketDTO.setAtivo(true);
//
//        AlunoDTO alunoDTO = new AlunoDTO();
//        ticketDTO.setAlunoId(alunoDTO);
//
//        NotebookDTO notebookDTO = new NotebookDTO();
//        ticketDTO.setNotebookId(notebookDTO);
//
//        ticketDTO.setDataEntrega(LocalDateTime.now());
//
//        when(ticketService.editarTicket(ticketId,ticketDTO)).thenReturn("Ticket atualizado com sucesso!");
//
//        ResponseEntity<String> response = ticketController.editarTicket(ticketId,ticketDTO);
//
//        assertEquals(HttpStatus.OK,response.getStatusCode());
//        assertEquals("Ticket atualizado com sucesso!",response.getBody());
//    }
//
//    @Test
//    void editarTicketTesteNaoExiste() {
//        Long ticketId = 1L;
//        TicketDTO ticketDTO = new TicketDTO();
//
//        when(ticketService.editarTicket(ticketId,ticketDTO)).thenThrow(EntityNotFoundException.class);
//
//        ResponseEntity<String> response = ticketController.editarTicket(ticketId,ticketDTO);
//
//        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
//        assertNull(response.getBody());
//    }
//}
