package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

   // @Query("SELECT u FROM users u WHERE u.username = ?1")
    Optional<User> findByUsername(String username);
}
