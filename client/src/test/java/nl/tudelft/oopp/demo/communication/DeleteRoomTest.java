package nl.tudelft.oopp.demo.communication;

import org.junit.jupiter.api.Test;


public class DeleteRoomTest {

    @Test
    public void testDeleteRoom() {
        String roomCode = "abc";
        System.out.println("---------------------------");
        System.out.println("Test = testDeleteRoom");
        String response = RoomCommunication.deleteRoomFromDatabase(roomCode);
        if (response.equals("1")) {
            System.out.println("Deleted building with code " + roomCode);
        } else if (response.equals("0")) {
            System.out.println("There was no building with code " + roomCode);
        } else {
            System.out.println("Some other error!");
        }
        System.out.println("---------------------------");
    }

}
