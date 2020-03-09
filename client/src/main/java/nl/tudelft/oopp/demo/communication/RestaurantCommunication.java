package nl.tudelft.oopp.demo.communication;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.entities.Room;

import java.util.ArrayList;
import java.util.List;

public class RestaurantCommunication {

    /**
     * For adding a restaurant to the database.
     * @param name name of  restaurant
     * @param buildingCode the building where it belongs to
     * @param openingHours
     */
    public static void addRestaurantToDatabase(String name,
                                         Integer buildingCode,
                                         String openingHours) {
        String url = "/restaurants/add?name=" + name + "&buildingCode=" + buildingCode + "&openingHours" + openingHours;

        try {
            ServerCommunication.authenticatedRequest(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a list of all the rooms that are part of the building.
     * @param building the number of the building you want to see the rooms from
     * @return a list of rooms from that building
     */
    public static List<Restaurant> getAllRestaurantsFromBuilding(Integer building) {
        String url = "/rooms/getRoomsFromBuilding?building=" + building;
        try {

            return parseRestaurants(ServerCommunication.authenticatedRequest(url).getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Parses a Restaurant from a JSON object.
     * @param inputRestaurant JSON object with room attributes
     * @return Room object
     */
    public static Restaurant parseRestaurant(JsonObject inputRestaurant) {
        String name = inputRestaurant.get("name").getAsString();
        Integer buildingCode = BuildingCommunication.parseBuilding(
                inputRestaurant.get("building").getAsJsonObject()).getCode();
        String openingHours = inputRestaurant.get("openingHours").getAsString();
        return new Restaurant(name, buildingCode, openingHours);
    }

    /**
     * Parses a List of Restaurants from a string input.
     * @param inputRestaurants a JSON string as an array of restaurants.
     * @return List of Restaurants
     */
    private static List<Restaurant> parseRestaurants(String inputRestaurants) {
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = jsonParser.parse(inputRestaurants).getAsJsonArray();
        List<Restaurant> listOfRestaurants = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            listOfRestaurants.add(parseRestaurant(jsonArray.get(i).getAsJsonObject()));
        }
        return listOfRestaurants;
    }

    /**
     * Returns a list of all the Restaurants from the database.
     * @return list of Restaurants
     */
    public static List<Restaurant> getAllRestaurants() {
        String url = "/restaurants/all";
        try {
            return parseRestaurants(ServerCommunication.authenticatedRequest(url).getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Deletes a Restaurant from the database.
     * @param name the name of the restaurant that needs to be removed
     * @return
     */
    public static String deleteRestaurantFromDatabase(String name) {
        String url = "/restaurants/delete?name=" + name;
        try {

            return ServerCommunication.authenticatedRequest(url).getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "-1";
    }
}
