package nl.tudelft.oopp.demo.entities.reservation;

import static org.junit.jupiter.api.Assertions.*;

import nl.tudelft.oopp.demo.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReservationTest {

    private  int id;
    private User user;
    private String date;
    private String timeSlot;
    private String activity;
    private PersonalReservation testRes;

    @BeforeEach
    void setUpper() {
        id = 123;
        user = new User(123);
        date = "2-2-2020";
        timeSlot = "16:00-23:00";
        activity = "jumping";
        testRes = new PersonalReservation(user, date, timeSlot, activity);
        testRes.setId(id);
    }

    @Test
    void constructorTest() {
        assertNotNull(testRes);
    }

    @Test
    void setIdTest() {
        testRes.setId(0);
        assertEquals(0, testRes.getId());
    }

    @Test
    void getIdTest() {
        assertEquals(id, testRes.getId());
    }

    @Test
    void getUserTest() {
        assertEquals(user, testRes.getUser());
    }

    @Test
    void setUserTest() {
        User testUser = new User(44);
        testRes.setUser(testUser);
        assertEquals(testUser, testRes.getUser());
    }

    @Test
    void getDateTest() {
        assertEquals(date, testRes.getDate());
    }

    @Test
    void setDateTest() {
        String testDate = "16-4-2004";
        testRes.setDate(testDate);
        assertEquals(testDate, testRes.getDate());
    }

    @Test
    void getTimeSlotTest() {
        assertEquals(timeSlot, testRes.getTimeSlot());
    }

    @Test
    void setTimeSlotTest() {
        String testTimeSlot = "01:00-13:00";
        testRes.setTimeSlot(testTimeSlot);
        assertEquals(testTimeSlot, testRes.getTimeSlot());
    }

    @Test
    void equalsTest() {
        PersonalReservation copyReservation = new PersonalReservation(user, date,
                timeSlot, activity);
        copyReservation.setId(id);
        assertTrue(testRes.equals(copyReservation));
    }

    @Test
    void hashCodeTest() {
        assertTrue(Integer.class.isInstance(testRes.hashCode()));
    }

    @Test
    void toStringTest() {
        assertEquals("personal reservation{user: user{id: 123, name:null, role: Student},"
                        + " date: 2-2-2020, timeslot: 16:00-23:00, activity: jumping}",
                testRes.toString());
    }
}