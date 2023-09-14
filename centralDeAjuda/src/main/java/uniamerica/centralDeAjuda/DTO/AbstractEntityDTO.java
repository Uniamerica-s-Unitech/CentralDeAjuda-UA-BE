package uniamerica.centralDeAjuda.DTO;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter@Setter
public abstract class AbstractEntityDTO {
    private Long id;
    private LocalDateTime cadastro;
    private LocalDateTime atualizacao;
    private Boolean ativo;

    @PrePersist
    private void prePersist(){
        this.cadastro = LocalDateTime.now();
        this.ativo = true;
    }

    @PreUpdate
    private void preUpdaete(){
        this.atualizacao = LocalDateTime.now();
    }
}
