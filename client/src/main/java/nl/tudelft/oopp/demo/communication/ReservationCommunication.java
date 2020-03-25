package nl.tudelft.oopp.demo.communication;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.entities.Reservation;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.entities.Room;
import org.springframework.http.ResponseEntity;

public class ReservationCommunication {


    /**
     * Adds a reservation to the database via HTTP request.
     * @param userId the id of the User that made the Reservation
     * @param room the roomCode of the Room that is reserved
     * @param slot the time at which the Room is reserved
     */
    public static void addReservationToDatabase(Integer userId,
                                             String room,
                                             String slot) {
        String url = "/reservations/add?user=" + userId
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

    private static RoomReservation parseReservation(JsonObject inputReservation) {
        Integer id = inputReservation.get("id").getAsInt();
        //System.out.println(id);
        Integer user = null;
        if (!inputReservation.get("user").isJsonNull()) {
            user = Integer.parseInt(
                    inputReservation.getAsJsonObject("user").get("id").getAsString()
            );
        }
        //System.out.println(user);
        Room room = null;
        if (!inputReservation.get("room").isJsonNull()) {
            room = new Gson().fromJson(inputReservation.get("room").toString(), Room.class);
        }
        //System.out.println(room);
        String timeSlot = inputReservation.get("timeSlot").getAsString();
        //System.out.println(timeSlot);
        return new RoomReservation(id, user, room, timeSlot);
    }

    private static List<RoomReservation> parseReservations(String inputReservations) {
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = jsonParser.parse(inputReservations).getAsJsonArray();
        List<RoomReservation> listOfReservations = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            listOfReservations.add(parseReservation(jsonArray.get(i).getAsJsonObject()));
        }
        return listOfReservations;
    }

    /**
     * Returns a list of all the reservations from the database.
     * Required permission: Student
     * @return List of Reservations
     */
    public static List<RoomReservation> getAllReservations() {
        String url = "/reservations/all";
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
        String url = "/reservations/myReservations?user=" + AuthenticationCommunication.myUserId;
        try {
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
        String url = "/reservations/allForUser?user=" + user;
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
        String url = "/reservations/allForRoom?room=" + room;
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
        String url = "/reservations/delete?id=" + id;
        try {
            ServerCommunication.authenticatedRequest(url).getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
