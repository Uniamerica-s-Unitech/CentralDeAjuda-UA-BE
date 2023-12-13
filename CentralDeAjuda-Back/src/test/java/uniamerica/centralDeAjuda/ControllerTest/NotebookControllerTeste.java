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
//import uniamerica.centralDeAjuda.Controller.NotebookController;
//import uniamerica.centralDeAjuda.DTO.ModeloDTO;
//import uniamerica.centralDeAjuda.DTO.NotebookDTO;
//import uniamerica.centralDeAjuda.Services.NotebookService;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//public class NotebookControllerTeste {
//    @InjectMocks
//    private NotebookController notebookController;
//
//    @Mock
//    private NotebookService notebookService;
//
//    @BeforeEach
//    void setUp(){
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void buscarByIdTeste(){
//        Long notebookId = 1L;
//        NotebookDTO notebookDTO = new NotebookDTO();
//
//        when(notebookService.findNotebookById(notebookId)).thenReturn(notebookDTO);
//
//        ResponseEntity<NotebookDTO> response = notebookController.buscarPorId(notebookId);
//
//        assertEquals(HttpStatus.OK,response.getStatusCode());
//        assertEquals(notebookDTO, response.getBody());
//    }
//
//    @Test
//    void buscarByIdTesteNaoExiste(){
//        Long notebookId = 1L;
//
//        when(notebookService.findNotebookById(notebookId)).thenReturn(null);
//
//        ResponseEntity<NotebookDTO> response = notebookController.buscarPorId(notebookId);
//
//        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
//        assertNull(response.getBody());
//    }
//
//    @Test
//    void listarNotebooksTeste() {
//        List<NotebookDTO> notebooks = new ArrayList<>();
//
//        when(notebookService.listar()).thenReturn(notebooks);
//
//        List<NotebookDTO> notebookDTOList = notebookController.listar();
//
//        assertEquals(notebooks.size(),notebookDTOList.size());
//    }
//
//    @Test
//    void cadastrarNotebookTeste() {
//        NotebookDTO notebookDTO = new NotebookDTO();
//        notebookDTO.setId(1L);
//        notebookDTO.setAtivo(true);
//        notebookDTO.setPatrimonio("12345678");
//
//        ModeloDTO modeloDTO = new ModeloDTO();
//        notebookDTO.setModeloId(modeloDTO);
//
//        when(notebookService.cadastrarNotebook(notebookDTO)).thenReturn("Notebook cadastrado com sucesso!");
//
//        ResponseEntity<String> response = notebookController.cadastrarNotebook(notebookDTO);
//
//        assertEquals(HttpStatus.OK,response.getStatusCode());
//        assertEquals("Notebook cadastrado com sucesso!",response.getBody());
//    }
//
//    @Test
//    void cadastrarNotebookTesteModeloNaoExiste() {
//        NotebookDTO notebookDTO = new NotebookDTO();
//        notebookDTO.setId(1L);
//        notebookDTO.setAtivo(true);
//        notebookDTO.setPatrimonio("123123");
//
//        when(notebookService.cadastrarNotebook(notebookDTO)).thenThrow(EntityNotFoundException.class);
//
//        ResponseEntity<String> response = notebookController.cadastrarNotebook(notebookDTO);
//
//        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
//        assertNull(response.getBody());
//    }
//
//    @Test
//    void editarNotebookTeste() {
//        Long notebookId = 1L;
//        NotebookDTO notebookDTO = new NotebookDTO();
//        notebookDTO.setId(1L);
//        notebookDTO.setAtivo(true);
//        notebookDTO.setPatrimonio("000");
//
//        ModeloDTO modeloDTO = new ModeloDTO();
//        notebookDTO.setModeloId(modeloDTO);
//
//        when(notebookService.editarNotebook(notebookId,notebookDTO)).thenReturn("Notebook atualizado com sucesso!");
//
//        ResponseEntity<String> response = notebookController.editarNotebook(notebookId,notebookDTO);
//
//        assertEquals(HttpStatus.OK,response.getStatusCode());
//        assertEquals("Notebook atualizado com sucesso!",response.getBody());
//    }
//
//    @Test
//    void editarNotebookTesteNaoExiste() {
//        Long notebookId = 1L;
//        NotebookDTO notebookDTO = new NotebookDTO();
//
//        when(notebookService.editarNotebook(notebookId,notebookDTO)).thenThrow(EntityNotFoundException.class);
//
//        ResponseEntity<String> response = notebookController.editarNotebook(notebookId,notebookDTO);
//
//        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
//        assertNull(response.getBody());
//    }
//
//    @Test
//    void deletarNotebookTeste() {
//        Long notebookId = 1L;
//
//        ResponseEntity<String> response = notebookController.deletar(notebookId);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("Notebook deletado com sucesso!", response.getBody());
//    }
//}
