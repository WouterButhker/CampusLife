package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.*;

import nl.tudelft.oopp.demo.entities.FavoriteRestaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FavoriteRestaurantTest {

    private Integer id;
    private Integer restaurant;
    private Integer user;
    private FavoriteRestaurant fr;

    @BeforeEach
    void setUpper() {
        id = 42;
        restaurant = 123;
        user = 3;
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
        fr.setRestaurant(1212);
        assertEquals(1212, fr.getRestaurant());
    }

    @Test
    void getUserTest() {
        assertEquals(user, fr.getUser());
    }

    @Test
    void setUserTest() {
        fr.setUser(1212);
        assertEquals(1212, fr.getUser());
    }
}