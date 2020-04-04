package nl.tudelft.oopp.demo.communication.reservation;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import nl.tudelft.oopp.demo.communication.AuthenticationCommunication;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.reservation.PersonalReservation;
import nl.tudelft.oopp.demo.entities.reservation.PersonalReservation;
import org.springframework.http.ResponseEntity;

public class PersonalReservationCommunication {
    /**
     * Adds a reservation to the database via HTTP request.
     * @param userId the id of the User that made the Reservation
     * @param slot the time at which the Room is reserved
     * @param activity the activity you are planning to do
     */
    public static void addReservationToDatabase(Integer userId,
                                                String slot,
                                                String activity) {
        String url = "/personalReservations/add?user=" + userId
                + "&slot=" + slot + "&activity=" + activity;
        try {
            ServerCommunication.authenticatedRequest(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<PersonalReservation> parseReservations(String inputReservations) {
        Type listType = new TypeToken<List<PersonalReservation>>() {}.getType();
        return new Gson().fromJson(inputReservations, listType);
    }

    /**
     * Returns a list of all the reservations from the database.
     * Required permission: Student
     * @return List of Reservations
     */
    public static List<PersonalReservation> getAllReservations() {
        String url = "/personalReservations/all";
        try {
            return parseReservations(ServerCommunication.authenticatedRequest(url).getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get all of the reservations for the current User.
     * @return A list of the current user's reservations
     */
    public static List<PersonalReservation> getMyReservations() {
        String url = "/personalReservations/myReservations?user="
                + AuthenticationCommunication.myUserId;
        try {
            System.out.println(ServerCommunication.authenticatedRequest(url).getBody());
            return parseReservations(ServerCommunication.authenticatedRequest(url).getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Deletes a reservation with the specified id from the database.
     * @param id the id of the reservation
     */
    public static void deleteReservationFromDatabase(Integer id) {
        String url = "/personalReservations/delete?id=" + id;
        try {
            ServerCommunication.authenticatedRequest(url).getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
