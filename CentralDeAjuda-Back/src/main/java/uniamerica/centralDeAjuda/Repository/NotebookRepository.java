package uniamerica.centralDeAjuda.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uniamerica.centralDeAjuda.Entity.Modelo;
import uniamerica.centralDeAjuda.Entity.Notebook;

import java.util.List;

public interface NotebookRepository extends JpaRepository<Notebook, Long> {
    @Query("FROM Notebook WHERE ativo = true")
    List<Notebook> findNotebookByAtivo();
    @Query("SELECT a FROM Notebook a WHERE a NOT IN (SELECT t.notebookId FROM Ticket t WHERE t.dataDevolucao IS NULL AND t.ativo = true)")
    List<Notebook> findNotebookByAtivoSemVinculo();
    @Query("FROM Notebook WHERE ativo = true AND modeloId = :modelo")
    List<Notebook> findNotebookByModeloAtivo(Modelo modelo);
    @Query("FROM Notebook WHERE patrimonio = :patrimonio")
    List<Notebook> findByIdPatrimonio(@Param("patrimonio") final String patrimonio);
    @Query("FROM Notebook WHERE patrimonio = :patrimonio AND id != :id")
    List<Notebook> findByIdPatrimonioEditar(@Param("patrimonio") final String patrimonio, @Param("id") final Long id);
}
