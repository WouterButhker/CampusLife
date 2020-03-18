package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.FoodOrderQuantityKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodJunctionRepository extends JpaRepository<FoodOrderQuantityKey, Integer> {
}
