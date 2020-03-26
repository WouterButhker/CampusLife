package nl.tudelft.oopp.demo.communication;

import com.google.gson.Gson;
import nl.tudelft.oopp.demo.entities.Food;
import nl.tudelft.oopp.demo.entities.Restaurant;

public class FoodCommunication {

    /**
     * Creates a food.
     * @return The created Food entity
     */
    public static Food createFood(Food food) {
        try {
            String responseString = ServerCommunication.authenticatedPostRequest(
                    "/foods", food).getBody();
            Gson gson = new Gson();
            return gson.fromJson(responseString, Food.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Deletes a food.
     * @param id the id of the food to delete
     * @return True if the food was deleted
     */
    public static String deleteFood(int id) {
        try {
            return ServerCommunication.authenticatedDeleteRequest(
                    String.format("/foods/%d", id)).getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "-1";
    }

    /**
     * Modifies a food using a PUT request.
     *
     * @param food the food to be updated
     * @return the modified Restaurant entity
     */
    public static Restaurant updateFood(Food food) {
        try {
            String responseString = ServerCommunication.authenticatedPutRequest(
                    String.format("/foods/%d", food.getId()), food).getBody();
            Gson gson = new Gson();
            return gson.fromJson(responseString, Restaurant.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
