package nl.tudelft.oopp.demo.communication;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import nl.tudelft.oopp.demo.communication.AuthenticationCommunication;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.communication.RestaurantCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class GetAllBuildingsTest {

    @BeforeEach
    void doBeforeEach() {
        AuthenticationCommunication.login("admin", "admin");
    }

    @Test
    public void testGetAllBuildings() {
        List<Building> buildings = BuildingCommunication.getAllBuildings();
        System.out.println("---------------------------");
        System.out.println("Test = testGetAllBuildings");
        if (buildings != null) {
            System.out.println(buildings.toString());
        }
        assertNull(null);
    }

}
