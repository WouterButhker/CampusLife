package nl.tudelft.oopp.demo.communication;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import org.springframework.web.bind.annotation.RequestParam;

public class RoomCommunication {


    /**
     * For adding a room to the database.
     * Required permission: Admin
     * @param roomCode the code (abreviation) of the room
     * @param name the actual name of the room
     * @param capacity seat capacity
     * @param hasWhiteboard if the room has a whiteboard is true
     * @param hasTV if the room has a TV is true
     * @param rights the rights required to reserve this room
     * @param building the building where it belongs to
     */
    public static void addRoomToDatabase(String roomCode,
                                         String name,
                                         Integer capacity,
                                         Boolean hasWhiteboard,
                                         Boolean hasTV,
                                         Integer rights,
                                         Integer building) {
        /*
        roomCode = roomCode.replace(" ", "%20");
        name = name.replace(" ", "%20");
         */
        String url = "/rooms/add?roomCode=" + roomCode
                + "&name=" + name + "&capacity=" + capacity + "&hasWhiteboard=" + hasWhiteboard
                + "&hasTV=" + hasTV + "&rights=" + rights + "&building=" + building;

        try {
            ServerCommunication.authenticatedRequest(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
     * Parses a Room from a JSON object.
     * @param inputRoom JSON object with room attributes
     * @return Room object
     */
    public static Room parseRoom(JsonObject inputRoom) {
        String code = inputRoom.get("roomCode").getAsString();
        String name = inputRoom.get("name").getAsString();
        Integer capacity = inputRoom.get("capacity").getAsInt();
        boolean hasWhiteboard = inputRoom.get("hasWhiteboard").getAsBoolean();
        boolean hasTV = inputRoom.get("hasTV").getAsBoolean();
        Integer rights = inputRoom.get("rights").getAsInt();
        Integer buildingCode = BuildingCommunication.parseBuilding(
                inputRoom.get("building").getAsJsonObject()).getCode();
        return new Room(code, name, capacity, hasWhiteboard, hasTV, rights, buildingCode);
    }

    /**
     * Parses a List of Rooms from a string input.
     * @param inputRooms a JSON string as an array of rooms.
     * @return List of Rooms
     */
    private static List<Room> parseRooms(String inputRooms) {
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = jsonParser.parse(inputRooms).getAsJsonArray();
        List<Room> listOfRooms = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            listOfRooms.add(parseRoom(jsonArray.get(i).getAsJsonObject()));
        }
        return listOfRooms;
    }

    /**
     * Returns a list of all the Rooms from the database.
     * Required permission: Student
     * @return list of Rooms
     */
    public static List<Room> getAllRooms() {
        StringBuilder url = new StringBuilder("/rooms/all");
        Integer rights = 0;
        if (AuthenticationCommunication.myUserRole.equalsIgnoreCase("Employee")) {
            rights = 1;
        } else if (AuthenticationCommunication.myUserRole.equalsIgnoreCase("Admin")) {
            rights = 2;
        }
        url.append("?search=rights<" + rights + 1);
        String urlString = url.toString();
        try {
            return parseRooms(ServerCommunication.authenticatedRequest(urlString).getBody());
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
    public static String deleteRoomFromDatabase(String roomCode) {
        String url = "/rooms/delete?roomCode=" + roomCode;
        try {

            return ServerCommunication.authenticatedRequest(url).getBody();
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
            return parseRooms(ServerCommunication.authenticatedRequest(urlString).getBody());
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
        if (AuthenticationCommunication.myUserRole.equalsIgnoreCase("Employee")) {
            rights = 1;
        } else if (AuthenticationCommunication.myUserRole.equalsIgnoreCase("Admin")) {
            rights = 2;
        }
        url.append(" AND rights<" + rights + 1);
        String urlString = url.toString();
        try {
            return parseRooms(ServerCommunication.authenticatedRequest(urlString).getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
