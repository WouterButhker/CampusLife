package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.*;

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
        restaurant = new Restaurant(123, null, 1, null);
        user = new User(3);
        fr = new FavoriteRestaurant(id, restaurant, user);
    }

    @Test
    void constructorTest() {
        assertNotNull(fr);
    }

    @Test
    void getIdTest() {
        assertTrue(id.equals(fr.getId()));
    }

    @Test
    void setIdTest() {
        fr.setId(1212);
        assertTrue(fr.getId().equals(1212));
    }

    @Test
    void getRestaurantTest() {
        assertEquals(restaurant, fr.getRestaurant());
    }

    @Test
    void setRestaurantTest() {
        fr.setRestaurant(new Restaurant(1212, null, 1, null));
        assertTrue(fr.getRestaurant().getId().equals(1212));
    }

    @Test
    void getUserTest() {
        assertEquals(user, fr.getUser());
    }

    @Test
    void setUserTest() {
        fr.setUser(new User(1212));
        assertTrue(fr.getUser().getId().equals(1212));
    }
}