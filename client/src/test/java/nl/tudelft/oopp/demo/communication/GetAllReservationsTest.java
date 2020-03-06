package nl.tudelft.oopp.demo.communication;

import nl.tudelft.oopp.demo.entities.Reservation;
import org.junit.jupiter.api.Test;

import java.util.List;


public class GetAllReservationsTest {

    @Test
    public void testGetAllReservations() {
        List<Reservation> reservationList = ReservationCommunication.getAllReservations();
        System.out.println("---------------------------");
        System.out.println("Test = testGetAllRooms");
        if (reservationList != null)
            System.out.println(reservationList.toString());
        /*
        for (Reservation reservation : reservationList) {
            System.out.println(reservation.toString());
        }
        */

    }

}
