package nl.tudelft.oopp.demo.communication;

import org.junit.jupiter.api.Test;


public class AddRoomTest {

    @Test
    public void testAddRoomToDatabaseForDeletion() {
        String roomCode = "abc";
        String name = "Delete Me";
        Integer capacity = 1;
        Boolean hasWhiteboard = true;
        Boolean hasTV = true;
        Integer rights = 1;
        Integer building = 1;
        RoomCommunication.addRoomToDatabase(roomCode, name, capacity, hasWhiteboard, hasTV, rights, building);
    }

}
