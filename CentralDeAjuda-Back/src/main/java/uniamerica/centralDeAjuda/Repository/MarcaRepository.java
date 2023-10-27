package uniamerica.centralDeAjuda.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uniamerica.centralDeAjuda.Entity.Marca;

import java.util.List;

public interface MarcaRepository extends JpaRepository<Marca, Long> {
    @Query("FROM Marca WHERE ativo = true")
    List<Marca> findMarcaByAtivo();
    @Query("FROM Marca WHERE nome = :nome")
    List<Marca> findByNomeMarca(@Param("nome") final String nome);
}
