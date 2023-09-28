package uniamerica.centralDeAjuda.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class MarcaDTO{
    private Long id;

    private Boolean ativo = true;

    @NotNull(message = "Nome inválido")
    @NotBlank(message = "O nome do marca é obrigatório")
    @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres")
    private String nome;
}