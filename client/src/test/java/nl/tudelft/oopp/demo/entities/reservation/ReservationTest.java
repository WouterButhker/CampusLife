package nl.tudelft.oopp.demo.entities.reservation;

import static org.junit.jupiter.api.Assertions.*;

import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.entities.reservation.FoodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReservationTest {

    private int id;
    private String date;
    private String timeSlot;
    private Restaurant restaurant;
    private FoodOrder testOrder;

    @BeforeEach
    void setUpper() {
        id = 123;
        date = "1-1-1970";
        timeSlot = "12:00-20:00";
        restaurant = new Restaurant(456, "test rest", 456, "desc");
        testOrder = new FoodOrder(new User(id), date, timeSlot, restaurant);
        testOrder.setId(id);
    }

    @Test
    void constructorTest() {
        assertNotNull(testOrder);
    }

    @Test
    void setIdTest() {
        testOrder.setId(44);
        assertEquals(44, testOrder.getId());
    }

    @Test
    void getIdTest() {
        assertEquals(id, testOrder.getId());
    }

    @Test
    void getDateTest() {
        assertEquals(date, testOrder.getDate());
    }

    @Test
    void setDateTest() {
        testOrder.setDate("3-4-2020");
        assertEquals("3-4-2020", testOrder.getDate());
    }

    @Test
    void getTimeSlotTest() {
        assertEquals(timeSlot, testOrder.getTimeSlot());
    }

    @Test
    void setTimeSlotTest() {
        testOrder.setTimeSlot("10:00-11:00");
        assertEquals("10:00-11:00", testOrder.getTimeSlot());
    }

    @Test
    void equalsTest() {
        assertTrue(testOrder.equals(testOrder));
    }

    @Test
    void equalsNotInstanceOfTest() {
        assertFalse(testOrder.equals(new Object()));
    }

    @Test
    void testToStringTest() {
        assertEquals("food order{user: Id: 123 user: null pass: null role: Student, date: 1-1-1970"
                + ", timeslot: 12:00-20:00, restaurant: {456, test rest, 456, desc}"
                + ", delivery room: null}", testOrder.toString());
    }
}