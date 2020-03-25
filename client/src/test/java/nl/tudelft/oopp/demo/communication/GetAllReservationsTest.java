package nl.tudelft.oopp.demo.communication;

import java.util.List;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GetAllReservationsTest {

    @BeforeEach
    void doBeforeEach() {
        AuthenticationCommunication.login("admin", "admin");
    }

    @Test
    public void testGetAllReservations() {
        List<RoomReservation> reservationList = ReservationCommunication.getAllReservations();
        System.out.println("---------------------------");
        System.out.println("Test = testGetAllRooms");
        if (reservationList != null) {
            System.out.println(reservationList.toString());
        }
        /*
        for (Reservation reservation : reservationList) {
            System.out.println(reservation.toString());
        }
        */

    }

}
