package nl.tudelft.oopp.demo.communication;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.oopp.demo.entities.BikeReservation;
import nl.tudelft.oopp.demo.entities.Building;
import org.springframework.http.ResponseEntity;

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

    /**
     * Creates a BikeReservation using a POST request but is probably broken at the moment.
     * @param bikeReservation The BikeReservation object that is being stored
     * @return The same BikeReservation object (I think?)
     */
    public static BikeReservation createBikeReservation(BikeReservation bikeReservation) {
        try {
            String responseString = ServerCommunication.authenticatedPostRequest(
                    "/bikeReservations", bikeReservation).getBody();
//            Building pickUpBuilding = bikeReservation.getPickUpBuilding();
//            pickUpBuilding.setBikes(pickUpBuilding.getBikes() - 1);
//            Building dropOffBuilding = bikeReservation.getDropOffBuilding();
//            dropOffBuilding.setBikes(dropOffBuilding.getBikes() + 1);
            //PUT requests for updating buildings from other branch
            Gson gson = new Gson();
            return gson.fromJson(responseString, BikeReservation.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static BikeReservation parseBikeReservation(JsonObject inputReservation) {
        Integer user = null;
        if (!inputReservation.get("user").isJsonNull()) {
            user = Integer.parseInt(
                    inputReservation.getAsJsonObject("user").get("id").getAsString()
            );
        }
        String date = inputReservation.get("date").getAsString();
        Building pickUpBuilding = null;
        if (!inputReservation.get("pickUpBuilding").isJsonNull()) {
            pickUpBuilding = BuildingCommunication.parseBuilding(
                    inputReservation.get("pickUpBuilding").getAsJsonObject());
        }
        Building dropOffBuilding = null;
        if (!inputReservation.get("dropOffBuilding").isJsonNull()) {
            dropOffBuilding = BuildingCommunication.parseBuilding(
                    inputReservation.get("dropOffBuilding").getAsJsonObject());
        }
        Integer id = inputReservation.get("id").getAsInt();
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

    /**
     * Deletes the BikeReservation with the given id.
     * @param id The id of the BikeReservation that has to be deleted
     */
    public void deleteBikeReservation(int id) {
        try {
            ServerCommunication.authenticatedDeleteRequest(
                    String.format("/bikeReservations/%d", id));

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Delete failed");
        }
    }
}
