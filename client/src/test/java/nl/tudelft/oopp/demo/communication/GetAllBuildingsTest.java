package nl.tudelft.oopp.demo.communication;

import nl.tudelft.oopp.demo.entities.Building;
import org.junit.jupiter.api.Test;

import java.util.List;


public class GetAllBuildingsTest {

    @Test
    public void testGetAllBuildings() {
        List<Building> buildings = BuildingCommunication.getAllBuildings();
        System.out.println("---------------------------");
        System.out.println("Test = testGetAllBuildings");
        System.out.println(buildings.toString());
    }

}
