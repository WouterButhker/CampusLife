package nl.tudelft.oopp.demo.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {

    private int id;
    private String date;
    private String timeSlot;
    private Integer restaurant;
    private FoodOrder testOrder;

    @BeforeEach
    void setUpper() {
        id = 123;
        date = "1-1-1970";
        timeSlot = "12:00-20:00";
        restaurant = 4;
        testOrder = new FoodOrder(id, date, timeSlot, restaurant);
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
    void testToStringTest() {
        assertEquals(null, testOrder.toString());
    }
}