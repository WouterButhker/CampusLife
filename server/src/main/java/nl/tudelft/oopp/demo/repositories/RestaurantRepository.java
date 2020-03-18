package nl.tudelft.oopp.demo.repositories;

import java.util.List;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id = ?1")
    Integer deleteRestaurantWithId(Integer myId);

    @Query("SELECT r From Restaurant r WHERE r.building = ?1")
    List<Restaurant> allRestaurantsFromBuilding(Building myBuilding);
}
