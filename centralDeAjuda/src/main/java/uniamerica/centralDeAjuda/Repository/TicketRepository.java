package uniamerica.centralDeAjuda.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uniamerica.centralDeAjuda.Entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
