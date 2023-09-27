package uniamerica.centralDeAjuda.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "notebooks", schema = "public")
@Getter @Setter
public class Notebook{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ativo")
    private Boolean ativo = true;

    @Column(name = "patrimonio",nullable = false,unique = true)
    private String patrimonio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modelo_id",nullable = false)
    private Modelo modeloId;
}
