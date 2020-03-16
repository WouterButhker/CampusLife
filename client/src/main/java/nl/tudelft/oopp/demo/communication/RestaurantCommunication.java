package nl.tudelft.oopp.demo.communication;

<<<<<<< .merge_file_a00912
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import nl.tudelft.oopp.demo.entities.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantCommunication {

    /**
     * For adding a restaurant to the database.
     *
     * @param name         name of  restaurant
     * @param buildingCode the building where it belongs to
     * @param openingHours
     */
    public static void addRestaurantToDatabase(String name,
                                               Integer buildingCode,
                                               String openingHours) {
        String url = "/restaurants/add?name=" + name + "&buildingCode=" + buildingCode + "&openingHours=" + openingHours;

        try {
            ServerCommunication.authenticatedRequest(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a list of all the rooms that are part of the building.
     *
     * @param building the number of the building you want to see the rooms from
     * @return a list of rooms from that building
     */
    public static List<Restaurant> getAllRestaurantsFromBuilding(Integer building) {
        String url = "/rooms/getRoomsFromBuilding?building=" + building;
        try {

            return parseRestaurants(ServerCommunication.authenticatedRequest(url).getBody());
=======
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import nl.tudelft.oopp.demo.entities.Food;
import nl.tudelft.oopp.demo.entities.Restaurant;

public class RestaurantCommunication {
    /**
     * Retrieves all the restaurants.
     * @return List of all Restaurants
     */
    public static List<Restaurant> getRestaurants() {
        try {
            String responseString = ServerCommunication.authenticatedRequest(
                    "/restaurants").getBody();
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Restaurant>>() {}.getType();
            return gson.fromJson(responseString, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all the foods of a restaurant.
     * @param restaurantId the id of the restaurant
     * @return List of all Food of restaurant
     */
    public static List<Food> getAllFood(int restaurantId) {
        try {
            String responseString = ServerCommunication.authenticatedRequest(
                    String.format("/restaurants/%d/food", restaurantId)).getBody();
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Food>>() {}.getType();
            return gson.fromJson(responseString, listType);
>>>>>>> .merge_file_a18160
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
<<<<<<< .merge_file_a00912
     * Parses a Restaurant from a JSON object.
     *
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
     *
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
     *
     * @return list of Restaurants
     */
    public static List<Restaurant> getAllRestaurants() {
        String url = "/restaurants/all";
        try {
            return parseRestaurants(ServerCommunication.authenticatedRequest(url).getBody());
=======
     * Creates a restaurant.
     * @return The created Restaurant entity
     */
    public static Restaurant createRestaurant(Restaurant restaurant) {
        try {
            String responseString = ServerCommunication.authenticatedPostRequest(
                    "/restaurants", restaurant).getBody();
            Gson gson = new Gson();
            return gson.fromJson(responseString, Restaurant.class);
>>>>>>> .merge_file_a18160
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
<<<<<<< .merge_file_a00912
     * Deletes a Restaurant from the database.
     *
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
=======
     * Deletes a restaurant.
     * @param id the id of the restaurant to delete
     * @return True if the restaurant was deleted
     */
    public static boolean deleteRestaurant(int id) {
        try {
            String responseString = ServerCommunication.authenticatedDeleteRequest(
                    String.format("/restaurants/%d", id)).getBody();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
>>>>>>> .merge_file_a18160
    }
}
