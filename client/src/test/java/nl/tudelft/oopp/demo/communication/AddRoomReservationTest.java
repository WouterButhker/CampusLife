package nl.tudelft.oopp.demo.communication;

import static org.junit.jupiter.api.Assertions.assertNull;

import nl.tudelft.oopp.demo.communication.reservation.RoomReservationCommunication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddRoomReservationTest {

    @BeforeEach
    void doBeforeEach() {
        AuthenticationCommunication.login("admin", "admin");
    }

    @Test
    public void testAddReservationToDatabase() {
        System.out.println("TEST");
        Integer user = 4;
        String room = "2";
        String timeSlot = "01/02/2020,16:00 - 01/02/2020,17:00";
        RoomReservationCommunication.addReservationToDatabase(user, room, timeSlot);
        assertNull(null);
    }

}
