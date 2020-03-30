package nl.tudelft.oopp.demo.repositories;

import java.util.List;
import nl.tudelft.oopp.demo.entities.food.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface FoodRepository extends JpaRepository<Food, Integer> {

    @Query("SELECT f FROM Food f WHERE f.restaurant = ?1")
    List<Food> allFoodOfRestaurant(int restaurantId);
}
