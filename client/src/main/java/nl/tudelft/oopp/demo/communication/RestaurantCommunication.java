package nl.tudelft.oopp.demo.communication;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import nl.tudelft.oopp.demo.entities.Food;
import nl.tudelft.oopp.demo.entities.Restaurant;
import org.springframework.web.client.HttpClientErrorException;

public class RestaurantCommunication {

    /**
     * Creates a restaurant.
     *
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
     * Returns a list of all the restaurants that are part of the building.
     *
     * @param building the number of the building you want to see the restaurants from
     * @return a list of restaurants from that building
     */
    public static List<Restaurant> getAllRestaurantsFromBuilding(Integer building) {
        String url = "/restaurants/getAllRestaurantsFromBuilding?building=" + building;
        try {
            String responseString = ServerCommunication.authenticatedRequest(
                    url).getBody();
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Restaurant>>() {}.getType();
            return gson.fromJson(responseString, listType);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Deletes a Restaurant from the database.
     *
     * @param id the name of the restaurant that needs to be removed
     * @return result code; -1 if exception
     */

    public static String deleteRestaurantFromDatabase(Integer id) {
        String url = "/restaurants/delete?id=" + id;
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
}
