package nl.tudelft.oopp.demo.communication.reservation;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import nl.tudelft.oopp.demo.communication.AuthenticationCommunication;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.FoodOrderQuantity;
import nl.tudelft.oopp.demo.entities.reservation.FoodOrder;

public class FoodOrderCommunication {

    /**
     * Creates a food order.
     * @return The created FoodOrder entity
     */
    public static FoodOrder createFoodOrder(FoodOrder foodOrder) {
        try {
            String responseString = ServerCommunication.authenticatedPostRequest(
                    "/foodOrder", foodOrder).getBody();
            Gson gson = new Gson();
            return gson.fromJson(responseString, FoodOrder.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all food orders of the logged in user.
     * @return a list of all food orders of the logged in user
     */
    public static List<FoodOrder> getAll() {
        try {
            String responseString = ServerCommunication.authenticatedRequest(
                    String.format("/foodOrder/user/%d", AuthenticationCommunication.myUserId)
            ).getBody();
            Gson gson = new Gson();
            Type listType = new TypeToken<List<FoodOrder>>() {}.getType();
            return gson.fromJson(responseString, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all food order quantities of the food order.
     * @param foodOrder the food order
     * @return a list of all food order quantities of the food order
     */
    public static List<FoodOrderQuantity> getAllQuantities(FoodOrder foodOrder) {
        try {
            String responseString = ServerCommunication.authenticatedRequest(
                    String.format("/foodOrder/%d", foodOrder.getId())
            ).getBody();
            Gson gson = new Gson();
            Type listType = new TypeToken<List<FoodOrderQuantity>>() {}.getType();
            return gson.fromJson(responseString, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
