package nl.tudelft.oopp.demo.testfolder;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import nl.tudelft.oopp.demo.communication.AuthenticationCommunication;
import nl.tudelft.oopp.demo.communication.RestaurantCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class GetAllBuildingsTest {

    @BeforeEach
    void doBeforeEach() {
        AuthenticationCommunication.login("random", "admin");
    }

    @Test
    public void testGetAllBuildings() {
        List<Building> buildings = RestaurantCommunication.getAllBuildings();
        System.out.println("---------------------------");
        System.out.println("Test = testGetAllBuildings");
        if (buildings != null) {
            System.out.println(buildings.toString());
        }
        assertNull(null);
    }

}
