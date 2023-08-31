package uniamerica.centralDeAjuda.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uniamerica.centralDeAjuda.Entity.Laptop;

public interface LaptopRepository extends JpaRepository<Laptop, Long> {
}
