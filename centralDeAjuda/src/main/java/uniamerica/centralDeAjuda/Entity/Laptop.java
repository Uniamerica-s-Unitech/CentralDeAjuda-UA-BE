package uniamerica.centralDeAjuda.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "laptop", schema = "public")
@Getter @Setter
public class Laptop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String patrimonio;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Modelo modelo_id;
}
