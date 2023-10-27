package uniamerica.centralDeAjuda.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlunoDTO{
    private Long id;

    private Boolean ativo = true;

    @NotNull(message = "Nome inválido")
    @NotBlank(message = "O nome do aluno é obrigatório")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @NotNull(message = "RA inválido")
    @NotBlank(message = "O RA do aluno é obrigatório")
    @Size(min = 6, max = 6, message = "O RA deve ter entre 6 numeros")
    private String ra;
}