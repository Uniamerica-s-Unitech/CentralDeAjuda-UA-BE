package uniamerica.centralDeAjuda.ServiceTest;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import uniamerica.centralDeAjuda.DTO.MarcaDTO;
import uniamerica.centralDeAjuda.DTO.ModeloDTO;
import uniamerica.centralDeAjuda.Entity.Marca;
import uniamerica.centralDeAjuda.Entity.Modelo;
import uniamerica.centralDeAjuda.Repository.MarcaRepository;
import uniamerica.centralDeAjuda.Repository.ModeloRepository;
import uniamerica.centralDeAjuda.Repository.NotebookRepository;
import uniamerica.centralDeAjuda.Services.ModeloService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ModeloServiceTeste {

    @InjectMocks
    private ModeloService modeloService;

    @Mock
    private ModeloRepository modeloRepository;

    @Mock
    private MarcaRepository marcaRepository;

    @Mock
    private NotebookRepository notebookRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findModeloByIdTest() {
        Modelo modelo = new Modelo();
        modelo.setId(1L);

        Marca marca = new Marca();
        marca.setId(1L);
        modelo.setMarcaId(marca);

        when(modeloRepository.findById(1L)).thenReturn(Optional.of(modelo));

        ModeloDTO modeloDTO = modeloService.findModeloById(1L);

        assertNotNull(modeloDTO);
        assertEquals(1L, modeloDTO.getId());
        assertNotNull(modeloDTO.getMarcaId());
    }

    @Test
    void findModeloByIdNULLTeste(){
        when(modeloRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,()-> modeloService.findModeloById(2L));
    }

    @Test
    void listarModelosTest() {
        List<Modelo> modelos = new ArrayList<>();
        Modelo modelo = new Modelo();
        modelo.setId(1L);
        modelo.setAtivo(true);
        modelo.setNome("Modelo1");

        Marca marca = new Marca();
        marca.setId(1L);
        modelo.setMarcaId(marca);

        modelos.add(modelo);

        when(modeloRepository.findModeloByAtivo()).thenReturn(modelos);

        List<ModeloDTO> modeloDTOS = modeloService.listar();

        assertNotNull(modeloDTOS);
        assertEquals(modelos.size(), modeloDTOS.size());

        ModeloDTO modeloDTO = modeloDTOS.get(0);
        assertEquals(modelo.getId(), modeloDTO.getId());
        assertEquals(modelo.getAtivo(), modeloDTO.getAtivo());
        assertEquals(modelo.getNome(), modeloDTO.getNome());
        assertEquals(modelo.getMarcaId().getId(), modeloDTO.getMarcaId().getId());
    }

    @Test
    void cadastrarModeloTeste() {
        ModeloDTO modeloDTO = new ModeloDTO();
        modeloDTO.setNome("Nitro 5");
        modeloDTO.setMarcaId(new MarcaDTO(1L,true,"Acer"));

        Marca marca = new Marca();
        marca.setId(1L);
        marca.setAtivo(true);
        marca.setNome("Acer");

        when(marcaRepository.findById(1L)).thenReturn(Optional.of(marca));

        String mensagem = modeloService.cadastrarModelo(modeloDTO);

        assertEquals("Modelo cadastrado com sucesso!",mensagem);

        Modelo modeloCadastrado = modeloService.toModelo(modeloDTO);

        assertEquals(modeloDTO.getNome(),modeloCadastrado.getNome());
        assertEquals(modeloDTO.getMarcaId().getId(),modeloCadastrado.getMarcaId().getId());
    }

    @Test
    void editarModeloTeste(){
        Long modeloid = 1L;
        ModeloDTO modeloDTO = new ModeloDTO();
        modeloDTO.setId(modeloid);
        modeloDTO.setAtivo(true);
        modeloDTO.setNome("Modelo1");
        modeloDTO.setMarcaId(new MarcaDTO(2L,true,"Dell"));


        Modelo modelo = new Modelo();
        modelo.setId(modeloid);
        modelo.setAtivo(true);
        modelo.setNome("Modelo2");
        modelo.setMarcaId(new Marca(2L,true,"Dell",null));


        when(modeloRepository.existsById(modeloid)).thenReturn(true);
        when(modeloRepository.save(any(Modelo.class))).thenReturn(modelo);
        when(marcaRepository.findById(2L)).thenReturn(Optional.of(new Marca(2L, true, "Marca2",null)));


        String mensagem = modeloService.editarModelo(modeloid,modeloDTO);

        assertEquals("Modelo atualizado com sucesso!",mensagem);

        ArgumentCaptor<Modelo> modeloCaptor = ArgumentCaptor.forClass(Modelo.class);
        verify(modeloRepository).save(modeloCaptor.capture());

        Modelo modeloEditado = modeloCaptor.getValue();
        assertEquals(modeloDTO.getId(),modeloEditado.getId());
        assertEquals(modeloDTO.getAtivo(), modeloEditado.getAtivo());
        assertEquals(modeloDTO.getNome(),modeloEditado.getNome());
        assertEquals(modeloDTO.getMarcaId().getId(),modeloEditado.getMarcaId().getId());
    }

    @Test
    void editarModeloNaoExisteTest() {
        Long modeloId = 1L;
        ModeloDTO modeloDTO = new ModeloDTO();
        modeloDTO.setId(modeloId);

        when(modeloRepository.existsById(modeloId)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> modeloService.editarModelo(modeloId, modeloDTO));
    }

    @Test
    void deletarAlunoTeste() {
        Long id = 1L;
        Modelo modelo = new Modelo();
        modelo.setId(id);
        modelo.setAtivo(true);
        modelo.setNome("Dell");

        Marca marca = new Marca();
        modelo.setMarcaId(marca);

        when(modeloRepository.findById(id)).thenReturn(Optional.of(modelo));
        when(notebookRepository.findNotebookByModeloAtivo(modelo)).thenReturn(new ArrayList<>());

        assertDoesNotThrow(() -> modeloService.deletar(id));

        ArgumentCaptor<Modelo> modeloCaptor = ArgumentCaptor.forClass(Modelo.class);
        verify(modeloRepository).save(modeloCaptor.capture());

        Modelo modeloDesativada = modeloCaptor.getValue();
        assertFalse(modeloDesativada.getAtivo());
    }
}
