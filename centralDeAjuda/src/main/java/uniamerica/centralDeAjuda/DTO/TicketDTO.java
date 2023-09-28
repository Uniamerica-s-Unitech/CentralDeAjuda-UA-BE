package uniamerica.centralDeAjuda.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter@Setter
public class TicketDTO{
    private Long id;

    private Boolean ativo = true;

    @NotNull(message = "ID inválido")
    @NotBlank(message = "O ID do aluno é obrigatório")
    private AlunoDTO alunoId;

    @NotNull(message = "ID inválido")
    @NotBlank(message = "O ID do notebook é obrigatório")
    private NotebookDTO notebookId;

    @NotNull(message = "Data inválida")
    @NotBlank(message = "A data de entrega é obrigatório")
    private LocalDateTime dataEntrega;

    private LocalDateTime dataDevolucao;
}