package uniamerica.centralDeAjuda.DTOTest;

import org.junit.Test;
import uniamerica.centralDeAjuda.DTO.ModeloDTO;
import uniamerica.centralDeAjuda.DTO.NotebookDTO;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class NotebookDTOTeste {
    @Test
    public void testNotebookDTOValidation() {
        // Crie uma instância de NotebookDTO para testar
        NotebookDTO notebookDTO = new NotebookDTO();
        notebookDTO.setId(1L);
        notebookDTO.setAtivo(true);
        notebookDTO.setPatrimonio("Patrimonio1");

        // Crie uma instância de ModeloDTO e defina seus atributos
        ModeloDTO modeloDTO = new ModeloDTO();
        modeloDTO.setId(1L);
        modeloDTO.setAtivo(true);
        modeloDTO.setNome("Modelo1");

        // Defina o atributo modeloId do NotebookDTO
        notebookDTO.setModeloId(modeloDTO);

        // Verifique as anotações de validação individualmente
        assertThat(notebookDTO.getId()).isNotNull();
        assertThat(notebookDTO.getAtivo()).isTrue();
        assertThat(notebookDTO.getPatrimonio()).isNotBlank();
        assertThat(notebookDTO.getModeloId()).isNotNull();
    }
}
