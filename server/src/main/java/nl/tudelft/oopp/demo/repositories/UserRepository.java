package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Integer> {

   // @Query("SELECT u FROM users u WHERE u.username = ?1")
    Optional<Users> findByUsername(String username);
}
