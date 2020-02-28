package nl.tudelft.oopp.demo.testFolder;

import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import org.junit.jupiter.api.Test;

import java.util.List;


public class GetAllBuildingsTest {

    @Test
    public void testGetAllBuildings() {
        List<Building> buildings = BuildingCommunication.getAllBuildings();
        System.out.println("---------------------------");
        System.out.println("Test = testGetAllBuildings");
        if (buildings != null)
            System.out.println(buildings.toString());
    }

}
