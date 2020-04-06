package nl.tudelft.oopp.demo.entities.image;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Restaurant;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "restaurant_image")
public class RestaurantImage extends Image {

    @OneToOne
    @JoinColumn(name = "restaurant")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    public RestaurantImage() {

    }

    public RestaurantImage(String fileName,
                           String fileType,
                           byte[] data,
                           Restaurant restaurant) {
        super(fileName, fileType, data);
        this.restaurant = restaurant;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return null;
    }
}
