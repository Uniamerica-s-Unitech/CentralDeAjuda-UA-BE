package uniamerica.centralDeAjuda.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ModeloDTO{
    private Long id;

    private Boolean ativo = true;

    private String nome;

    private MarcaDTO marcaId;
}