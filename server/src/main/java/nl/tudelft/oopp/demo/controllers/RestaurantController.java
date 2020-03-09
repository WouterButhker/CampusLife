package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @GetMapping(path = "/all")
    public List<Restaurant> getAll() { return restaurantRepository.findAll(); }

    /**
     * Adds a new restaurant to the database.
     * @param name the actual (full) name
     * @param buildingCode the number of the building
     * @param openingHours format aa:bb-cc:dd
     * @return Saved
     */
    @GetMapping(path = "/add")
    public @ResponseBody
    String addNewRestaurant(@RequestParam String name,
                          @RequestParam Integer buildingCode,
                          @RequestParam String openingHours) {
        Restaurant restaurant = new Restaurant( name, buildingCode, openingHours);
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

}
