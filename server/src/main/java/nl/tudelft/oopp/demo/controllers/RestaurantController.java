package nl.tudelft.oopp.demo.controllers;

import java.util.List;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.entities.food.Food;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.FoodRepository;
import nl.tudelft.oopp.demo.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    /**
     * Adds a new restaurant to the database.
     * @param restaurant the restaurant
     * @return Saved
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    Restaurant addRestaurant(@RequestBody Restaurant restaurant) {
        Building building = buildingRepository.findById(restaurant.getBuildingCode()).get();
        restaurant.setBuilding(building);
        return restaurantRepository.save(restaurant);
    }

    @GetMapping
    List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    @Transactional
    @GetMapping(path = "/delete")
    public Integer deleteRestaurantWithId(@RequestParam Integer id) {
        return restaurantRepository.deleteRestaurantWithId(id);
    }

    /**
     * Updates a restaurant.
     *
     * @param id The id of the restaurant to be modified
     * @param restaurant The restaurant to be modified
     * @return the updated restaurant
     */
    @PutMapping (value = "/{id}", consumes = "application/json", produces = "application/json")
    public Restaurant updateRestaurant(@PathVariable Integer id,
                                       @RequestBody Restaurant restaurant) {
        Building building = buildingRepository.findById(restaurant.getBuildingCode()).get();
        restaurant.setBuilding(building);
        return restaurantRepository.save(restaurant);
    }

    /**
     * Returns all food of the restaurant.
     *
     * @param id the id of the restaurant
     * @return a list of all food of the restaurant
     */
    @GetMapping(value = "/{id}/food")
    List<Food> getAllFood(@PathVariable Integer id) {
        return foodRepository.allFoodOfRestaurant(id);
    }

    /**
     * Returns all the restaurants from a certain building.
     *
     * @param building the building that the restaurants are from
     * @return the list of restaurants
     */
    @GetMapping(path = "/getAllRestaurantsFromBuilding")
    public List<Restaurant> getAllRestaurantsFromBuilding(@RequestParam Building building) {
        return restaurantRepository.allRestaurantsFromBuilding(building);
    }

    /**
     * Retrieves all the foods ids and names from the database.
     *
     * @return a list with format "id name"
     */
    @GetMapping(path = "/id+name")
    public List<String> getRestaurantsIdAndName() {

        List<String> response = restaurantRepository.getRestaurantsIdAndName();
        for (int i = 0; i < response.size(); i++) {
            String current = response.get(i);
            response.set(i, current.replace(',', ' '));
        }
        return response;

    }

    /*@DeleteMapping(value = "/{id}")
    ResponseEntity<Integer> deleteRestaurant(@PathVariable Integer id) {
        boolean exists = restaurantRepository.findById(id).isPresent();

        if (!exists) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        restaurantRepository.deleteById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }*/

}
