package uniamerica.centralDeAjuda.Repository;

import java.util.List;
import java.util.Optional;
import uniamerica.centralDeAjuda.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



public interface LoginRepository extends JpaRepository<User, Long>{
    @Query("FROM User WHERE ativo = true AND username = :login")
    public Optional<User> findByUsername(String login);

    @Query("FROM User WHERE ativo = true")
    List<User> findUserByAtivo();

    @Query(value = "SELECT password FROM usuario WHERE id = :id",nativeQuery = true)
    String findSenhaById(Long id);

}