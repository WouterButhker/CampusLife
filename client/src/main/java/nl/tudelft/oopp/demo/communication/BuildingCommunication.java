package nl.tudelft.oopp.demo.communication;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Restaurant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class BuildingCommunication {

    public static List<String> getBuildingsCodeAndName() {
        List<String> response = new ArrayList<>();
        List<Building> buildings = getAllBuildings();
        for (Building building : buildings) {
            response.add(building.getCode() + " " + building.getName());
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
        String url = "/buildings/all";

        try {
            Type listType = new TypeToken<List<Building>>() {}.getType();
            return new Gson().fromJson(ServerCommunication.authenticatedRequest(url).getBody(), listType);
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
            Type listType = new TypeToken<List<Building>>() {}.getType();
            return new Gson().fromJson(ServerCommunication.authenticatedRequest(url).getBody(), listType);
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
        String url = "/buildings/save";
        try {
            return ServerCommunication.authenticatedPostRequest(url, building)
                    .getStatusCode().toString();
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
        String url = "/buildings/update";
        try {
            return ServerCommunication.authenticatedPutRequest(url, building)
                    .getStatusCode().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "400 BAD_REQUEST";
    }

    public static Building getBuildingByCode(Integer buildingCode) {
        String url = "/buildings/" + buildingCode;
        try {
            return new Gson().fromJson(ServerCommunication.authenticatedRequest(url).getBody(), Building.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
