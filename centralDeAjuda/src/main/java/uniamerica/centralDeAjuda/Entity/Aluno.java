package uniamerica.centralDeAjuda.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "aluno", schema = "public")
public class Aluno {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter@Setter
    @Column(length = 100, nullable = false)
    private String nome;
    @Getter@Setter
    @Column(length = 6,nullable = false,unique = true)
    private Integer RA;

}
