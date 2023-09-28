package uniamerica.centralDeAjuda.DTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class NotebookDTO{
    private Long id;

    private Boolean ativo = true;

    @NotNull(message = "Patrimonio inválido")
    @NotBlank(message = "O patrimonio do notebook é obrigatório")
    private String patrimonio;

    @NotNull(message = "ID inválido")
    @NotBlank(message = "O ID do modelo é obrigatório")
    private ModeloDTO modeloId;
}