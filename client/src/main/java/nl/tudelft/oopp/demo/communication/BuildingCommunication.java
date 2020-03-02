package nl.tudelft.oopp.demo.communication;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import nl.tudelft.oopp.demo.entities.Building;


public class BuildingCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();

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

    /**
     * Delete a building from the database by passing the building code.
     * @param buildingCode the number of the building
     * @return 1 if the building was deleted
     *         0 if there was no building with that code
     *         something else otherwise
     */
    public static String deleteBuildingFromDatabase(Integer buildingCode) {
        URI myUri = URI.create("http://localhost:8080/buildings/delete?buildingCode=" + buildingCode);
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

    /**
     * Counts all the buildings from the database.
     * @return an int with a number of all the buildings
     */
    public static Integer countAllBuildings() {
        URI myUri = URI.create("http://localhost:8080/buildings/count");
        HttpRequest request = HttpRequest.newBuilder().GET().uri(myUri).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return Integer.parseInt(response.body());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * This transforms the JSON of a building into a Building object.
     * @param inputBuilding a JSON with a building
     * @return a Building object.
     */
    public static Building parseBuilding(String inputBuilding) {
        inputBuilding = inputBuilding.replace("{", "");
        inputBuilding = inputBuilding.replace("}", "");
        String[] parameters = inputBuilding.split(",");
        for (int i = 0; i < parameters.length; i++) {
            String[] miniParams = parameters[i].split(":");
            miniParams[1] = miniParams[1].replace("\"", "");
            parameters[i] = miniParams[1];
            if (i == 3) {
                miniParams[3] = miniParams[3].replace("\"", "");
                parameters[i] += ":" + miniParams[2] + ":" + miniParams[3];
            }
        }
        Integer code = Integer.parseInt(parameters[0]);
        String name = parameters[1];
        String location = parameters[2];
        String openingHours = parameters[3];
        return new Building(code, name, location, openingHours,
                "/images/main-screen-default-building.jpg");
    }

    private static List<Building> parseBuildings(String inputBuildings) {
        List<Building> listOfBuildings = new ArrayList<>();
        inputBuildings = inputBuildings.replace("[", "");
        inputBuildings = inputBuildings.replace("]", "");
        if (inputBuildings.equals("")) {
            return listOfBuildings;
        }
        String[] listOfStrings = inputBuildings.split("(},)");
        for (int i = 0; i < listOfStrings.length; i++) {
            listOfBuildings.add(parseBuilding(listOfStrings[i]));
        }
        return listOfBuildings;
    }

    /**
     * Returns a list of all the buildings from the database.
     * @return list of buildings
     */
    public static List<Building> getAllBuildings() {
        URI myUri = URI.create("http://localhost:8080/buildings/all");
        HttpRequest request = HttpRequest.newBuilder().GET().uri(myUri).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return parseBuildings(response.body());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
