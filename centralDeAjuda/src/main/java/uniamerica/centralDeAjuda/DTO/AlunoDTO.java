package uniamerica.centralDeAjuda.DTO;

import lombok.Getter;
import lombok.Setter;

public class AlunoDTO {
    @Getter@Setter
    private String nome;
    @Getter@Setter
    private Integer RA;

    public AlunoDTO(String nome, Integer RA) {
        this.nome = nome;
        this.RA = RA;
    }
}
