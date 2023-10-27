package uniamerica.centralDeAjuda.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModeloDTO{
    private Long id;

    private Boolean ativo = true;

    @NotNull(message = "Nome inválido")
    @NotBlank(message = "O nome do modelo é obrigatório")
    @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres")
    private String nome;

    @NotNull(message = "ID inválido")
    @NotBlank(message = "O ID da marca é obrigatório")
    private MarcaDTO marcaId;
}