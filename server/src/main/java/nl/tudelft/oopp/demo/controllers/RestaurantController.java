package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.Food;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.repositories.FoodRepository;
import nl.tudelft.oopp.demo.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private FoodRepository foodRepository;

    @GetMapping
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    /**
     * Adds a new restaurant to the database.
     *
     * @param name         the actual (full) name
     * @param buildingCode the number of the building
     * @param openingHours format aa:bb-cc:dd
     * @return Saved
     */
    @GetMapping(path = "/add")
    public @ResponseBody
    String addNewRestaurant(@RequestParam String name,
                            @RequestParam Integer buildingCode,
                            @RequestParam String openingHours) {
        Restaurant restaurant = new Restaurant(name, buildingCode, openingHours);
        restaurantRepository.save(restaurant);
        return "Saved";
    }

    @Transactional
    @GetMapping(path = "/delete")
    public Integer deleteRestaurant(@RequestParam String name) {
        return restaurantRepository.deleteRestaurantWithName(name);
    }

    @GetMapping(path = "/count")
    public Integer countBuildings() {
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
    @PostMapping(consumes = "application/json", produces = "application/json")
    Restaurant addRestaurant(@RequestBody Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
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
}
