package nl.tudelft.oopp.demo.testfolder;

import nl.tudelft.oopp.demo.communication.AuthenticationCommunication;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.communication.RestaurantCommunication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OtherBuildingTests {

    @BeforeEach
    void doBeforeEach() {
        AuthenticationCommunication.login("random", "admin");
    }

    @Test
    public void testCountBuildings() {
        Integer response = BuildingCommunication.countAllBuildings();
        System.out.println("---------------------------");
        System.out.println("Test = testCountBuildings");
        System.out.println(response);
    }

    @Test
    public void testGetBuildingsCodeAndName() {
        String[] buildingsCodeAndName = BuildingCommunication.getBuildingsCodeAndName();
        System.out.println("---------------------------");
        System.out.println("Test = testGetBuildingsCodeAndName");
        if (buildingsCodeAndName != null) {
            for (int i = 0; i < buildingsCodeAndName.length; i++) {
                System.out.println(buildingsCodeAndName[i]);
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
