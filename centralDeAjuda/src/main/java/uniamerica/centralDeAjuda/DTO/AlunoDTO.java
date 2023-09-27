package uniamerica.centralDeAjuda.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class AlunoDTO{
    private Long id;

    private Boolean ativo = true;

    private String nome;

    private String ra;
}