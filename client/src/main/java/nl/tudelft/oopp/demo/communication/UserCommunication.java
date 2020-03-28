package nl.tudelft.oopp.demo.communication;

import java.lang.reflect.Type;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import nl.tudelft.oopp.demo.entities.User;

public class UserCommunication {

    public List<User> getAllUsers() {
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

    public User updateUserRole(User user) {
        try {
            String responseString = ServerCommunication.authenticatedPutRequest("/rest/users", user)
                    .getBody();
            Gson gson = new Gson();
            return gson.fromJson(responseString, User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
