package uniamerica.centralDeAjuda.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
//@MappedSuperclass
@Entity
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;
    @ManyToOne
    @JoinColumn(name = "notebook_id")
    private Notebook notebook;
    @ManyToOne
    @JoinColumn(name = "marca_id")
    @JsonIgnoreProperties("modelos")
    private Marca marca;
    @ManyToOne
    @JoinColumn(name = "modelo_id")
    private Modelo modelo;
    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;
    private String userCriacao;
    private String userAlteracao;
    private String userExclusao;
    private String userFinalizacao;
    private LocalDateTime dataHoraCriacao;
    private LocalDateTime dataHoraAlteracao;
    private LocalDateTime dataHoraExclusao;
    private LocalDateTime dataHoraFinalizacao;

}
