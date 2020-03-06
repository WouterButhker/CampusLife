package nl.tudelft.oopp.demo.communication;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.oopp.demo.entities.Building;

public class BuildingCommunication {


    /**
     * Retrieves all the buildings codes and names from the database and returns an array.
     * @return array of all the buildings, format: "code name"
     */
    public static String[] getBuildingsCodeAndName() {
        try {
            String responseString = ServerCommunication.authenticatedRequest(
                    "/buildings/code+name").getBody();
            responseString = responseString.replace("[", "");
            responseString = responseString.replace("]", "");
            responseString = responseString.replace("\"", "");
            return responseString.split(",");
        } catch (Exception e) {
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
        /*
        name = name.replace(" ", "%20");
        location = location.replace(" ", "%20");
        openingHours = openingHours.replace(" ", "%20");
         */
        String url = "/buildings/add?buildingCode=" + buildingCode
                + "&name=" + name + "&location=" + location + "&openingHours=" + openingHours;

        try {
            ServerCommunication.authenticatedRequest(url);
        } catch (Exception e) {
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
        String url = "/buildings/delete?buildingCode=" + buildingCode;
        try {
            return ServerCommunication.authenticatedRequest(url).getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "-1";
    }

    /**
     * Counts all the buildings from the database.
     * @return an int with a number of all the buildings
     */
    public static Integer countAllBuildings() {
        String url = "/buildings/count";

        try {
            return Integer.parseInt(ServerCommunication.authenticatedRequest(url).getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * This transforms the JSON of a building into a Building object.
     * @param inputBuilding a JSON with a building
     * @return a Building object.
     */
    public static Building parseBuilding(JsonObject inputBuilding) {
        Integer code = inputBuilding.get("buildingCode").getAsInt();
        String name = inputBuilding.get("buildingCode").getAsString();
        String location = inputBuilding.get("location").getAsString();
        String openingHours = inputBuilding.get("openingHours").getAsString();
        return new Building(code, name, location, openingHours,
                "/images/main-screen-default-building.jpg");
    }

    private static List<Building> parseBuildings(String inputBuildings) {
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = jsonParser.parse(inputBuildings).getAsJsonArray();
        List<Building> listOfBuildings = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            listOfBuildings.add(parseBuilding(jsonArray.get(i).getAsJsonObject()));
        }
        return listOfBuildings;
    }

    /**
     * Returns a list of all the buildings from the database.
     * @return list of buildings
     */
    public static List<Building> getAllBuildings() {
        String url = "/buildings/all";

        try {
            return parseBuildings(ServerCommunication.authenticatedRequest(url).getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
