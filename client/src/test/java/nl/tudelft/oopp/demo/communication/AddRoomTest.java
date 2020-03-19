package nl.tudelft.oopp.demo.communication;

import nl.tudelft.oopp.demo.entities.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class AddRoomTest {

    @BeforeEach
    void doBeforeEach() {
        AuthenticationCommunication.login("admin", "admin");
    }

    @Test
    public void testAddRoomToDatabaseForDeletion() {
        String roomCode = "abc";
        String name = "Delete Me";
        Integer capacity = 1;
        Boolean hasWhiteboard = true;
        Boolean hasTV = true;
        Integer rights = 1;
        Integer buildingCode = 1;
        Room room = new Room(roomCode, name, capacity, hasWhiteboard, hasTV, rights, BuildingCommunication.getBuildingByCode(buildingCode));
        RoomCommunication.saveRoom(room);
    }

}
