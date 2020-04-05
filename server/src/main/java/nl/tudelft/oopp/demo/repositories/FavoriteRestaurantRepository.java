package nl.tudelft.oopp.demo.repositories;

import java.util.List;
import nl.tudelft.oopp.demo.entities.FavoriteRestaurant;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FavoriteRestaurantRepository extends JpaRepository<FavoriteRestaurant, Integer> {

    @Query("SELECT r From FavoriteRestaurant r WHERE r.user = ?1 AND r.restaurant = ?2")
    FavoriteRestaurant findBy(User user, Restaurant restaurant);

    @Query("SELECT r From FavoriteRestaurant r WHERE r.user = ?1")
    List<FavoriteRestaurant> allFavoriteRestaurantsOfUser(User user);
}