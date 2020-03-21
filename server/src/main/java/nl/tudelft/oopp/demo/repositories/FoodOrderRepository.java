package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.reservation.food.FoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodOrderRepository extends JpaRepository<FoodOrder, Integer> {

}
