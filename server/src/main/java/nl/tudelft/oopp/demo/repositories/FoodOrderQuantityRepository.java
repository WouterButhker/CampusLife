package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.entities.food.FoodOrder;
import nl.tudelft.oopp.demo.entities.food.FoodOrderQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FoodOrderQuantityRepository extends JpaRepository<FoodOrderQuantity, Integer> {
    @Query("SELECT r From FoodOrderQuantity r WHERE r.foodOrder = ?1")
    List<FoodOrderQuantity> allFoodQuantitiesOfOrder(FoodOrder foodOrder);
}
