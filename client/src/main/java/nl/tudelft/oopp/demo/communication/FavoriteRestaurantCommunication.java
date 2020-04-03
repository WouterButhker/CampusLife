package nl.tudelft.oopp.demo.communication;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import nl.tudelft.oopp.demo.entities.FavoriteRestaurant;
import nl.tudelft.oopp.demo.entities.Food;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.entities.User;

import java.lang.reflect.Type;
import java.util.List;

public class FavoriteRestaurantCommunication {

    /**
     * Creates a favorite restaurant.
     * @return The created FavoriteRestaurant entity
     */
    public static FavoriteRestaurant addFavorite(Restaurant restaurant) {
        try {
            User user = new User(AuthenticationCommunication.myUserId);

            FavoriteRestaurant favoriteRestaurant = new FavoriteRestaurant(
                    null,
                    restaurant,
                    user
            );
            String responseString = ServerCommunication.authenticatedPostRequest(
                    "/favoriterestaurants", favoriteRestaurant).getBody();
            Gson gson = new Gson();
            return gson.fromJson(responseString, FavoriteRestaurant.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns a list of all favorite restaurants of the user.
     * @return the list of all favorite restaurants
     */
    public static List<FavoriteRestaurant> getAll() {
        try {
            String responseString = ServerCommunication.authenticatedRequest(
                    String.format(
                            "/favoriterestaurants/user/%d",
                            AuthenticationCommunication.myUserId
                    )).getBody();
            Gson gson = new Gson();
            Type listType = new TypeToken<List<FavoriteRestaurant>>() {
            }.getType();
            return gson.fromJson(responseString, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns the FavoriteRestaurant if it is a favorite.
     * @return null if the restaurant is not a favorite
     */
    public static FavoriteRestaurant isFavorite(Restaurant restaurant) {
        try {
            String responseString = ServerCommunication.authenticatedRequest(
                    String.format(
                            "favoriterestaurants/user/%d/restaurant/%d",
                            AuthenticationCommunication.myUserId,
                            restaurant.getId()
                    )).getBody();
            Gson gson = new Gson();
            return gson.fromJson(responseString, FavoriteRestaurant.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Deletes a favorite restaurant.
     * @param id the id of the favorite restaurant to delete
     * @return True if the favorite restaurant was deleted
     */
    public static boolean removeFavorite(int id) {
        try {
            String responseString = ServerCommunication.authenticatedDeleteRequest(
                    String.format("/favoriterestaurants/%d", id)).getBody();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
