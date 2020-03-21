package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.reservation.food.FoodOrderQuantity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodOrderQuantityRepository extends JpaRepository<FoodOrderQuantity, Integer> {

}
