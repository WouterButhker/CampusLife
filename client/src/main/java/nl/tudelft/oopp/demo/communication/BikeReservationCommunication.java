package nl.tudelft.oopp.demo.communication;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import nl.tudelft.oopp.demo.entities.BikeReservation;
import nl.tudelft.oopp.demo.entities.Building;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Type;
import java.util.List;

public class BikeReservationCommunication {

    /**
     * Creates a BikeReservation using a POST request but is probably broken at the moment.
     * @param bikeReservation The BikeReservation object that is being stored
     * @return The same BikeReservation object (I think?)
     */
    public static BikeReservation createBikeReservation(BikeReservation bikeReservation) {
        try {
            String responseString = ServerCommunication.authenticatedPostRequest(
                    "/bikeReservations", bikeReservation).getBody();
            Building pickUpBuilding = bikeReservation.getPickUpBuilding();
            pickUpBuilding.setBikes(pickUpBuilding.getBikes() - 1);
            Building dropOffBuilding = bikeReservation.getDropOffBuilding();
            dropOffBuilding.setBikes(dropOffBuilding.getBikes() + 1);
            BuildingCommunication.updateBuilding(pickUpBuilding);
            BuildingCommunication.updateBuilding(dropOffBuilding);
            Gson gson = new Gson();
            return gson.fromJson(responseString, BikeReservation.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns a list of all the bike reservations from the database.
     * @return List of BikeReservations
     */
    public static List<BikeReservation> getAllReservations() {
        String url = "/bikeReservations/all";

        try {
            ResponseEntity<String> response = ServerCommunication.authenticatedRequest(url);
            if (response != null) {
                Type listType = new TypeToken<List<BikeReservation>>() {}.getType();
                return new Gson().fromJson(response.getBody(), listType);
            }
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
