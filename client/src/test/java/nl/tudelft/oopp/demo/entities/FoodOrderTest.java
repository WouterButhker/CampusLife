package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.tudelft.oopp.demo.entities.reservation.FoodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FoodOrderTest {

    private Restaurant restaurant;
    private List<List<Integer>> foodsList;
    private int userId;
    private String date;
    private String timeSlot;
    private FoodOrder order;

    @BeforeEach
    void setUpper() {
        restaurant = new Restaurant(4, "rest name", 68, "descriptive building");
        userId = 123;
        date = "2-4-2019";
        timeSlot = "13:00-15:00";
        order = new FoodOrder(new User(userId), date, timeSlot, restaurant);
        foodsList = new ArrayList<>();
    }

    @Test
    void constructorTest() {
        assertNotNull(order);
    }

    @Test
    void getRestaurantTest() {
        assertEquals(restaurant, order.getRestaurant());
    }

    @Test
    void setRestaurantTest() {
        Restaurant testRest = new Restaurant(99, "name yes", 55, "description here");
        order.setRestaurant(testRest);
        assertEquals(testRest, order.getRestaurant());
    }

    @Test
    void testToStringTest() {
        assertEquals("food order{user: Id: 123 user: null pass: null role: Student, date: 2-4-2019"
                + ", timeslot: 13:00-15:00, restaurant: {4, rest name, 68, descriptive building}"
                + ", delivery room: null}", order.toString());
    }

    @Test
    void addFoodTest() {
        Food food = new Food(1, "test food", 444, 7.99);
        order.addFood(food);
        List<List<Integer>> testList = new ArrayList<>();
        testList.add(Arrays.asList(food.getId(), 1));
        assertEquals(testList, order.getFoodsList());
    }

    @Test
    void removeFoodTest() {
        Food food = new Food(1, "test food", 444, 7.99);
        order.addFood(food);
        order.addFood(food);
        order.addFood(food);
        List<List<Integer>> testList = new ArrayList<>();
        testList.add(Arrays.asList(food.getId(), 3));
        assertEquals(testList, order.getFoodsList());

        order.removeFood(food);
        order.removeFood(food);
        testList.get(0).set(1, 1);
        assertEquals(testList, order.getFoodsList());
    }

    @Test
    void removeFoodPairTest() {
        Food food = new Food(1, "test food", 444, 7.99);
        order.addFood(food);
        assertEquals(Arrays.asList(1, 1), order.getFoodsList().get(0));
        // confirms that there's something in the list
        List<List<Integer>> emptyList = new ArrayList<>();
        order.removeFood(food);
        assertEquals(emptyList, order.getFoodsList());
    }

    @Test
    void getFoodsListTest() {
        assertEquals(foodsList, order.getFoodsList());
    }
}