package uniamerica.centralDeAjuda.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "modelo", schema = "public")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Modelo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ativo")
    private Boolean ativo = true;

    @Column(name = "name",length = 50, nullable = false)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marca_id",nullable = false)
    @JsonIgnoreProperties("modelos")
    private Marca marcaId;

    @OneToMany(mappedBy = "modeloId",cascade = CascadeType.ALL)
    @JsonIgnoreProperties("modeloId")
    private List<Notebook> notebooks;
}