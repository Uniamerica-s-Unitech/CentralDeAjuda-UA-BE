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
import uniamerica.centralDeAjuda.Entity.Marca;
import uniamerica.centralDeAjuda.Repository.MarcaRepository;
import uniamerica.centralDeAjuda.Repository.ModeloRepository;
import uniamerica.centralDeAjuda.Services.MarcaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MarcaServiceTeste {
    @InjectMocks
    private MarcaService marcaService;
    @Mock
    private MarcaRepository marcaRepository;
    @Mock
    private ModeloRepository modeloRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findMarcaByIdTeste(){
        Marca marca = new Marca();
        marca.setId(1L);

        when(marcaRepository.findById(1L)).thenReturn(Optional.of(marca));

        MarcaDTO marcaDTO = marcaService.findMarcaById(1L);

        assertNotNull(marcaDTO);
        assertEquals(1L,marcaDTO.getId());
    }

    @Test
    void findMarcaByIdNULLTeste(){
        when(marcaRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,()-> marcaService.findMarcaById(2L));
    }

    @Test
    void listarMarcasTeste(){
        List<Marca> marcas = new ArrayList<>();
        Marca marca = new Marca();
        marca.setId(1L);
        marca.setAtivo(true);
        marca.setNome("Acer");
        marcas.add(marca);

        when(marcaRepository.findMarcaByAtivo()).thenReturn(marcas);

        List<MarcaDTO> marcaDTOS = marcaService.listar();

        assertNotNull(marcaDTOS);
        assertEquals(marcas.size(),marcaDTOS.size());

        MarcaDTO marcaDTO = marcaDTOS.get(0);
        assertEquals(marca.getId(),marcaDTO.getId());
        assertEquals(marca.getAtivo(),marcaDTO.getAtivo());
        assertEquals(marca.getNome(),marcaDTO.getNome());
    }

    @Test
    void cadastrarMarcaTeste() {
        MarcaDTO marcaDTO = new MarcaDTO();
        marcaDTO.setNome("Acer");

        String mensagem = marcaService.cadastrarMarca(marcaDTO);

        assertEquals("Marca cadastrada com sucesso!",mensagem);

        Marca marcaCadastrada = marcaService.toMarca(marcaDTO);

        assertEquals(marcaDTO.getNome(),marcaCadastrada.getNome());
    }

    @Test
    void editarMarcaTeste(){
        Long marcaid = 1L;
        MarcaDTO marcaDTO = new MarcaDTO();
        marcaDTO.setId(marcaid);
        marcaDTO.setAtivo(true);
        marcaDTO.setNome("Acer");

        Marca marca = new Marca();
        marca.setId(marcaid);
        marca.setAtivo(true);
        marca.setNome("Lenovo");

        when(marcaRepository.existsById(marcaid)).thenReturn(true);
        when(marcaRepository.save(any(Marca.class))).thenReturn(marca);

        String mensagem = marcaService.editarMarca(marcaid,marcaDTO);

        assertEquals("Marca atualizada com sucesso!",mensagem);

        ArgumentCaptor<Marca> marcaCaptor = ArgumentCaptor.forClass(Marca.class);
        verify(marcaRepository).save(marcaCaptor.capture());

        Marca marcaEditada = marcaCaptor.getValue();
        assertEquals(marcaDTO.getId(),marcaEditada.getId());
        assertEquals(marcaDTO.getAtivo(), marcaEditada.getAtivo());
        assertEquals(marcaDTO.getNome(),marcaEditada.getNome());
    }

    @Test
    void editarMarcaNaoExisteTest() {
        Long marcaId = 1L;
        MarcaDTO marcaDTO = new MarcaDTO();
        marcaDTO.setId(marcaId);

        when(marcaRepository.existsById(marcaId)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> marcaService.editarMarca(marcaId, marcaDTO));
    }

    @Test
    void deletarAlunoTeste() {
        Long id = 1L;
        Marca marca = new Marca();
        marca.setId(id);
        marca.setAtivo(true);
        marca.setNome("Dell");

        when(marcaRepository.findById(id)).thenReturn(Optional.of(marca));
        when(modeloRepository.findModeloByMarcaAtiva(marca)).thenReturn(new ArrayList<>());

        assertDoesNotThrow(() -> marcaService.deletar(id));

        ArgumentCaptor<Marca> marcaCaptor = ArgumentCaptor.forClass(Marca.class);
        verify(marcaRepository).save(marcaCaptor.capture());

        Marca marcaDesativada = marcaCaptor.getValue();
        assertFalse(marcaDesativada.getAtivo());
    }
}
