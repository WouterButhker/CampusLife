package nl.tudelft.oopp.demo.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


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
        fr.setRestaurant(new Restaurant(1212, null, 1, null));
        assertEquals(1212, fr.getRestaurant().getId());
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