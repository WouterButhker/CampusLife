package nl.tudelft.oopp.demo.entities.food;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.entities.reservation.RoomReservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FoodOrderTest {

    private Restaurant restaurant;
    private List<List<Integer>> foodsList;
    private User user;
    private String date;
    private String timeSlot;
    private FoodOrder order;

    @BeforeEach
    void setUpper() {
        restaurant = new Restaurant(789, "test restaurant", new Building(), "d");;
        user = new User(123);
        date = "2-4-2019";
        timeSlot = "13:00-15:00";
        order = new FoodOrder(user, date, timeSlot, restaurant);
        foodsList = new ArrayList<>();
    }

    @Test
    void emptyConstructorTest() {
        assertNotNull(new FoodOrder());
    }

    @Test
    void constructorTest() {
        assertNotNull(order);
    }

    @Test
    void constructorTest2() {
        FoodOrder foodOrder = new FoodOrder(user, date, timeSlot, restaurant);
        assertNotNull(foodOrder);
    }

    @Test
    void constructorTest3() {
        Room room = new Room("1", "name", 5, true, true, 1, new Building());
        RoomReservation reservation = new RoomReservation(user, room, "1-1-2020", "10:00-20:00");
        FoodOrder foodOrder = new FoodOrder(user, date, timeSlot, restaurant, reservation);
        assertNotNull(foodOrder);
    }

    @Test
    void setRestaurantTest() {
        Restaurant testRestaurant = new Restaurant(11, "name", new Building(), "des");
        order.setRestaurant(testRestaurant);
        assertEquals(testRestaurant, order.getRestaurant());
    }

    @Test
    void getRoomTest() {
        Room room = new Room("1", "name", 5, true, true, 1, new Building());
        RoomReservation reservation = new RoomReservation(user, room, "1-1-2020", "10:00-20:00");
        FoodOrder foodOrder = new FoodOrder(user, date, timeSlot, restaurant, reservation);
        assertEquals(reservation, foodOrder.getRoom());
    }

    @Test
    void setRoomTest() {
        Room room = new Room("1", "name", 5, true, true, 1, new Building());
        RoomReservation reservation = new RoomReservation(user, room, "1-1-2020", "10:00-20:00");
        order.setRoom(reservation);
        assertEquals(reservation, order.getRoom());
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
    void equalsNotSameTest() {
        FoodOrder otherOrder = new FoodOrder(new User(98), date, timeSlot, restaurant);
        assertFalse(order.equals(otherOrder));
    }

    @Test
    void equalsSameDifferentObjectTest() {
        FoodOrder otherOrder = new FoodOrder(user, date, timeSlot, restaurant);
        assertTrue(order.equals(otherOrder));
    }

    @Test
    void hashCodeTest() {
        assertTrue(Integer.class.isInstance(order.hashCode()));
    }

    @Test
    void toStringTest() {
        assertEquals("food order{user: user{id: 123, name:null, role: null}, date: 2-4-2019,"
                        + " timeslot: 13:00-15:00, restaurant: {789, test restaurant, null, d}"
                        + ", delivery room: null}", order.toString());
    }

    @Test
    void getFoodsList() {
        assertEquals(null, order.getFoodsList());
    }
}