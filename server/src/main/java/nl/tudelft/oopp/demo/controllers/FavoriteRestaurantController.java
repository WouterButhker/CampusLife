package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.FavoriteRestaurant;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.FavoriteRestaurantRepository;
import nl.tudelft.oopp.demo.repositories.RestaurantRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/favoriterestaurants")
public class FavoriteRestaurantController {

    @Autowired
    private FavoriteRestaurantRepository favoriteRestaurantRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    /**
     * Adds a new favorite restaurant to the database.
     * @param favoriteRestaurant the favorite restaurant
     * @return the favorite restaurant instance
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    FavoriteRestaurant addFavoriteRestaurant(@RequestBody FavoriteRestaurant favoriteRestaurant) {
        User user = favoriteRestaurant.getUser();
        Restaurant restaurant = favoriteRestaurant.getRestaurant();

        FavoriteRestaurant found = favoriteRestaurantRepository.findBy(user, restaurant);

        if (found != null) {
            return found;
        } else {
            return favoriteRestaurantRepository.save(favoriteRestaurant);
        }
    }

    /**
     * Get favorite restaurants of user.
     * @param userId the user id
     * @return a list of favorite restaurants of userId
     */
    @GetMapping(value = "/user/{userId}")
    List<FavoriteRestaurant> getFavoriteRestaurants(@PathVariable Integer userId) {
        User user = userRepository.findById(userId).get();

        return favoriteRestaurantRepository.allFavoriteRestaurantsOfUser(user);
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<Integer> deleteFavoriteRestaurant(@PathVariable Integer id) {
        boolean exists = favoriteRestaurantRepository.findById(id).isPresent();

        if (!exists) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        favoriteRestaurantRepository.deleteById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping(value = "/user/{userId}/restaurant/{restaurantId}")
    FavoriteRestaurant getFavoriteRestaurant(
            @PathVariable Integer userId,
            @PathVariable Integer restaurantId) {
        User user = userRepository.findById(userId).get();
        Restaurant restaurant = restaurantRepository.findById(restaurantId).get();

        return favoriteRestaurantRepository.findBy(user, restaurant);
    }
}
