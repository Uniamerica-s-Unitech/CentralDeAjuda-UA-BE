package uniamerica.centralDeAjuda.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "marca", schema = "public")
@Getter @Setter
public class Marca extends AbstractEntity{
    @Column(length = 50, nullable = false)
    private String nome;
}
