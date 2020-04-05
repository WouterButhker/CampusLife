package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.*;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.FavoriteRestaurant;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FavoriteRestaurantTest {

    private Integer id;
    private Restaurant restaurant;
    private User user;
    private FavoriteRestaurant fr;

    @BeforeEach
    void setUpper() {
        id = 42;
        restaurant = new Restaurant();
        restaurant.setId(123);
        user = new User(11);
        fr = new FavoriteRestaurant(id, restaurant, user);
    }

    @Test
    void constructorTest() {
        assertNotNull(fr);
    }

    @Test
    void getIdTest() {
        assertEquals(id, fr.getId());
    }

    @Test
    void setIdTest() {
        fr.setId(1212);
        assertEquals(1212, fr.getId());
    }

    @Test
    void getRestaurantTest() {
        assertEquals(restaurant, fr.getRestaurant());
    }

    @Test
    void setRestaurantTest() {
        fr.setRestaurant(new Restaurant(5, null, new Building(), null));
        assertEquals(5, fr.getRestaurant().getId());
    }

    @Test
    void getUserTest() {
        assertEquals(user, fr.getUser());
    }

    @Test
    void setUserTest() {
        fr.setUser(new User(1212));
        assertEquals(1212, fr.getUser().getId());
    }
}