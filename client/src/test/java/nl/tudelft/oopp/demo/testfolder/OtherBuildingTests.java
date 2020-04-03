package nl.tudelft.oopp.demo.testfolder;

import java.util.List;
import nl.tudelft.oopp.demo.communication.AuthenticationCommunication;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OtherBuildingTests {

    @Test
    public void testGetBuildingsCodeAndName() {
        List<String> buildingsCodeAndName = BuildingCommunication.getBuildingsCodeAndName();
        System.out.println("---------------------------");
        System.out.println("Test = testGetBuildingsCodeAndName");
        if (buildingsCodeAndName != null) {
            for (String building : buildingsCodeAndName) {
                System.out.println(building);
            }
        } else {
            System.out.println("NULL");
        }
        System.out.println("---------------------------");
    }

    /*
    @Test
    public void testAddBuildingToDatabase() {
        Integer buildingCode = 20;
        String name = "Aula";
        String location = "Mekelweg 5";
        String openingHours = "08:00-22:00";
        BuildingCommunication.addBuildingToDatabase(buildingCode, name, location, openingHours);
    }
     */

}
