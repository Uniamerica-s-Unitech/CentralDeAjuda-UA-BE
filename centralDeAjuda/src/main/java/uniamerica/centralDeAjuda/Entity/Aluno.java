package uniamerica.centralDeAjuda.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "aluno", schema = "public")
@Getter@Setter
public class Aluno extends AbstractEntity{
    @Column(length = 100, nullable = false)
    private String nome;
    @Column(length = 8,nullable = false,unique = true)
    private String ra;
}