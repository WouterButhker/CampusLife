package nl.tudelft.oopp.demo.repositories.image;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.entities.image.BuildingImage;
import nl.tudelft.oopp.demo.entities.image.RestaurantImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RestaurantImageRepository extends JpaRepository<RestaurantImage, Integer> {
    RestaurantImage findByRestaurant(Restaurant restaurant);

    boolean existsByRestaurant(Restaurant restaurant);

    RestaurantImage findByImageId(String imageId);

    @Transactional
    void deleteByRestaurant(Restaurant restaurant);
}
