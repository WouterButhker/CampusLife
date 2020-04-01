package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.food.Food;
import nl.tudelft.oopp.demo.entities.image.BuildingImage;
import nl.tudelft.oopp.demo.entities.image.RestaurantImage;
import nl.tudelft.oopp.demo.entities.image.RoomImage;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.FoodRepository;
import nl.tudelft.oopp.demo.repositories.RestaurantRepository;
import nl.tudelft.oopp.demo.repositories.image.RestaurantImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private RestaurantImageRepository restaurantImageRepository;

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

    @Modifying
    @PutMapping(value = "/image/{restaurantCode}")
    ResponseEntity<RestaurantImage> uploadFile(@PathVariable Integer restaurantCode,
                                             @RequestParam("file") MultipartFile file)
            throws IOException {
        if (!restaurantRepository.existsById(restaurantCode)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Restaurant restaurant = restaurantRepository.findRestaurantById(restaurantCode);

        if (restaurantImageRepository.existsByRestaurant(restaurant)) {
            restaurantImageRepository.deleteByRestaurant(restaurant);
        }
        String fileName = ImageController.checkFile(file);
        RestaurantImage restaurantImage = new RestaurantImage(
                fileName, file.getContentType(), file.getBytes(), restaurant);
        restaurantImageRepository.save(restaurantImage);
        return new ResponseEntity<>(restaurantImage, HttpStatus.OK);
    }

    @GetMapping("/image/getUrl/{restaurantCode}")
    List<String> getUrl(@PathVariable Integer restaurantCode) {
        Restaurant restaurant = restaurantRepository.findRestaurantById(restaurantCode);
        if (restaurantImageRepository.existsByRestaurant(restaurant)) {
            List<RestaurantImage> restaurantImages =
                    restaurantImageRepository.findByRestaurant(restaurant);
            List<String> response = new ArrayList<>();
            for (RestaurantImage restaurantImage : restaurantImages) {
                response.add(ImageController.getUrl(
                        "/restaurants/image/downloadFile/", restaurantImage));
            }
            return response;
        }
        return null;
    }

    @GetMapping("/image/downloadFile/{imageId}")
    ResponseEntity<Resource> downloadFile(@PathVariable String imageId) {
        return ImageController.downloadFile(restaurantImageRepository.findByImageId(imageId));
    }

    @DeleteMapping("/image/{imageId}")
    String deleteImage(@PathVariable String imageId) {
        return restaurantImageRepository.deleteByImageId(imageId);
    }

}
