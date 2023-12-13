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
//import uniamerica.centralDeAjuda.Controller.ModeloController;
//import uniamerica.centralDeAjuda.DTO.MarcaDTO;
//import uniamerica.centralDeAjuda.DTO.ModeloDTO;
//import uniamerica.centralDeAjuda.Services.ModeloService;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//class ModeloControllerTeste {
//    @InjectMocks
//    private ModeloController modeloController;
//
//    @Mock
//    private ModeloService modeloService;
//
//    @BeforeEach
//    void setUp(){
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void buscarByIdTeste(){
//        Long modeloId = 1L;
//        ModeloDTO modeloDTO = new ModeloDTO();
//
//        when(modeloService.findModeloById(modeloId)).thenReturn(modeloDTO);
//
//        ResponseEntity<ModeloDTO> response = modeloController.buscarPorId(modeloId);
//
//        assertEquals(HttpStatus.OK,response.getStatusCode());
//        assertEquals(modeloDTO, response.getBody());
//    }
//
//    @Test
//    void buscarByIdTesteNaoExiste(){
//        Long modeloId = 1L;
//
//        when(modeloService.findModeloById(modeloId)).thenReturn(null);
//
//        ResponseEntity<ModeloDTO> response = modeloController.buscarPorId(modeloId);
//
//        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
//        assertNull(response.getBody());
//    }
//
//    @Test
//    void listarModelosTeste() {
//        List<ModeloDTO> modelos = new ArrayList<>();
//
//        when(modeloService.listar()).thenReturn(modelos);
//
//        List<ModeloDTO> modeloDTOList = modeloController.listar();
//
//        assertEquals(modelos.size(),modeloDTOList.size());
//    }
//
//    @Test
//    void cadastrarModeloTeste() {
//        ModeloDTO modeloDTO = new ModeloDTO();
//        modeloDTO.setId(1L);
//        modeloDTO.setAtivo(true);
//        modeloDTO.setNome("Nitro 5");
//
//        MarcaDTO marcaDTO = new MarcaDTO();
//        modeloDTO.setMarcaId(marcaDTO);
//
//        when(modeloService.cadastrarModelo(modeloDTO)).thenReturn("Modelo cadastrado com sucesso!");
//
//        ResponseEntity<String> response = modeloController.cadastrarModelo(modeloDTO);
//
//        assertEquals(HttpStatus.OK,response.getStatusCode());
//        assertEquals("Modelo cadastrado com sucesso!",response.getBody());
//    }
//
//    @Test
//    void cadastrarModeloTesteMarcaNaoExiste() {
//        ModeloDTO modeloDTO = new ModeloDTO();
//        modeloDTO.setId(1L);
//        modeloDTO.setAtivo(true);
//        modeloDTO.setNome("Nitro 5");
//
//        when(modeloService.cadastrarModelo(modeloDTO)).thenThrow(EntityNotFoundException.class);
//
//        ResponseEntity<String> response = modeloController.cadastrarModelo(modeloDTO);
//
//        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
//        assertNull(response.getBody());
//    }
//
//    @Test
//    void editarModeloTeste() {
//        Long modeloId = 1L;
//        ModeloDTO modeloDTO = new ModeloDTO();
//        modeloDTO.setId(1L);
//        modeloDTO.setAtivo(true);
//        modeloDTO.setNome("Nitro 5");
//
//        MarcaDTO marcaDTO = new MarcaDTO();
//        modeloDTO.setMarcaId(marcaDTO);
//
//        when(modeloService.editarModelo(modeloId,modeloDTO)).thenReturn("Modelo atualizado com sucesso!");
//
//        ResponseEntity<String> response = modeloController.editarModelo(modeloId,modeloDTO);
//
//        assertEquals(HttpStatus.OK,response.getStatusCode());
//        assertEquals("Modelo atualizado com sucesso!",response.getBody());
//    }
//
//    @Test
//    void editarModeloTesteNaoExiste() {
//        Long modeloId = 1L;
//        ModeloDTO modeloDTO = new ModeloDTO();
//
//        when(modeloService.editarModelo(modeloId,modeloDTO)).thenThrow(EntityNotFoundException.class);
//
//        ResponseEntity<String> response = modeloController.editarModelo(modeloId,modeloDTO);
//
//        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
//        assertNull(response.getBody());
//    }
//
//    @Test
//    void deletarModeloTeste() {
//        Long modeloId = 1L;
//
//        ResponseEntity<String> response = modeloController.deletar(modeloId);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("Modelo deletado com sucesso!", response.getBody());
//    }
//}