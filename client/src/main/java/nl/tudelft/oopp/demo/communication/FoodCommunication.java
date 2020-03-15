package nl.tudelft.oopp.demo.communication;

import com.google.gson.Gson;
import nl.tudelft.oopp.demo.entities.Food;

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
    public static boolean deleteFood(int id) {
        try {
            String responseString = ServerCommunication.authenticatedDeleteRequest(
                    String.format("/foods/%d", id)).getBody();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
