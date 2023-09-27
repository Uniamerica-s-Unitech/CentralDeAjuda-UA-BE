package uniamerica.centralDeAjuda.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter@Setter
public class TicketDTO{
    private Long id;

    private Boolean ativo = true;

    private AlunoDTO alunoId;

    private NotebookDTO notebookId;

    private LocalDateTime dataEntrega;

    private LocalDateTime dataDevolucao;
}