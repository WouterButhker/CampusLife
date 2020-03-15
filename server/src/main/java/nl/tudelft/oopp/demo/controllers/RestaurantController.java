package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
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

    @GetMapping
    List<Restaurant> getAll() {
        return restaurantRepository.findAll();
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
