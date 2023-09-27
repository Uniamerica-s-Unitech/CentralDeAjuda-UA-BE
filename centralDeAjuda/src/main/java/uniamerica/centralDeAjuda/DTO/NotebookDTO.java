package uniamerica.centralDeAjuda.DTO;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class NotebookDTO{
    private Long id;

    private Boolean ativo = true;

    private String patrimonio;

    private ModeloDTO modeloId;
}