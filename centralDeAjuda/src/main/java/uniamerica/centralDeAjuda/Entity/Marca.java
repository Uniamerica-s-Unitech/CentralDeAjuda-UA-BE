package uniamerica.centralDeAjuda.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "marca", schema = "public")
@Getter @Setter
public class Marca{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ativo")
    private Boolean ativo = true;

    @Column(length = 50, nullable = false)
    private String nome;

    @OneToMany(mappedBy = "marcaId",cascade = CascadeType.ALL)
    @JsonIgnoreProperties("modelos")
    private List<Modelo> modelos;
}