package uniamerica.centralDeAjuda.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uniamerica.centralDeAjuda.Entity.Aluno;
import uniamerica.centralDeAjuda.Entity.Ticket;

import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    @Query("FROM Aluno WHERE ativo = true")
    List<Aluno> findAlunoByAtivo();

    @Query("SELECT a FROM Aluno a WHERE a NOT IN (SELECT t.alunoId FROM Ticket t WHERE t.dataDevolucao IS NULL AND t.ativo = true)")
    List<Aluno> findAlunoByAtivoSemVinculo();

    @Query("FROM Aluno WHERE ra = :ra")
    List<Aluno> findByRA(@Param("ra") final String ra);
    @Query("FROM Aluno WHERE ra = :ra AND id != :id")
    List<Aluno> findByRaEditar(@Param("ra") final String cpf, @Param("id") final Long id);
}