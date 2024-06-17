package uniamerica.centralDeAjuda.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO{
    private Long id;

    @NotNull(message = "ID inválido")
    @NotBlank(message = "O ID do aluno é obrigatório")
    private AlunoDTO alunoId;

    @NotNull(message = "ID inválido")
    @NotBlank(message = "O ID do notebook é obrigatório")
    private NotebookDTO notebookId;

    @NotNull(message = "Data inválida")
    @NotBlank(message = "A data de entrega é obrigatório")
    private Timestamp dataEntrega;

    private Timestamp dataDevolucao;

    private String observacao;
}