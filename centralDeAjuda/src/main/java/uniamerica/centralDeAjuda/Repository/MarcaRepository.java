package uniamerica.centralDeAjuda.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uniamerica.centralDeAjuda.Entity.Marca;

public interface MarcaRepository extends JpaRepository<Marca, Long> {
}
