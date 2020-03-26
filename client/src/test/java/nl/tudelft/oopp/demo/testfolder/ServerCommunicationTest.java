package nl.tudelft.oopp.demo.testfolder;

import nl.tudelft.oopp.demo.communication.AuthenticationCommunication;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.communication.RoomCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ServerCommunicationTest {

    @BeforeEach
    void doBeforeEach() {
        AuthenticationCommunication.login("admin", "admin");
    }

    @Test
    public void testAddBuildingToDatabase() {
        Integer buildingCode = 20;
        String name = "Aula";
        String location = "Mekelweg 5";
        String openingHours = "08:00-22:00";
        Integer bikes = 12;
        Building building = new Building(buildingCode,
                name, location, openingHours, "image", bikes);
        BuildingCommunication.saveBuilding(building);
    }

    @Test
    public void testAddRoomToDatabase() {
        String roomCode = "PC 3";
        String name = "PC hall 3";
        Integer capacity = 36;
        Boolean hasWhiteboard = true;
        Boolean hasTV = false;
        Integer rights = 1;
        Integer buildingCode = 35;
        Room room = new Room(roomCode, name, capacity, hasWhiteboard, hasTV, rights, BuildingCommunication.getBuildingByCode(buildingCode));
        RoomCommunication.saveRoom(room);
    }

    @Test
    public void testGetAllRoomsFromBuilding() {
        Integer building = 35;
        System.out.println("---------------------------");
        System.out.println("Test = testGetAllRoomsFromBuilding");
        System.out.println(RoomCommunication.getAllRoomsFromBuilding(building));
        System.out.println("---------------------------");
    }
}
