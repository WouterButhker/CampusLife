package nl.tudelft.oopp.demo.testfolder;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import nl.tudelft.oopp.demo.communication.AuthenticationCommunication;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.communication.RestaurantCommunication;
import nl.tudelft.oopp.demo.communication.RoomCommunication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ServerCommunicationTest {

    @BeforeEach
    void doBeforeEach() {
        AuthenticationCommunication.login("random", "admin");
    }

    @Test
    public void testGetBuildingsCodeAndName() {
        String[] buildingsCodeAndName = BuildingCommunication.getBuildingsCodeAndName();
        /*
        if (buildingsCodeAndName != null) {
            for (int i = 0; i < buildingsCodeAndName.length; i++)
                System.out.println(buildingsCodeAndName[i]);
        }
        else System.out.println("NULL");
         */
    }

    @Test
    public void testAddBuildingToDatabase() {
        Integer buildingCode = 20;
        String name = "Aula";
        String location = "Mekelweg 5";
        String openingHours = "08:00-22:00";
        Integer bikes = 12;
        BuildingCommunication.addBuildingToDatabase(buildingCode,
                name, location, openingHours, bikes);
    }

    @Test
    public void testAddRoomToDatabase() {
        String roomCode = "PC 3";
        String name = "PC hall 3";
        Integer capacity = 36;
        Boolean hasWhiteboard = true;
        Boolean hasTV = false;
        Integer rights = 1;
        Integer building = 35;
        RoomCommunication.addRoomToDatabase(roomCode, name, capacity,
                hasWhiteboard, hasTV, rights, building);
    }

    @Test
    public void testGetAllRoomsFromBuilding() {
        Integer building = 35;
        System.out.println("---------------------------");
        System.out.println("Test = testGetAllRoomsFromBuilding");
        System.out.println(RoomCommunication.getAllRoomsFromBuilding(building));
        System.out.println("---------------------------");
    }

    @Test
    public void testGetAllRoomNamesFromBuilding() {
        Integer building = 35;
        System.out.println("---------------------------");
        System.out.println("Test = testGetAllRoomNamesFromBuilding");
        System.out.println(RoomCommunication.getAllRoomNamesFromBuilding(building));
        System.out.println("---------------------------");
    }
}
