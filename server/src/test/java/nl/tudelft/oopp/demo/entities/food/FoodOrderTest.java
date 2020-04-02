package nl.tudelft.oopp.demo.entities.food;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FoodOrderTest {

    private Integer restaurant;
    private List<List<Integer>> foodsList;
    private int userId;
    private String date;
    private String timeSlot;
    private FoodOrder order;

    @BeforeEach
    void setUpper() {
        restaurant = 42;
        userId = 123;
        date = "2-4-2019";
        timeSlot = "13:00-15:00";
        order = new FoodOrder(userId, date, timeSlot, restaurant);
        foodsList = new ArrayList<>();
    }

    @Test
    void constructorTest() {
        assertNotNull(order);
    }

    @Test
    void getRestaurant() {
        assertEquals(restaurant, order.getRestaurant());
    }

    @Test
    void testToString() {
        assertEquals("Id: 0 user: NAME: null date: 2-4-2019 time: 13:00-15:00 restaurant: 42", order.toString());
    }

    @Test
    void getFoodsList() {
        assertEquals(foodsList, order.getFoodsList());
    }
}