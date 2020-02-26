package nl.tudelft.oopp.demo.communication;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
    public static String getAllRoomsFromBuilding(Integer building) {
        URI myUri = URI.create("http://localhost:8080/rooms/getRoomsFromBuilding?building=" + building);
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
}
