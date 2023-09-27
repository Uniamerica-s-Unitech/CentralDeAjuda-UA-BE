package uniamerica.centralDeAjuda.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uniamerica.centralDeAjuda.Entity.Marca;
import uniamerica.centralDeAjuda.Entity.Modelo;

import java.util.List;

public interface ModeloRepository extends JpaRepository<Modelo, Long> {
    @Query("FROM Modelo WHERE ativo = true")
    List<Modelo> findModeloByAtivo();

    @Query("FROM Modelo WHERE ativo = true")
    List<Modelo> findModeloByMarcaAtiva(Marca marca);
}