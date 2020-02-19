package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.Quote;
import nl.tudelft.oopp.demo.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Integer> {}
