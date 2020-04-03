package nl.tudelft.oopp.demo.entities.reservation;

import nl.tudelft.oopp.demo.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FoodOrderTest {

    private User user;
    private String date;
    private String timeSlot;
    private Building building;
    private Room room;
    private Restaurant restaurant;
    private RoomReservation reservation;
    private List<List<Integer>> foodsList;
    private FoodOrder order;

    @BeforeEach
    void setUpper() {
        user = new User(123);
        date = "2-4-2019";
        timeSlot = "13:00-15:00";
        building = new Building(1, "name", "place", "Always closed", 3);
        room = new Room("code", "name", 30, true, true, 1, building);
        restaurant = new Restaurant(789, "test restaurant", 4, "d");;
        reservation = new RoomReservation(user, room, date, timeSlot);
        foodsList = new ArrayList<>();
        order = new FoodOrder(user, date, timeSlot, restaurant, reservation);
    }

    @Test
    void constructorTest() {
        assertNotNull(order);
    }

    @Test
    void constructorTest2() {
        assertNotNull(new FoodOrder(user, date, timeSlot, restaurant));
    }

    @Test
    void addFoodTest() {
        Food food = new Food(1, "test food", 444, 7.99);
        order.addFood(food);
        List<List<Integer>> testList = new ArrayList<>();
        testList.add(Arrays.asList(food.getId(), 1));
        assertEquals(testList, order.getFoodsList());
        order.addFood(food);
        order.addFood(food);
        testList.get(0).set(1, 3);
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
    void setRestaurantTest() {
        Restaurant testRestaurant = new Restaurant(4, "testRestaurant", 6, "pa");
        order.setRestaurant(testRestaurant);
        assertEquals(testRestaurant, order.getRestaurant());
    }

    @Test
    void getRoomTest() {
        assertEquals(reservation, order.getRoom());
    }

    @Test
    void setRoomTest() {
        Room testRoom = new Room("2c", "name", 4, false, true, 0, building);
        RoomReservation testRes = new RoomReservation(user, testRoom, "4-5-1234", "00:00-24:00");
        order.setRoom(testRes);
        assertEquals(testRes, order.getRoom());
    }

    @Test
    void getRestaurantTest() {
        assertEquals(restaurant, order.getRestaurant());
    }

    @Test
    void equalsSameTest() {
        assertTrue(order.equals(order));
    }

    @Test
    void equalsNotInstanceOfTest() {
        assertFalse(order.equals(new Object()));
    }

    @Test
    void equalsFalseTest() {
        FoodOrder test = new FoodOrder(new User(2), "test", "test", restaurant);
        assertFalse(order.equals(test));
    }

    @Test
    void equalsOtherSameTest() {
        FoodOrder test = new FoodOrder(user, date, timeSlot, restaurant, reservation);
        assertTrue(order.equals(test));
    }

    @Test
    void hashCodeTest() {
        assertTrue(Integer.class.isInstance(order.hashCode()));
    }

    @Test
    void toStringTest() {
        assertTrue(String.class.isInstance(order.toString()));
    }

    @Test
    void toDisplayStringTest() {
        assertEquals("Food order | test restaurant | Order:"
                + " (order details are not implemented yet) | Date: 2-4-2019 |"
                + " Timeslot: 13:00-15:00", order.toDisplayString());
    }

    @Test
    void getFoodsListTest() {
        assertEquals(foodsList, order.getFoodsList());
    }
}