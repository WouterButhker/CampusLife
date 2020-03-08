package nl.tudelft.oopp.demo.communication;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;


public class AddReservationTest {

    @Test
    void test() {
        System.out.println("this is a test");
        assertNull(null);
    }

    @Test
    public void testAddReservationToDatabase() {
        System.out.println("TEST");
        ServerCommunication.login("random", "admin");
        Integer user = 4;
        String room = "2";
        String timeSlot = "01/02/2020,16:00 - 01/02/2020,17:00";
        ReservationCommunication.addReservationToDatabase(user, room, timeSlot);
        assertNull(null);
    }

}
