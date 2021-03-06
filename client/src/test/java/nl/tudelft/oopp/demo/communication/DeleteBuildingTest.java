package nl.tudelft.oopp.demo.communication;

import nl.tudelft.oopp.demo.communication.AuthenticationCommunication;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.communication.RestaurantCommunication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeleteBuildingTest {

    @Test
    public void testDeleteBuilding() {
        Integer buildingCode = 1;
        System.out.println("---------------------------");
        System.out.println("Test = testDeleteBuilding");
        String response = BuildingCommunication.deleteBuilding(buildingCode);
        if (response.equals("1")) {
            System.out.println("Deleted building with code " + buildingCode);
        } else if (response.equals("0")) {
            System.out.println("There was no building with code " + buildingCode);
        } else {
            System.out.println("Some other error!");
        }
        System.out.println("---------------------------");
    }

}
