package uniamerica.centralDeAjuda.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ticket", schema = "public")
@Getter
@Setter
public class Ticket {
}
