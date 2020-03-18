package nl.tudelft.oopp.demo.communication;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.oopp.demo.entities.Food;
import nl.tudelft.oopp.demo.entities.Restaurant;
import org.springframework.web.client.HttpClientErrorException;

public class RestaurantCommunication {

    /*
    /**
     * For adding a restaurant to the database.
     *
     * @param id the ID of the restaurant
     * @param name         name of  restaurant
     * @param buildingCode the building where it belongs to
     * @param description the description of the restaurant
     *
    public static void addRestaurantToDatabase(int id,
                                               String name,
                                               Integer buildingCode,
                                               String description) {
        String url = "/restaurants/add?id=" + id
                + "&name=" + name
                + "&buildingCode=" + buildingCode
                + "&description=" + description;

        try {
            ServerCommunication.authenticatedRequest(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     */

    /**
     * Retrieves all the restaurants.
     *
     * @return List of all Restaurants
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
     * Returns a list of all the restaurants that are part of the building.
     *
     * @param building the number of the building you want to see the restaurants from
     * @return a list of restaurants from that building
     */
    public static List<Restaurant> getAllRestaurantsFromBuilding(Integer building) {
        String url = "/restaurants/getAllRestaurantsFromBuilding?building=" + building;
        try {

            return parseRestaurants(ServerCommunication.authenticatedRequest(url).getBody());
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Deletes a Restaurant from the database.
     *
     * @param name the name of the restaurant that needs to be removed
     * @return something
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

    /**
     * Retrieves all the foods of a restaurant.
     *
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Parses a Restaurant from a JSON object.
     *
     * @param inputRestaurant JSON object with restaurant attributes
     * @return Restaurant object
     */
    public static Restaurant parseRestaurant(JsonObject inputRestaurant) {
        int id = inputRestaurant.get("id").getAsInt();
        //Exception in thread "JavaFX Application Thread" java.lang.NullPointerException??????
        Integer buildingCode = BuildingCommunication.parseBuilding(
                inputRestaurant.get("building").getAsJsonObject()).getCode();
        String name = inputRestaurant.get("name").getAsString();
        String description = inputRestaurant.get("description").getAsString();
        return new Restaurant(id, name, buildingCode, description);
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
     * Creates a restaurant.
     *
     * @return The created Restaurant entity
     */
    public static Restaurant createRestaurant(Restaurant restaurant) {
        try {
            String responseString = ServerCommunication.authenticatedPostRequest(
                    "/restaurants/addRestaurant", restaurant).getBody();
            Gson gson = new Gson();
            return gson.fromJson(responseString, Restaurant.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
