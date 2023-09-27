package uniamerica.centralDeAjuda.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket", schema = "public")
@Getter @Setter
public class Ticket{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ativo")
    private Boolean ativo = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id",nullable = false)
    private Aluno alunoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patrimonio_id",nullable = false)
    @JsonIgnoreProperties("marcaId")
    private Notebook notebookId;

    @Column(name = "data_de_entrega", nullable = false)
    private LocalDateTime dataEntrega;

    @Column(name = "data_de_devolucao")
    private LocalDateTime dataDevolucao;
}