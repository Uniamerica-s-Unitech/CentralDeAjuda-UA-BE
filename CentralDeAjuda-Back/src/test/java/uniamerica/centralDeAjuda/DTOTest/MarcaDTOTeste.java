package uniamerica.centralDeAjuda.DTOTest;

import org.junit.Test;
import uniamerica.centralDeAjuda.DTO.MarcaDTO;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MarcaDTOTeste {
    @Test
    public void testMarcaDTOValidation() {
        // Crie uma instância de MarcaDTO para testar
        MarcaDTO marcaDTO = new MarcaDTO();
        marcaDTO.setId(1L);
        marcaDTO.setAtivo(true);
        marcaDTO.setNome("Marca1");

        // Verifique as anotações de validação individualmente
        assertThat(marcaDTO.getId()).isNotNull();
        assertThat(marcaDTO.getAtivo()).isTrue();
        assertThat(marcaDTO.getNome()).isNotBlank().hasSizeBetween(2, 50);
    }
}
