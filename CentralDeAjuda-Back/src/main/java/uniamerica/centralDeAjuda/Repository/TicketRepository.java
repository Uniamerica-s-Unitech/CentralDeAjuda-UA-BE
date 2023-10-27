package uniamerica.centralDeAjuda.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uniamerica.centralDeAjuda.Entity.Aluno;
import uniamerica.centralDeAjuda.Entity.Notebook;
import uniamerica.centralDeAjuda.Entity.Ticket;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("FROM Ticket WHERE dataDevolucao IS NULL")
    List<Ticket> findTicketsAbertos();
    @Query("FROM Ticket WHERE dataDevolucao IS NULL AND alunoId = :aluno")
    List<Ticket> findTicketsAbertosPorAluno(Aluno aluno);
    @Query("FROM Ticket WHERE dataDevolucao IS NULL AND notebookId = :notebook")
    List<Ticket> findTicketsAbertosPorNotebook(Notebook notebook);
    @Query("FROM Ticket WHERE dataDevolucao IS NOT NULL")
    List<Ticket> findHistoricoByDataDevolucao();
}