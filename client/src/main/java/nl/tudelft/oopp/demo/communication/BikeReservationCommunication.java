package nl.tudelft.oopp.demo.communication;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import nl.tudelft.oopp.demo.entities.BikeReservation;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import org.springframework.http.ResponseEntity;

public class BikeReservationCommunication {

    /**
     * Creates a BikeReservation using a POST request.
     * @param bikeReservation The BikeReservation object that is being stored
     */
    public static void createBikeReservation(BikeReservation bikeReservation) {
        try {
            Building pickUpBuilding = bikeReservation.getPickUpBuilding();
            pickUpBuilding.setBikes(pickUpBuilding.getBikes() - 1);
            Building dropOffBuilding = bikeReservation.getDropOffBuilding();
            dropOffBuilding.setBikes(dropOffBuilding.getBikes() + 1);
            BuildingCommunication.updateBuilding(pickUpBuilding);
            BuildingCommunication.updateBuilding(dropOffBuilding);
            ServerCommunication.authenticatedPostRequest("/bikeReservations", bikeReservation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static BikeReservation parseBikeReservation(JsonObject inputReservation) {
        Integer user = null;
        if (!inputReservation.get("user").isJsonNull()) {
            user = Integer.parseInt(
                    inputReservation.getAsJsonObject("user").get("id").getAsString()
            );
        }
        Building pickUpBuilding = null;
        if (!inputReservation.get("pickUpBuilding").isJsonNull()) {
            pickUpBuilding = new Gson().fromJson(inputReservation
                    .get("pickUpBuilding").toString(), Building.class);
        }
        Building dropOffBuilding = null;
        if (!inputReservation.get("pickUpBuilding").isJsonNull()) {
            dropOffBuilding = new Gson().fromJson(inputReservation
                    .get("pickUpBuilding").toString(), Building.class);
        }
        String date = inputReservation.get("date").getAsString();
        String timeSlot = inputReservation.get("timeSlot").getAsString();
        Integer id = inputReservation.get("id").getAsInt();
        return new BikeReservation(id, user, pickUpBuilding, dropOffBuilding, date, timeSlot);
    }

    //    /**
    //     * Returns a list of all the bike reservations from the database.
    //     * @return List of BikeReservations
    //     */
    //    public static List<BikeReservation> getAllReservations() {
    //        String url = "/bikeReservations/all";
    //
    //        try {
    //            ResponseEntity<String> response = ServerCommunication.authenticatedRequest(url);
    //            if (response != null) {
    //                Type listType = new TypeToken<List<BikeReservation>>() {}.getType();
    //                return new Gson().fromJson(response.getBody(), listType);
    //            }
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //        return null;
    //    }

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
     * Method that returns all the reservations that are either today or in the future.
     * So it essentially drops all the past reservations.
     * @return A List with all the reservations that are today or in the future.
     */
    public static List<BikeReservation> getAllRelevantReservations() {
        List<BikeReservation> all = getAllReservations();
        if (all == null) {
            return null;
        }
        List<BikeReservation> res = new ArrayList<>();
        Date now = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String nowString = sdf.format(now);
        for (int i = 0; i < all.size(); i++) {
            String date = all.get(i).getDate();
            Date reservationDate = null;
            try {
                reservationDate = sdf.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (reservationDate == null) {
                // throw errorpopup
            }
            //checking that it's either today or in the future
            //so filtering out all the past reservations
            if (nowString.equals(date) || !reservationDate.before(now)) {
                res.add(all.get(i));
            }
        }
        return res;
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
