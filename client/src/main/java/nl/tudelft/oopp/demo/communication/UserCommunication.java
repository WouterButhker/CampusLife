package nl.tudelft.oopp.demo.communication;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import nl.tudelft.oopp.demo.entities.User;

public class UserCommunication {

    /**
     * Method that returns a list of all Users.
     * @return A list of all Users
     */
    public static List<User> getAllUsers() {
        try {
            String responseString = ServerCommunication.authenticatedRequest("/rest/users/all")
                    .getBody();
            Gson gson = new Gson();
            Type listType = new TypeToken<List<User>>() {
            }.getType();
            return gson.fromJson(responseString, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method that updates the role of an User.
     * @param user The User of which the role is being updated
     * @return The updated User
     */
    public static User updateUserRole(User user) {
        try {
            String responseString = ServerCommunication.authenticatedPutRequest(
                    "/rest/users/changeRole", user).getBody();
            Gson gson = new Gson();
            return gson.fromJson(responseString, User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method that deletes an User.
     * @param id The id of the User that is being deleted
     */
    public static void deleteUser(Integer id) {
        String url = "/rest/users/" + id;
        try {
            ServerCommunication.authenticatedDeleteRequest(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
