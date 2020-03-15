package nl.tudelft.oopp.demo.communication;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Restaurant;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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
     * Creates a restaurant.
     * @return The created Restaurant entity
     */
    public static Restaurant createRestaurant(Restaurant restaurant) {
        try {
            String responseString = ServerCommunication.authenticatedPostRequest(
                    "/restaurants", restaurant).getBody();
            Gson gson = new Gson();
            return gson.fromJson(responseString, Restaurant.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
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
    }
}
