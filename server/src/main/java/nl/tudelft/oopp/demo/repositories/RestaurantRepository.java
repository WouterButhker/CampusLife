package nl.tudelft.oopp.demo.repositories;

import java.util.List;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    @Query("SELECT id FROM Restaurant")
    List<Restaurant> findAllRestaurantsId();

    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.name = ?1")
    Integer deleteRestaurantWithName(String myRestaurant);

    /*
    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id = ?1")
    Integer deleteRestaurantWithId(String myRestaurant);
     */

    @Query("SELECT r.id From Restaurant r WHERE r.building = ?1")
    List<String> allRestaurantIdsFromBuilding(Building myBuilding);

    @Query("SELECT COUNT(name) FROM Restaurant")
    Integer countAllRestaurants();
}
