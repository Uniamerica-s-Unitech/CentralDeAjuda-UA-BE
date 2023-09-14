package uniamerica.centralDeAjuda.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "notebooks", schema = "public")
@Getter @Setter
public class Notebook extends AbstractEntity{
    @Column(nullable = false,unique = true)
    private String patrimonio;

    @ManyToOne
    @JoinColumn(name = "modelo_id",nullable = false)
    private Modelo modeloId;
}
