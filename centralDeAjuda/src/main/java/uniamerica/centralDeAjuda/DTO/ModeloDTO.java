package uniamerica.centralDeAjuda.DTO;

import lombok.Getter;
import lombok.Setter;
import uniamerica.centralDeAjuda.Entity.Marca;

@Getter@Setter
public class ModeloDTO extends AbstractEntityDTO{
    private String nome;

    private Marca marca_id;
}
