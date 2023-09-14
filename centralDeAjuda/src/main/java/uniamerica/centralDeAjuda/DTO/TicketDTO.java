package uniamerica.centralDeAjuda.DTO;

import lombok.Getter;
import lombok.Setter;
import uniamerica.centralDeAjuda.Entity.Aluno;
import uniamerica.centralDeAjuda.Entity.Notebook;

import java.time.LocalDateTime;

@Getter@Setter
public class TicketDTO extends AbstractEntityDTO{
    private Aluno alunoId;

    private Notebook notebookId;

    private LocalDateTime dataEntrega;

    private LocalDateTime dataDevolucao;
}
