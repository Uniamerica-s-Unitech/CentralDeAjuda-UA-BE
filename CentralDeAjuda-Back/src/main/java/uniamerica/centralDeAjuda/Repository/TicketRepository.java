package uniamerica.centralDeAjuda.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uniamerica.centralDeAjuda.Entity.Aluno;
import uniamerica.centralDeAjuda.Entity.Notebook;
import uniamerica.centralDeAjuda.Entity.Ticket;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("FROM Ticket WHERE dataDevolucao IS NULL and ativo = true")
    List<Ticket> findTicketsAbertos();
    @Query("FROM Ticket t WHERE t.dataDevolucao IS NULL AND t.ativo = true AND t.alunoId = :aluno")
    List<Ticket> findTicketsAbertosPorAluno(Aluno aluno);
    @Query("FROM Ticket t WHERE t.dataDevolucao IS NULL AND t.ativo = true AND t.notebookId = :notebook")
    List<Ticket> findTicketsAbertosPorNotebook(Notebook notebook);
    @Query("FROM Ticket WHERE dataDevolucao IS NOT NULL")
    List<Ticket> findHistoricoByDataDevolucao();
}