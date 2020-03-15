package nl.tudelft.oopp.demo.communication;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import nl.tudelft.oopp.demo.entities.BikeReservation;
import nl.tudelft.oopp.demo.entities.Food;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;


public class BikeReservationCommunication {

    /**
     * Adds a bike reservation to the database via HTTP request.
     * @param userId The id of the User that made the bike reservation
     * @param pickUpBuilding The id of the building where the bike is picked up
     * @param dropOffBuilding The id of the building where the bike is dropped off
     * @param date The date of the bike reservation
     * @param slot The timeslot of the bike reservation
     */
    public static void addReservationToTheDatabase(Integer userId,
                                                   Integer pickUpBuilding,
                                                   Integer dropOffBuilding,
                                                   String date,
                                                   String slot) {
        String url = "/bikeReservations/add?user=" + userId
                + "&pickUpBuilding=" + pickUpBuilding + "&dropOffBuilding="
                + dropOffBuilding + "&date=" + date + "&slot=" + slot;

        try {
            ResponseEntity<String> response = ServerCommunication.authenticatedRequest(url);
            System.out.println(response.getBody() + " Bike reservation for user "
                    + userId + " at building " + pickUpBuilding + " and dropped off at building "
                    + dropOffBuilding + " on " + date + " at slot " + slot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static BikeReservation createBikeReservation(BikeReservation bikeReservation) {
        try {
            String responseString = ServerCommunication.authenticatedPostRequest(
                    "/bikeReservations", bikeReservation).getBody();
            Gson gson = new Gson();
            return gson.fromJson(responseString, BikeReservation.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
        Integer pickUpBuilding = null;
        if (!inputReservation.get("pickUpBuilding").isJsonNull()) {
            pickUpBuilding = Integer.parseInt(
                    inputReservation.getAsJsonObject("pickUpBuilding").get("buildingCode").getAsString()
            );
        }
        Integer dropOffBuilding = null;
        if (!inputReservation.get("dropOffBuilding").isJsonNull()) {
            dropOffBuilding = Integer.parseInt(
                    inputReservation.getAsJsonObject("dropOffBuilding").get("buildingCode").getAsString()
            );
        }
        String timeSlot = inputReservation.get("timeSlot").getAsString();
        return new BikeReservation(id, user, pickUpBuilding, dropOffBuilding, date, timeSlot);
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
