package nl.tudelft.oopp.demo.communication;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.oopp.demo.entities.Building;
import org.springframework.http.ResponseEntity;

public class BuildingCommunication {

    /**
     * Returns a list of Strings with the codes and names of the Buildings.
     * @return List of Strings
     */
    public static List<String> getBuildingsCodeAndName() {
        List<String> response = new ArrayList<>();
        List<Building> buildings = getAllBuildings();
        if (buildings != null) {
            for (Building building : buildings) {
                response.add(building.getCode() + " " + building.getName());
            }
        }
        return response;
    }

    /**
     * Delete a building from the database by passing the building code.
     * Required permission: Admin
     * @param buildingCode the number of the building
     * @return 1 if the building was deleted
     *         0 if there was no building with that code
     *         something else otherwise
     */
    public static String deleteBuilding(Integer buildingCode) {
        String url = "/buildings/" + buildingCode;
        try {
            return ServerCommunication.authenticatedDeleteRequest(url).getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "-1";
    }

    /**
     * Returns a list of all the buildings from the database.
     * Required permission: Student
     * @return list of buildings
     */
    public static List<Building> getAllBuildings() {
        String url = "/buildings";

        try {
            ResponseEntity<String> response = ServerCommunication.authenticatedRequest(url);
            if (response != null) {
                Type listType = new TypeToken<List<Building>>() {}.getType();
                return new Gson().fromJson(response.getBody(), listType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns a list of all the buildings with a bike station from the database.
     * @return list of buildings with a bike station
     */
    public static List<Building> getAllBuildingsWithBikeStation() {
        String url = "/buildings/bikes";
        try {
            ResponseEntity<String> response = ServerCommunication.authenticatedRequest(url);
            if (response != null) {
                Type listType = new TypeToken<List<Building>>() {}.getType();
                return new Gson().fromJson(response.getBody(), listType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Save a Building to the database via POST request.
     * @param building the Building entity that you want to save
     * @return 200 OK if it's fine or 400 BAD_REQUEST if it's not fine
     */
    public static String saveBuilding(Building building) {
        String url = "/buildings";
        try {
            ResponseEntity<String> response = ServerCommunication
                    .authenticatedPostRequest(url, building);
            if (response != null) {
                return response.getStatusCode().toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "400 BAD_REQUEST";
    }

    /**
     * Update a Building from the database via PUT request.
     * @param building the Building entity that you want to update
     * @return 200 OK if it's fine or 400 BAD_REQUEST if it's not fine
     */
    public static String updateBuilding(Building building) {
        String url = "/buildings";
        try {
            ResponseEntity<String> response = ServerCommunication
                    .authenticatedPutRequest(url, building);
            if (response != null) {
                return response.getStatusCode().toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "400 BAD_REQUEST";
    }

    /**
     * Returns a Building that has the code buildingCode.
     * @param buildingCode the code of the Building you are looking for
     * @return Building if found / null if not found.
     */
    public static Building getBuildingByCode(Integer buildingCode) {
        String url = "/buildings/" + buildingCode;
        try {
            ResponseEntity<String> response = ServerCommunication.authenticatedRequest(url);
            if (response != null) {
                return new Gson().fromJson(response.getBody(), Building.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
