package nl.tudelft.oopp.demo.communication;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import nl.tudelft.oopp.demo.entities.reservation.RoomReservation;
import org.springframework.http.ResponseEntity;

public class RoomReservationCommunication {


    /**
     * Adds a reservation to the database via HTTP request.
     * @param userId the id of the User that made the Reservation
     * @param room the roomCode of the Room that is reserved
     * @param slot the time at which the Room is reserved
     */
    public static void addReservationToDatabase(Integer userId,
                                             String room,
                                             String slot) {
        String url = "/roomReservations/add?user=" + userId
                + "&room=" + room + "&slot=" + slot;

        try {
            ResponseEntity<String> response = ServerCommunication.authenticatedRequest(url);
            /*
            System.out.println(response.getBody() + " reservation for user "
                    + userId + " at room " + room + " at slot " + slot);

             */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static RoomReservation parseReservation(String inputReservation) {
        return new Gson().fromJson(inputReservation, RoomReservation.class);
    }

    private static List<RoomReservation> parseReservations(String inputReservations) {
        Type listType = new TypeToken<List<RoomReservation>>() {}.getType();
        return new Gson().fromJson(inputReservations, listType);
    }

    /**
     * Returns a list of all the reservations from the database.
     * Required permission: Student
     * @return List of Reservations
     */
    public static List<RoomReservation> getAllReservations() {
        String url = "/roomReservations/all";
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
    public static List<RoomReservation> getMyReservations() {
        String url = "/roomReservations/myReservations?user="
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
     * Returns a list of all the reservations a specific user has made.
     * @param user the User whose reservations you are looking for
     * @return A List of Reservations
     */
    public static List<RoomReservation> getAllReservationsForUser(Integer user) {
        String url = "/roomReservations/allForUser?user=" + user;
        try {
            return parseReservations(ServerCommunication.authenticatedRequest(url).getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns a list of all the reservations that were made for a specific room.
     * Required permission: Student
     * @param room the Room whose reservations you are looking for
     * @return A List of Reservations
     */
    public static List<RoomReservation> getAllReservationsForRoom(String room) {
        String url = "/roomReservations/allForRoom?room=" + room;
        try {
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
        String url = "/roomReservations/delete?id=" + id;
        try {
            ServerCommunication.authenticatedRequest(url).getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
