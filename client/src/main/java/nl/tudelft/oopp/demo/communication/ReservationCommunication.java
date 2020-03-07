package nl.tudelft.oopp.demo.communication;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import nl.tudelft.oopp.demo.entities.Reservation;
import nl.tudelft.oopp.demo.entities.Room;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;


public class ReservationCommunication {


    /**
     * Adds a reservation to the database via HTTP request.
     * @param userId the id of the User that made the Reservation
     * @param room the roomCode of the Room that is reserved
     * @param timeSlot the time at which the Room is reserved
     */
    public static void addReservationToDatabase(Integer userId,
                                             String room,
                                             /// DATE
                                             String timeSlot) {
        room = room.replace(" ", "%20");
        String url = "/reservations/add?user=" + userId
                + "&room=" + room + "&timeSlot=" + timeSlot;

        try {
            ResponseEntity<String> response = ServerCommunication.authenticatedRequest(url);
            room = room.replace("%20", " ");
            System.out.println(response.getBody() + " reservation for user "
                    + userId + " at room " + room + " at time " + timeSlot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Reservation parseReservation(JsonObject inputReservation) {
        Integer id = inputReservation.get("id").getAsInt();
        System.out.println(id);
        Integer user = Integer.parseInt(inputReservation.getAsJsonObject("user").get("id").getAsString());
        System.out.println(user);
        String room = RoomCommunication.parseRoom(inputReservation.get("room").getAsJsonObject()).getCode();
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
