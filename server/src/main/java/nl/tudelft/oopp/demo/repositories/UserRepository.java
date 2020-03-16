package nl.tudelft.oopp.demo.repositories;

import java.util.Optional;
import nl.tudelft.oopp.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    @Query("SELECT u.id FROM User u WHERE u.username = ?1")
    Integer findIdByUsername(String username);

    @Query("SELECT u.role FROM User u WHERE u.username = ?1")
    String findRoleByUsername(String username);
}
