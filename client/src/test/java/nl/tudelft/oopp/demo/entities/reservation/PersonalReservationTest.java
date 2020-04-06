package nl.tudelft.oopp.demo.entities.reservation;

import static org.junit.jupiter.api.Assertions.*;

import nl.tudelft.oopp.demo.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonalReservationTest {

    private User user;
    private String date;
    private String timeSlot;
    private String activity;
    private PersonalReservation testRes;

    @BeforeEach
    void setUpper() {
        user = new User(123);
        date = "2-2-2020";
        timeSlot = "16:00-23:00";
        activity = "jumping";
        testRes = new PersonalReservation(user, date, timeSlot, activity);
    }

    @Test
    void constructorTest() {
        assertNotNull(testRes);
    }

    @Test
    void getActivityTest() {
        assertEquals(activity, testRes.getActivity());
    }

    @Test
    void setActivityTest() {
        String newActivity = "playing";
        testRes.setActivity(newActivity);
        assertEquals(newActivity, testRes.getActivity());
    }

    @Test
    void equalsSameTest() {
        assertTrue(testRes.equals(testRes));
    }

    @Test
    void equalsNotInstanceOfTest() {
        assertFalse(testRes.equals(new Object()));
    }

    @Test
    void equalsNotSameSuperTest() {
        PersonalReservation test = new PersonalReservation(new User(5), "test", "test", "testing");
        assertFalse(testRes.equals(test));
    }

    @Test
    void equalsSameOtherObjectTest() {
        PersonalReservation testResCopy = new PersonalReservation(user, date, timeSlot, activity);
        assertTrue(testRes.equals(testResCopy));
    }

    @Test
    void hashCodeTest() {
        assertTrue(Integer.class.isInstance(testRes.hashCode()));
    }

    @Test
    void toStringTest() {
        assertEquals("personal reservation{user: Id: 123 user: null pass: null role: Student, "
                + "date: 2-2-2020, timeslot: 16:00-23:00, activity: jumping}", testRes.toString());
    }

    @Test
    void toDisplayStringTest() {
        String displayString = "Appointment |  | jumping | Date: 2-2-2020 | Timeslot: 16:00-23:00";
        assertEquals(displayString, testRes.toDisplayString());
    }
}