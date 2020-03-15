package nl.tudelft.oopp.demo.communication;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import nl.tudelft.oopp.demo.entities.BikeReservation;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;


public class BikeReservationCommunication {

    /**
     * Adds a bike reservation to the database via HTTP request.
     * @param userId The id of the User that made the bike reservation
     * @param building The buildingCode of the building where the bike is reserved
     * @param date The date of the bike reservation
     * @param slot The timeslot of the bike reservation
     */
    public static void addReservationToTheDatabase(Integer userId,
                                                   Integer building,
                                                   String date,
                                                   String slot) {
        String url = "/bikeReservations/add?user=" + userId
                + "&building=" + building + "&date=" + date + "&slot=" + slot;

        try {
            ResponseEntity<String> response = ServerCommunication.authenticatedRequest(url);
            System.out.println(response.getBody() + " Bike reservation for user "
                    + userId + " at building " + building + " on " + date + " at slot " + slot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static BikeReservation parseBikeReservation(JsonObject inputReservation) {
        Integer id = inputReservation.get("id").getAsInt();
        Integer user = null;
        if (!inputReservation.get("user").isJsonNull()) {
            user = Integer.parseInt(
                    inputReservation.getAsJsonObject("user").get("id").getAsString()
            );
        }
        String date = inputReservation.get("date").getAsString();
        Integer building = null;
        if (!inputReservation.get("building").isJsonNull()) {
            building = Integer.parseInt(
                    inputReservation.getAsJsonObject("building").get("buildingCode").getAsString()
            );
        }
        String timeSlot = inputReservation.get("timeSlot").getAsString();
        return new BikeReservation(id, user, building, date, timeSlot);
    }

    private static List<BikeReservation> parseBikeReservations(String inputReservations) {
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = jsonParser.parse(inputReservations).getAsJsonArray();
        List<BikeReservation> listOfReservations = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            listOfReservations.add(parseBikeReservation(jsonArray.get(i).getAsJsonObject()));
        }
        return listOfReservations;
    }

    /**
     * Returns a list of all the bike reservations from the database.
     * @return List of BikeReservations
     */
    public static List<BikeReservation> getAllReservations() {
        String url = "/bikeReservations/all";
        try {
            return parseBikeReservations(ServerCommunication.authenticatedRequest(url).getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
