package uniamerica.centralDeAjuda.DTO;
import lombok.Getter;
import lombok.Setter;
import uniamerica.centralDeAjuda.Entity.Modelo;

@Getter@Setter
public class NotebookDTO extends AbstractEntityDTO{
    private String patrimonio;

    private Modelo modeloId;
}
