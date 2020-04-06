package nl.tudelft.oopp.demo.repositories;

import java.util.Optional;
import nl.tudelft.oopp.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserById(Integer userId);

    Optional<User> findByUsername(String username);

    @Query("SELECT u.id FROM User u WHERE u.username = ?1")
    Integer findIdByUsername(String username);

    @Query("SELECT u.role FROM User u WHERE u.username = ?1")
    String findRoleByUsername(String username);

    @Modifying
    @Query("UPDATE User u SET u.password = :password WHERE u.username = :username")
    int updatePassword(@Param("username") String username, @Param("password") String password);
}
