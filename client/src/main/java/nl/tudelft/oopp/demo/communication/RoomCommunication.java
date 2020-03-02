package nl.tudelft.oopp.demo.communication;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.oopp.demo.entities.Room;

public class RoomCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();

    /**
     * For adding a room to the database.
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
        roomCode = roomCode.replace(" ", "%20");
        name = name.replace(" ", "%20");
        URI myUri = URI.create("http://localhost:8080/rooms/add?roomCode=" + roomCode
                + "&name=" + name + "&capacity=" + capacity + "&hasWhiteboard=" + hasWhiteboard
                + "&hasTV=" + hasTV + "&rights=" + rights + "&building=" + building);
        HttpRequest request = HttpRequest.newBuilder().GET().uri(myUri).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a list of all the rooms that are part of the building.
     * @param building the number of the building you want to see the rooms from
     * @return a list of rooms from that building
     */
    public static List<Room> getAllRoomsFromBuilding(Integer building) {
        URI myUri = URI.create("http://localhost:8080/rooms/getRoomsFromBuilding?building=" + building);
        HttpRequest request = HttpRequest.newBuilder().GET().uri(myUri).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return parseRooms(response.body());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns a list of all the Room NAMES that are part of the building.
     * @param building the number of the building you want to see the Room NAMES from
     * @return a list of NAMES from that building
     */
    public static String getAllRoomNamesFromBuilding(Integer building) {
        URI myUri = URI.create("http://localhost:8080/rooms/getRoomNamesFromBuilding?building=" + building);
        HttpRequest request = HttpRequest.newBuilder().GET().uri(myUri).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static Room parseRoom(String inputRoom) {
        String[] building = inputRoom.split(",\"building\":");
        inputRoom = building[0];
        inputRoom = inputRoom.replace("{", "");
        inputRoom = inputRoom.replace("}", "");
        String[] parameters = inputRoom.split(",");
        for (int i = 0; i < parameters.length; i++) {
            String[] miniParams = parameters[i].split(":");
            miniParams[1] = miniParams[1].replace("\"", "");
            parameters[i] = miniParams[1];
        }
        String code = parameters[0];
        String name = parameters[1];
        Integer capacity = Integer.parseInt(parameters[2]);
        boolean hasWhiteboard = Boolean.parseBoolean(parameters[3]);
        boolean hasTV = Boolean.parseBoolean(parameters[4]);
        Integer rights = Integer.parseInt(parameters[5]);
        Integer buildingCode = null;
        if (!building[1].equals("null")) {
            buildingCode = BuildingCommunication.parseBuilding(building[1]).getCode();
        }
        return new Room(code, name, capacity, hasWhiteboard, hasTV, rights, buildingCode);
    }

    private static List<Room> parseRooms(String inputRooms) {
        List<Room> listOfRooms = new ArrayList<>();
        inputRooms = inputRooms.replace("[", "");
        inputRooms = inputRooms.replace("]", "");
        if (inputRooms.equals("")) {
            return listOfRooms;
        }
        String[] listOfStrings = inputRooms.split("(},)");
        for (int i = 0; i < listOfStrings.length; i++) {
            listOfRooms.add(parseRoom(listOfStrings[i]));
        }
        return listOfRooms;
    }

    /**
     * Returns a list of all the Rooms from the database.
     * @return list of Rooms
     */
    public static List<Room> getAllRooms() {
        URI myUri = URI.create("http://localhost:8080/rooms/all");
        HttpRequest request = HttpRequest.newBuilder().GET().uri(myUri).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return parseRooms(response.body());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Deletes a Room from the database.
     * @param roomCode the code for the room that needs to be removed
     * @return
     */
    public static String deleteRoomFromDatabase(String roomCode) {
        URI myUri = URI.create("http://localhost:8080/rooms/delete?roomCode=" + roomCode);
        HttpRequest request = HttpRequest.newBuilder().GET().uri(myUri).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "-1";
    }






}
