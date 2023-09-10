package uniamerica.centralDeAjuda.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uniamerica.centralDeAjuda.Entity.Aluno;

import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    @Query("FROM Aluno WHERE nome = :nome")
    List<Aluno> findByNome(@Param("nome") final String nome);
    @Query("FROM Aluno WHERE RA = :RA")
    List<Aluno> findByRA(@Param("RA") final String RA);
}
