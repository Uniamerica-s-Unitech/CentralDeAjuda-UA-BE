package uniamerica.centralDeAjuda.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "modelo", schema = "public")
@Getter @Setter
public class Modelo extends AbstractEntity{
    @Column(length = 50, nullable = false)
    private String nome;
    @ManyToOne
    @JoinColumn(name = "marca_id",nullable = false)
    private Marca marca_id;
}