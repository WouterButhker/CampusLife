package nl.tudelft.oopp.demo.communication;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

public class RoomCommunication {

    /**
     * Returns a list of all the rooms that are part of the building.
     * Required permission: Student
     * @param building the number of the building you want to see the rooms from
     * @return a list of rooms from that building
     */
    public static List<Room> getAllRoomsFromBuilding(Integer building) {
        String url = "/rooms/getRoomsFromBuilding?building=" + building;
        try {
            ResponseEntity<String> response = ServerCommunication.authenticatedRequest(url);
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
     * Returns a list of all the Room NAMES that are part of the building.
     * Required permission: Student
     * @param building the number of the building you want to see the Room NAMES from
     * @return a list of NAMES from that building
     */
    public static String getAllRoomNamesFromBuilding(Integer building) {
        String url = "/rooms/getRoomNamesFromBuilding?building=" + building;
        try {
            return ServerCommunication.authenticatedRequest(url).getBody();
        } catch (Exception  e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Returns a list of all the Rooms from the database.
     * Required permission: Student
     * @return list of Rooms
     */
    public static List<Room> getAllRooms() {
        String url = "/rooms/all";
        try {
            ResponseEntity<String> response = ServerCommunication.authenticatedRequest(url);
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
     * @return
     */
    public static String deleteRoom(String roomCode) {
        String url = "/rooms/delete?roomCode=" + roomCode;
        try {

            return ServerCommunication.authenticatedRequest(url).getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "-1";
    }

    /**
     * Method to search for rooms from the giving building with the filters applied.
     * @param building the roomCode primary key as Integer
     * @param myRights the rights of the user as Integer
     * @param hasTV boolean for if the room has a TV
     * @param hasWhiteboard boolean for if the room has a whiteboard
     * @param minCap integer for the minimum capacity a room may have
     * @param maxCap integer for the maximum capacity a room may have
     * @return List of filtered rooms from given building
     */
    public static List<Room> getFilteredRoomsFromBuilding(Integer building, Integer myRights,
                                                         Boolean hasTV, Boolean hasWhiteboard,
                                                         Integer minCap, Integer maxCap) {
        String url = "/rooms/filter/getFilteredRoomsFromBuilding?myBuilding=" + building
                + "&myRights=" + myRights + "&hasTV=" + hasTV + "&hasWhiteboard=" + hasWhiteboard
                + "&minCap=" + minCap + "&maxCap=" + maxCap;
        try {
            ResponseEntity<String> response = ServerCommunication.authenticatedRequest(url);
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
     * Method to get the filtered rooms from all the rooms.
     * @param myRights the rights of the user as Integer
     * @param hasTV boolean for if the room has a TV
     * @param hasWhiteboard boolean for if the room has a whiteboard
     * @param minCap integer for the minimum capacity a room may have
     * @param maxCap integer for the maximum capacity a room may have
     * @return List of rooms of all buildings
     */
    public static List<Room> getAllFilteredRooms(Integer myRights, Boolean hasTV,
                                                 Boolean hasWhiteboard, Integer minCap,
                                                 Integer maxCap) {
        String url = "/rooms/filter/getAllFilteredRooms?myRights=" + myRights
                + "&hasTV=" + hasTV + "&hasWhiteboard=" + hasWhiteboard
                + "&minCap=" + minCap + "&maxCap=" + maxCap;
        try {
            ResponseEntity<String> response = ServerCommunication.authenticatedRequest(url);
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
        String url = "/rooms/save";
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
        String url = "/rooms/update";
        try {
            return ServerCommunication.authenticatedPutRequest(url, room)
                    .getStatusCode().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "400 BAD_REQUEST";
    }


}
