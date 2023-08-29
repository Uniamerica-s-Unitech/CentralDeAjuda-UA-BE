package uniamerica.centralDeAjuda.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uniamerica.centralDeAjuda.Entity.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
}
