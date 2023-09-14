package uniamerica.centralDeAjuda.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uniamerica.centralDeAjuda.Entity.Aluno;

import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    @Query("FROM Aluno WHERE nome = :nome")
    List<Aluno> findByNome(@Param("nome") final String nome);
    @Query("FROM Aluno WHERE nome = :nome AND id != :id")
    List<Aluno> findByNomePut(@Param("nome") final String nome, @Param("id") final Long id);
    @Query("FROM Aluno WHERE ra = :ra")
    List<Aluno> findByRA(@Param("ra") final String ra);
    @Query("FROM Aluno WHERE ra = :ra AND id != :id")
    List<Aluno> findByRaPut(@Param("ra") final String ra, @Param("id") final Long id);
}