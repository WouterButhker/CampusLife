package nl.tudelft.oopp.demo.controllers;

import java.util.List;
import nl.tudelft.oopp.demo.entities.food.Food;
import nl.tudelft.oopp.demo.repositories.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/foods")
public class FoodController {

    @Autowired
    private FoodRepository foodRepository;

    /**
     * Adds a new food to the database.
     *
     * @param food the food
     * @return Saved
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    Food addFood(@RequestBody Food food) {
        return foodRepository.save(food);
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<Integer> deleteFood(@PathVariable Integer id) {
        boolean exists = foodRepository.findById(id).isPresent();

        if (!exists) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        foodRepository.deleteById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    /**
     * Updates a food item.
     *
     * @param id The id of the food to be modified
     * @param food The food to be modified
     * @return the updated food
     */
    @PutMapping (value = "/{id}", consumes = "application/json", produces = "application/json")
    public Food updateFood(@PathVariable Integer id,
                                       @RequestBody Food food) {
        if (!foodRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Food does not exists!");
        } else {
            return foodRepository.save(food);
        }
    }

    @GetMapping
    List<Food> getAll() {
        return foodRepository.findAll();
    }
}
