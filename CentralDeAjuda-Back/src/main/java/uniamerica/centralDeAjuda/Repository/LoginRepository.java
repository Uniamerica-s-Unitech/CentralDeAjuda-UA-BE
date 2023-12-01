package uniamerica.centralDeAjuda.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uniamerica.centralDeAjuda.Entity.User;

import java.util.List;
import java.util.Optional;


public interface LoginRepository extends JpaRepository<User, Long>{
    @Query("FROM User WHERE ativo = true AND username = :login")
    public Optional<User> findByUsername(String login);

    @Query("FROM User WHERE ativo = true")
    List<User> findUserByAtivo();

    @Query(value = "SELECT password FROM usuario WHERE id = :id",nativeQuery = true)
    String findSenhaById(Long id);

}