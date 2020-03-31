package nl.tudelft.oopp.demo.communication;

import com.google.gson.Gson;
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
}
