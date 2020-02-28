package nl.tudelft.oopp.demo.communication;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import org.junit.jupiter.api.Test;

import java.util.List;


public class GetAllRoomsTest {

    @Test
    public void testGetAllRooms() {
        List<Room> rooms = RoomCommunication.getAllRooms();
        System.out.println("---------------------------");
        System.out.println("Test = testGetAllRooms");
        if (rooms != null)
            System.out.println(rooms.toString());
    }

}
