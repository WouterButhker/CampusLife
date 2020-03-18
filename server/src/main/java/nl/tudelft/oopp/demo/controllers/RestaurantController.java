package nl.tudelft.oopp.demo.controllers;

import java.util.List;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Food;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.repositories.FoodRepository;
import nl.tudelft.oopp.demo.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private FoodRepository foodRepository;

    @GetMapping(path = "/all")
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    /**
     * Adds a new restaurant to the database.
     *
     * @param name         the actual (full) name
     * @param building the number of the building
     * @param description format aa:bb-cc:dd
     * @return Saved
     */
    @GetMapping(path = "/add")
    public @ResponseBody
    String addNewRestaurant(@RequestParam int id,
                            @RequestParam String name,
                            @RequestParam Building building,
                            @RequestParam String description) {
        Restaurant restaurant = new Restaurant(id, name, building, description);
        restaurantRepository.save(restaurant);
        return "Saved";
    }

    @Transactional
    @GetMapping(path = "/delete")
    public Integer deleteRestaurant(@RequestParam String name) {
        return restaurantRepository.deleteRestaurantWithName(name);
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<Integer> deleteRestaurant(@PathVariable Integer id) {
        boolean exists = restaurantRepository.findById(id).isPresent();

        if (!exists) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        restaurantRepository.deleteById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping(path = "/count")
    public Integer countRestaurants() {
        return restaurantRepository.countAllRestaurants();
    }

    /**
     * Returns all food of the restaurant.
     * @param id the id of the restaurant
     * @return a list of all food of the restaurant
     */
    @GetMapping(value = "/{id}/food")
    List<Food> getAllFood(@PathVariable Integer id) {
        return foodRepository.allFoodOfRestaurant(id);
    }

    /**
     * Adds a new restaurant to the database.
     * @param restaurant the restaurant
     * @return Saved
     */
    @PostMapping(path = "/addRestaurant",
            consumes = "application/json",
            produces = "application/json")
    Restaurant addRestaurant(@RequestBody Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @GetMapping(path = "/getAllRestaurantsFromBuilding")
    public List<Restaurant> getAllRestaurantsFromBuilding(@RequestParam Building building) {
        return restaurantRepository.allRestaurantsFromBuilding(building);
    }

}
