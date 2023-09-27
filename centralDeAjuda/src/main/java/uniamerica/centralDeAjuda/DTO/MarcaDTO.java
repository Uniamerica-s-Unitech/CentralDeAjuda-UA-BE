package uniamerica.centralDeAjuda.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class MarcaDTO{
    private Long id;

    private Boolean ativo = true;

    private String nome;
}