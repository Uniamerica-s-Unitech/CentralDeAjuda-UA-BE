package uniamerica.centralDeAjuda.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket", schema = "public")
@Getter
@Setter
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id",nullable = false,unique = true)
    private Aluno alunoId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patrimonio_id",nullable = false,unique = true)
    private Notebook notebookId;

    @Column(name = "data_de_entrega", nullable = false)
    private LocalDateTime dataEntrega;

    @Column(name = "data_de_devolucao")
    private LocalDateTime dataDevolucao;

}
