package nl.tudelft.oopp.demo.repositories;

import java.util.List;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.entities.food.FoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FoodOrderRepository extends JpaRepository<FoodOrder, Integer> {
    @Query("SELECT r From FoodOrder r WHERE r.user = ?1")
    List<FoodOrder> allFoodOrdersOfUser(User user);
}
