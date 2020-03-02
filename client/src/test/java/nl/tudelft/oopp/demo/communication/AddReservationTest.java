package nl.tudelft.oopp.demo.communication;

import nl.tudelft.oopp.demo.entities.Reservation;
import org.junit.jupiter.api.Test;


public class AddReservationTest {

    @Test
    public void testAddReservationToDatabase() {
        Integer user = 4;
        String room = "PC 2";
        String timeSlot = "08:00-09:00";
        ReservationCommunication.addReservationToDatabase(user, room, timeSlot);
    }

}
