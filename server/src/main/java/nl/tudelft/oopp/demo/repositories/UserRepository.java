package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.config.SecurityConfiguration;
import nl.tudelft.oopp.demo.entities.User;
import org.hibernate.annotations.SQLInsert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public interface UserRepository extends JpaRepository<User, Integer> {

//    @SQLInsert(sql = "INSERT INTO user(username, password, role)" +
//            "VALUES (admin, "  + ")")
//    @Query("INSERT INTO user ")
//    void addUsers();


}
