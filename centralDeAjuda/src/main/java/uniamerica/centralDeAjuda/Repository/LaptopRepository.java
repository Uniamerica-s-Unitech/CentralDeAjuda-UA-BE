package uniamerica.centralDeAjuda.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uniamerica.centralDeAjuda.Entity.Laptop;

import java.util.List;

public interface LaptopRepository extends JpaRepository<Laptop, Long> {
    @Query("FROM Laptop WHERE patrimonio = :patrimonio")
    List<Laptop> findByIdPatrimonio(@Param("patrimonio") final String patrimonio);
    @Query("FROM Laptop WHERE patrimonio = :patrimonio AND id != :id")
    List<Laptop> findByIdPatrimonioPut(@Param("patrimonio") final String patrimonio, @Param("id") final Long id);

}
