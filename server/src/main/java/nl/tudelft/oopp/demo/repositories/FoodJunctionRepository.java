package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.FoodOrder;
import nl.tudelft.oopp.demo.entities.FoodOrderJunction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodJunctionRepository extends JpaRepository<FoodOrderJunction, Integer> {
}
