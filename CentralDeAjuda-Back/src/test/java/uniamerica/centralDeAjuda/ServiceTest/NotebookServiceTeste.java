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
//import uniamerica.centralDeAjuda.DTO.MarcaDTO;
//import uniamerica.centralDeAjuda.DTO.ModeloDTO;
//import uniamerica.centralDeAjuda.DTO.NotebookDTO;
//import uniamerica.centralDeAjuda.Entity.Marca;
//import uniamerica.centralDeAjuda.Entity.Modelo;
//import uniamerica.centralDeAjuda.Entity.Notebook;
//import uniamerica.centralDeAjuda.Repository.ModeloRepository;
//import uniamerica.centralDeAjuda.Repository.NotebookRepository;
//import uniamerica.centralDeAjuda.Repository.TicketRepository;
//import uniamerica.centralDeAjuda.Services.NotebookService;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//public class NotebookServiceTeste {
//
//    @InjectMocks
//    private NotebookService notebookService;
//
//    @Mock
//    private ModeloRepository modeloRepository;
//
//    @Mock
//    private TicketRepository ticketRepository;
//
//    @Mock
//    private NotebookRepository notebookRepository;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void findNotebookByIdTest() {
//        Notebook notebook = new Notebook();
//        notebook.setId(1L);
//
//        Modelo modelo = new Modelo();
//        modelo.setId(1L);
//
//        Marca marca = new Marca();
//        marca.setId(1L);
//
//        modelo.setMarcaId(marca);
//
//        notebook.setModeloId(modelo);
//
//        when(notebookRepository.findById(1L)).thenReturn(Optional.of(notebook));
//
//        NotebookDTO notebookDTO = notebookService.findNotebookById(1L);
//
//        assertNotNull(notebookDTO);
//        assertEquals(1L, notebookDTO.getId());
//        assertNotNull(notebookDTO.getModeloId());
//    }
//
//    @Test
//    void findNotebookByIdNULLTeste(){
//        when(notebookRepository.findById(2L)).thenReturn(Optional.empty());
//
//        assertThrows(EntityNotFoundException.class,()-> notebookService.findNotebookById(2L));
//    }
//
//    @Test
//    void listarNotebooksTest() {
//        List<Notebook> notebooks = new ArrayList<>();
//        Notebook notebook = new Notebook();
//        notebook.setId(1L);
//        notebook.setAtivo(true);
//        notebook.setPatrimonio("12345678");
//
//        Modelo modelo = new Modelo();
//        modelo.setId(1L);
//
//        Marca marca = new Marca();
//        marca.setId(1L);
//
//        modelo.setMarcaId(marca);
//
//        notebook.setModeloId(modelo);
//
//        notebooks.add(notebook);
//
//        when(notebookRepository.findNotebookByAtivo()).thenReturn(notebooks);
//
//        List<NotebookDTO> notebookDTOS = notebookService.listar();
//
//        assertNotNull(notebookDTOS);
//        assertEquals(notebooks.size(), notebookDTOS.size());
//
//        NotebookDTO notebookDTO = notebookDTOS.get(0);
//        assertEquals(notebook.getId(), notebookDTO.getId());
//        assertEquals(notebook.getAtivo(), notebookDTO.getAtivo());
//        assertEquals(notebook.getPatrimonio(), notebookDTO.getPatrimonio());
//        assertEquals(notebook.getModeloId().getId(), notebookDTO.getModeloId().getId());
//    }
//
//    @Test
//    void cadastrarNotebookTeste() {
//        NotebookDTO notebookDTO = new NotebookDTO();
//        notebookDTO.setPatrimonio("123456123");
//        notebookDTO.setModeloId(new ModeloDTO(1L,true,"Nitro 5",new MarcaDTO()));
//
//        Modelo modelo = new Modelo();
//        modelo.setId(1L);
//        modelo.setAtivo(true);
//        modelo.setNome("Nitro 5");
//
//        when(modeloRepository.findById(1L)).thenReturn(Optional.of(modelo));
//
//        String mensagem = notebookService.cadastrarNotebook(notebookDTO);
//
//        assertEquals("Notebook cadastrado com sucesso!",mensagem);
//
//        Notebook notebookCadastrado = notebookService.toNotebook(notebookDTO);
//
//        assertEquals(notebookDTO.getPatrimonio(),notebookCadastrado.getPatrimonio());
//        assertEquals(notebookDTO.getModeloId().getId(),notebookCadastrado.getModeloId().getId());
//    }
//
//    @Test
//    void editarNotebookTeste(){
//        Long notebookid = 1L;
//        NotebookDTO notebookDTO = new NotebookDTO();
//        notebookDTO.setId(notebookid);
//        notebookDTO.setAtivo(true);
//        notebookDTO.setPatrimonio("000");
//        notebookDTO.setModeloId(new ModeloDTO(2L,true,"Dell",new MarcaDTO()));
//
//
//        Notebook notebook = new Notebook();
//        notebook.setId(notebookid);
//        notebook.setAtivo(true);
//        notebook.setPatrimonio("123");
//        notebook.setModeloId(new Modelo(2L,true,"Dell",new Marca(),null));
//
//
//        when(notebookRepository.existsById(notebookid)).thenReturn(true);
//        when(notebookRepository.save(any(Notebook.class))).thenReturn(notebook);
//        when(modeloRepository.findById(2L)).thenReturn(Optional.of(new Modelo(2L, true, "Dell",new Marca(),null)));
//
//
//        String mensagem = notebookService.editarNotebook(notebookid,notebookDTO);
//
//        assertEquals("Notebook atualizado com sucesso!",mensagem);
//
//        ArgumentCaptor<Notebook> notebookCaptor = ArgumentCaptor.forClass(Notebook.class);
//        verify(notebookRepository).save(notebookCaptor.capture());
//
//        Notebook notebookEditado = notebookCaptor.getValue();
//        assertEquals(notebookDTO.getId(),notebookEditado.getId());
//        assertEquals(notebookDTO.getAtivo(), notebookEditado.getAtivo());
//        assertEquals(notebookDTO.getPatrimonio(),notebookEditado.getPatrimonio());
//        assertEquals(notebookDTO.getModeloId().getId(),notebookEditado.getModeloId().getId());
//    }
//
//    @Test
//    void editarNotebookNaoExisteTest() {
//        Long notebookId = 1L;
//        NotebookDTO notebookDTO = new NotebookDTO();
//        notebookDTO.setId(notebookId);
//
//        when(notebookRepository.existsById(notebookId)).thenReturn(false);
//
//        assertThrows(IllegalArgumentException.class, () -> notebookService.editarNotebook(notebookId, notebookDTO));
//    }
//
//    @Test
//    void deletarAlunoTeste() {
//        Long id = 1L;
//        Notebook notebook = new Notebook();
//        notebook.setId(id);
//        notebook.setAtivo(true);
//        notebook.setPatrimonio("963");
//
//        Modelo modelo = new Modelo();
//        notebook.setModeloId(modelo);
//
//        when(notebookRepository.findById(id)).thenReturn(Optional.of(notebook));
//        when(ticketRepository.findTicketsAbertosPorNotebook(notebook)).thenReturn(new ArrayList<>());
//
//        assertDoesNotThrow(() -> notebookService.deletar(id));
//
//        ArgumentCaptor<Notebook> notebookCaptor = ArgumentCaptor.forClass(Notebook.class);
//        verify(notebookRepository).save(notebookCaptor.capture());
//
//        Notebook notebookDesativada = notebookCaptor.getValue();
//        assertFalse(notebookDesativada.getAtivo());
//    }
//}
