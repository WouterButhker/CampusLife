package nl.tudelft.oopp.demo.communication;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Reservation;
import org.springframework.http.ResponseEntity;


public class ReservationCommunication {


    /**
     * Adds a reservation to the database via HTTP request.
     * @param userId the id of the User that made the Reservation
     * @param room the roomCode of the Room that is reserved
     * @param timeSlot the time at which the Room is reserved
     */
    public static void addReservationToDatabase(Integer userId,
                                             String room,
                                             /// DATE
                                             String timeSlot) {
        room = room.replace(" ", "%20");
        String url = "/reservations/add?user=" + userId
                + "&room=" + room + "&timeSlot=" + timeSlot;

        try {
            ResponseEntity<String> response = ServerCommunication.authenticatedRequest(url);
            room = room.replace("%20", " ");
            System.out.println(response.getBody() + " reservation for user "
                    + userId + " at room " + room + " at time " + timeSlot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    public static Reservation parseReservation(String inputReservation) {
        inputReservation = inputReservation.replace("{", "");
        inputReservation = inputReservation.replace("}", "");
        String[] parameters = inputReservation.split(",");
        for (int i = 0; i < parameters.length; i++) {
            String[] miniParams = parameters[i].split(":");
            miniParams[1] = miniParams[1].replace("\"", "");
            parameters[i] = miniParams[1];
            if (i == 3)
            {
                miniParams[3] = miniParams[3].replace("\"", "");
                parameters[i] += ":" + miniParams[2] + ":" + miniParams[3];
            }
            System.out.println(parameters[i]);
        }
        Integer id = Integer.parseInt(parameters[0]);
        Integer user = Integer.parseInt(parameters[1]);
        String room = parameters[2];
        String timeSlot = parameters[3];
        return new Reservation(id, user, room, timeSlot);
    }

    private static List<Reservation> parseReservations(String inputReservations) {
        List<Reservation> listOfReservations = new ArrayList<>();
        inputReservations = inputReservations.replace("[", "");
        inputReservations = inputReservations.replace("]", "");
        if (inputReservations.equals("")) {
            return listOfReservations;
        }
        String[] listOfStrings = inputReservations.split("(},)");
        for (int i = 0; i < listOfStrings.length; i++) {
            listOfReservations.add(parseReservation()r(listOfStrings[i]));
        }
        return listOfReservations;
    }

    public static List<Reservation> getAllReservations() {
        URI myUri = URI.create("http://localhost:8080/reservations/all");
        HttpRequest request = HttpRequest.newBuilder().GET().uri(myUri).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return parseReservations(response.body());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    */

}
