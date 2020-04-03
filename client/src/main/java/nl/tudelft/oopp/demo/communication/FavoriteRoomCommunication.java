package nl.tudelft.oopp.demo.communication;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import nl.tudelft.oopp.demo.entities.*;

public class FavoriteRoomCommunication {

    /**
     * Creates a favorite room.
     * @return The created FavoriteRoom entity
     */
    public static FavoriteRoom addFavorite(Room room) {
        try {
            User user = new User(AuthenticationCommunication.myUserId);

            FavoriteRoom favoriteRoom = new FavoriteRoom(
                    null,
                    room,
                    user
            );
            String responseString = ServerCommunication.authenticatedPostRequest(
                    "/favoriterooms", favoriteRoom).getBody();
            Gson gson = new Gson();
            return gson.fromJson(responseString, FavoriteRoom.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns a list of all favorite rooms of the user.
     * @return the list of all favorite rooms
     */
    public static List<FavoriteRoom> getAll() {
        try {
            String responseString = ServerCommunication.authenticatedRequest(
                    String.format(
                            "/favoriterooms/user/%d",
                            AuthenticationCommunication.myUserId
                    )).getBody();
            Gson gson = new Gson();
            Type listType = new TypeToken<List<FavoriteRoom>>() {
            }.getType();
            return gson.fromJson(responseString, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns the FavoriteRoom if it is a favorite.
     * @return null if the room is not a favorite
     */
    public static FavoriteRoom isFavorite(Room room) {
        try {
            String responseString = ServerCommunication.authenticatedRequest(
                    String.format(
                            "favoriterooms/user/%d/room/%s",
                            AuthenticationCommunication.myUserId,
                            room.getRoomCode()
                    )).getBody();
            Gson gson = new Gson();
            return gson.fromJson(responseString, FavoriteRoom.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Deletes a favorite room.
     * @param id the id of the favorite room to delete
     * @return True if the favorite room was deleted
     */
    public static boolean removeFavorite(int id) {
        try {
            String responseString = ServerCommunication.authenticatedDeleteRequest(
                    String.format("/favoriterooms/%d", id)).getBody();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
