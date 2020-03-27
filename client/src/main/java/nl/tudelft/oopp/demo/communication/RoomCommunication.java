package nl.tudelft.oopp.demo.communication;

import static nl.tudelft.oopp.demo.communication.AuthenticationCommunication.myUserRole;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import nl.tudelft.oopp.demo.entities.Room;
import org.springframework.http.ResponseEntity;



public class RoomCommunication {

    /**
     * Returns a list of all the Rooms from the database.
     * Required permission: Student
     * @return list of Rooms
     */
    public static List<Room> getAllRooms() {
        StringBuilder url = new StringBuilder("/rooms/all");
        Integer rights = 0;
        if (myUserRole != null) {
            if (myUserRole.equalsIgnoreCase("Employee")) {
                rights = 1;
            } else if (myUserRole.equalsIgnoreCase("Admin")) {
                rights = 2;
            }
        }
        url.append("?search=rights<" + rights + 1);
        String urlString = url.toString();
        try {
            ResponseEntity<String> response = ServerCommunication.authenticatedRequest(urlString);
            if (response != null) {
                Type listType = new TypeToken<List<Room>>() {}.getType();
                return new Gson().fromJson(response.getBody(), listType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Deletes a Room from the database.
     * Required permission: Admin
     * @param roomCode the code for the room that needs to be removed
     * @return result code; -1 if exception
     */
    public static String deleteRoom(String roomCode) {
        String url = "/rooms/" + roomCode;
        try {
            return ServerCommunication.authenticatedDeleteRequest(url).getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "-1";
    }

    /**
     * Method to create the filtering url to return the rooms with applied filters.
     * @param buildingCode the code of the building
     * @param myRights the rights of the user
     * @param hasTV the boolean for if the room has a tv
     * @param hasWhiteboard the boolean for if the room has a whiteboard
     * @param minCap the minimum capacity of a room
     * @param maxCap the maximum capacity of a room
     * @return
     */
    public static List<Room> getFilteredRooms(Integer buildingCode, Integer myRights,
                                              Boolean hasTV, Boolean hasWhiteboard,
                                              Integer minCap, Integer maxCap) {
        StringBuilder url = new StringBuilder("/rooms/getFilteredRooms?search=");
        if (buildingCode != -1) {
            url.append("building.buildingCode:" + buildingCode + " AND ");
        }
        url.append("rights <" + (myRights + 1) + " AND ");
        if (hasTV) {
            url.append("hasTV:" + hasTV + " AND ");
        }
        if (hasWhiteboard) {
            url.append("hasWhiteboard:" + hasWhiteboard + " AND ");
        }
        url.append("capacity>" + (minCap - 1) + " AND " + "capacity<" + maxCap
                + " OR capacity:" + maxCap);
        System.out.println(url);
        String urlString = url.toString();

        try {
            ResponseEntity<String> response = ServerCommunication.authenticatedRequest(urlString);
            if (response != null) {
                Type listType = new TypeToken<List<Room>>() {}.getType();
                return new Gson().fromJson(response.getBody(), listType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns a list of all the rooms that are part of the building.
     * Required permission: Student
     * @param building the number of the building you want to see the rooms from
     * @return a list of rooms from that building
     */
    public static List<Room> getAllRoomsFromBuilding(Integer building) {
        StringBuilder url = new StringBuilder(
                "/rooms/getAllRoomsFromBuilding?search=building.buildingCode:" + building);
        Integer rights = 0;
        if (myUserRole != null) {
            if (myUserRole.equalsIgnoreCase("Employee")) {
                rights = 1;
            } else if (myUserRole.equalsIgnoreCase("Admin")) {
                rights = 2;
            }
        }
        url.append(" AND rights<" + rights + 1);
        String urlString = url.toString();
        try {
            ResponseEntity<String> response = ServerCommunication.authenticatedRequest(urlString);
            if (response != null) {
                Type listType = new TypeToken<List<Room>>() {}.getType();
                return new Gson().fromJson(response.getBody(), listType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Save a Room to the database via POST request.
     * @param room the Room entity that you want to save
     * @return 200 OK if it's fine or 400 BAD_REQUEST if it's not fine
     */
    public static String saveRoom(Room room) {
        String url = "/rooms";
        try {
            return ServerCommunication.authenticatedPostRequest(url, room)
                    .getStatusCode().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "400 BAD_REQUEST";
    }

    /**
     * Update a Room from the database via PUT request.
     * @param room the Building entity that you want to update
     * @return 200 OK if it's fine or 400 BAD_REQUEST if it's not fine
     */
    public static String updateRoom(Room room) {
        String url = "/rooms";
        try {
            return ServerCommunication.authenticatedPutRequest(url, room)
                    .getStatusCode().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "400 BAD_REQUEST";
    }


}
