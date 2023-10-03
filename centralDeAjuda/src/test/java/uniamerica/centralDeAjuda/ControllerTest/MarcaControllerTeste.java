package uniamerica.centralDeAjuda.ControllerTest;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uniamerica.centralDeAjuda.Controller.MarcaController;
import uniamerica.centralDeAjuda.DTO.MarcaDTO;
import uniamerica.centralDeAjuda.Services.MarcaService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MarcaControllerTeste {

    @InjectMocks
    private MarcaController marcaController;

    @Mock
    private MarcaService marcaService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void buscarByIdTeste(){
        Long marcaId = 1L;
        MarcaDTO marcaDTO = new MarcaDTO();

        when(marcaService.findMarcaById(marcaId)).thenReturn(marcaDTO);

        ResponseEntity<MarcaDTO> response = marcaController.buscarPorId(marcaId);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(marcaDTO, response.getBody());
    }

    @Test
    void buscarByIdTesteNaoExiste(){
        Long marcaId = 1L;

        when(marcaService.findMarcaById(marcaId)).thenReturn(null);

        ResponseEntity<MarcaDTO> response = marcaController.buscarPorId(marcaId);

        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void listarMarcasTeste() {
        List<MarcaDTO> marcas = new ArrayList<>();

        when(marcaService.listar()).thenReturn(marcas);

        List<MarcaDTO> marcaDTOList = marcaController.listar();

        assertEquals(marcas.size(),marcaDTOList.size());
    }

    @Test
    void cadastrarMarcaTeste() {
        MarcaDTO marcaDTO = new MarcaDTO();
        marcaDTO.setId(1L);
        marcaDTO.setAtivo(true);
        marcaDTO.setNome("Acer");

        when(marcaService.cadastrarMarca(marcaDTO)).thenReturn("Marca cadastrada com sucesso!");

        ResponseEntity<String> response = marcaController.cadastrarMarca(marcaDTO);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals("Marca cadastrada com sucesso!",response.getBody());
    }

    @Test
    void cadastrarMarcaTesteNomeNull() {
        MarcaDTO marcaDTO = new MarcaDTO();
        marcaDTO.setId(1L);

        when(marcaService.cadastrarMarca(marcaDTO)).thenThrow(EntityNotFoundException.class);

        ResponseEntity<String> response = marcaController.cadastrarMarca(marcaDTO);

        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void editarMarcaTeste() {
        Long marcaId = 1L;
        MarcaDTO marcaDTO = new MarcaDTO();
        marcaDTO.setId(1L);
        marcaDTO.setAtivo(true);
        marcaDTO.setNome("Dell");

        when(marcaService.editarMarca(marcaId,marcaDTO)).thenReturn("Marca atualizada com sucesso!");

        ResponseEntity<String> response = marcaController.editarMarca(marcaId,marcaDTO);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals("Marca atualizada com sucesso!",response.getBody());
    }

    @Test
    void editarMarcaTesteNaoExiste() {
        Long marcaId = 1L;
        MarcaDTO marcaDTO = new MarcaDTO();

        when(marcaService.editarMarca(marcaId,marcaDTO)).thenThrow(EntityNotFoundException.class);

        ResponseEntity<String> response = marcaController.editarMarca(marcaId,marcaDTO);

        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void deletarMarcaTeste() {
        Long marcaId = 1L;

        ResponseEntity<String> response = marcaController.deletar(marcaId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Marca deletado com sucesso!", response.getBody());
    }
}