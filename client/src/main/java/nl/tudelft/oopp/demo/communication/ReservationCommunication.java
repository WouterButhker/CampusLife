package nl.tudelft.oopp.demo.communication;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.oopp.demo.entities.Reservation;
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
        room = room.replace(" ", "%20");
        String url = "/reservations/add?user=" + userId
                + "&room=" + room + "&slot=" + slot;

        try {
            ResponseEntity<String> response = ServerCommunication.authenticatedRequest(url);
            room = room.replace("%20", " ");
            System.out.println(response.getBody() + " reservation for user "
                    + userId + " at room " + room + " at slot " + slot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Reservation parseReservation(JsonObject inputReservation) {
        Integer id = inputReservation.get("id").getAsInt();
        System.out.println(id);
        Integer user = inputReservation.get(inputReservation.get("user")
                .getAsJsonObject().get("id").getAsString()).getAsInt();
        System.out.println(user);
        String room = RoomCommunication.parseRoom(inputReservation.get("room")
                .getAsJsonObject()).getCode();
        System.out.println(room);
        String timeSlot = inputReservation.get("timeSlot").getAsString();
        System.out.println(timeSlot);
        return new Reservation(id, user, room, timeSlot);
    }

    private static List<Reservation> parseReservations(String inputReservations) {
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = jsonParser.parse(inputReservations).getAsJsonArray();
        List<Reservation> listOfReservations = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            listOfReservations.add(parseReservation(jsonArray.get(i).getAsJsonObject()));
        }
        return listOfReservations;
    }

    /**
     * Returns a list of all the reservations from the database.
     * @return List of Reservations
     */
    public static List<Reservation> getAllReservations() {
        String url = "/reservations/all";
        try {
            return parseReservations(ServerCommunication.authenticatedRequest(url).getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
