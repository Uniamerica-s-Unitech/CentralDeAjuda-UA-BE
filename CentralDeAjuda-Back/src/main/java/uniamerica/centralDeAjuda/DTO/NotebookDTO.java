package uniamerica.centralDeAjuda.DTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotebookDTO{
    private Long id;

    @NotNull(message = "Patrimonio inválido")
    @NotBlank(message = "O patrimonio do notebook é obrigatório")
    private String patrimonio;

    @NotNull(message = "ID inválido")
    @NotBlank(message = "O ID do modelo é obrigatório")
    private ModeloDTO modeloId;
}