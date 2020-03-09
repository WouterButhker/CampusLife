package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    @Query("SELECT name FROM Restaurant") // name FROM restaurant;
    List<String> findAllRestaurantsName();

    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.name = ?1")
    Integer deleteRestaurantWithName(String myRestaurant);

    @Query("SELECT r.name From Restaurant r WHERE r.buildingCode = ?1")
    List<String> allRoomNamesFromBuilding(Integer myBuilding);

    @Query("SELECT COUNT(name) FROM Restaurant")
    Integer countAllRestaurants();
}
