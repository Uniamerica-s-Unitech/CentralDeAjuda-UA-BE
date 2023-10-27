package uniamerica.centralDeAjuda.DTOTest;

import org.junit.Test;
import uniamerica.centralDeAjuda.DTO.MarcaDTO;
import uniamerica.centralDeAjuda.DTO.ModeloDTO;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ModeloDTOTeste {
    @Test
    public void testModeloDTOValidation() {
        // Crie uma instância de ModeloDTO para testar
        ModeloDTO modeloDTO = new ModeloDTO();
        modeloDTO.setId(1L);
        modeloDTO.setAtivo(true);
        modeloDTO.setNome("Modelo1");

        // Crie uma instância de MarcaDTO e defina seus atributos
        MarcaDTO marcaDTO = new MarcaDTO();
        marcaDTO.setId(1L);
        marcaDTO.setAtivo(true);
        marcaDTO.setNome("Marca1");

        // Defina o atributo marcaId do ModeloDTO
        modeloDTO.setMarcaId(marcaDTO);

        // Verifique as anotações de validação individualmente
        assertThat(modeloDTO.getId()).isNotNull();
        assertThat(modeloDTO.getAtivo()).isTrue();
        assertThat(modeloDTO.getNome()).isNotBlank().hasSizeBetween(2, 50);
        assertThat(modeloDTO.getMarcaId()).isNotNull();
    }
}
