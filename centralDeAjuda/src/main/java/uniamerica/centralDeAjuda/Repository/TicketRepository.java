package uniamerica.centralDeAjuda.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uniamerica.centralDeAjuda.Entity.Aluno;
import uniamerica.centralDeAjuda.Entity.Notebook;
import uniamerica.centralDeAjuda.Entity.Ticket;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("FROM Ticket WHERE dataDevolucao IS NULL")
    List<Ticket> findTicketsAbertos();

    @Query("FROM Ticket WHERE alunoId = :alunoId AND dataDevolucao IS NULL")
    List<Ticket> findByAluno(@Param("alunoId") final Aluno id);

    @Query("FROM Ticket WHERE alunoId = :alunoId AND dataDevolucao IS NULL AND id != :id")
    List<Ticket> findByAlunoPut(@Param("alunoId") final Aluno id,@Param("id") final Long Id);

    @Query("FROM Ticket WHERE notebookId = :notebookId AND dataDevolucao IS NULL")
    List<Ticket> findByNotebook(@Param("notebookId") final Notebook id);

    @Query("FROM Ticket WHERE notebookId = :notebookId AND dataDevolucao IS NULL AND id != :id")
    List<Ticket> findByNotebookPut(@Param("notebookId") final Notebook id,@Param("id") final Long Id);

    @Query("FROM Ticket WHERE dataDevolucao IS NOT NULL AND dataDevolucao <= :dataDevolucao")
    List<Ticket> findHistoricoByDataDevolucao(@Param("dataDevolucao") LocalDateTime dataDevolucao);
}