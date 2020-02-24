package nl.tudelft.oopp.demo.communication;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class ServerCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();

    /**
     * Retrieves a quote from the server.
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */
    public static String getQuote() {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/quote")).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return "Communication with server failed";
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }
        return response.body();
    }

    /**
     * Retrieves all the buildings codes and names from the database and returns an array.
     * @return array of all the buildings, format: "code name"
     */
    public static String[] getBuildingsCodeAndName() {
        URI myUri = URI.create("http://localhost:8080/buildings/code+name");
        HttpRequest request = HttpRequest.newBuilder().GET().uri(myUri).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseString = response.body();
            responseString = responseString.replace("[", "");
            responseString = responseString.replace("]", "");
            responseString = responseString.replace("\"", "");
            String[] buildingsCodeAndName = responseString.split(",");
            return buildingsCodeAndName;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * For adding buildings to the database.
     * @param buildingCode the number of the building
     * @param name the name of the building
     * @param location the street where the building is situated
     * @param openingHours time it is open with format hh:mm-hh:mm
     */
    public static void addBuildingToDatabase(Integer buildingCode,
                                             String name,
                                             String location,
                                             String openingHours) {
        name = name.replace(" ", "%20");
        location = location.replace(" ", "%20");
        openingHours = openingHours.replace(" ", "%20");
        URI myUri = URI.create("http://localhost:8080/buildings/add?buildingCode=" + buildingCode
                + "&name=" + name + "&location=" + location + "&openingHours=" + openingHours);
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
}
