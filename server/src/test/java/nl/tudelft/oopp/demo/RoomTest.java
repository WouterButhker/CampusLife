package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoomTest {

    private String roomCode;
    private String name;
    private Integer capacity;
    private boolean hasWhiteboard;
    private boolean hasTV;
    private Integer rights;
    private Building building;
    private Room room;


    @BeforeEach
    void setUpper() {
        // TODO fix to work with images

        roomCode = "69";
        name = "TestRoom";
        capacity = 69;
        hasWhiteboard = true;
        hasTV = true;
        rights = 2;
        building = new Building(123, "Test Building", "Somewhere", "11:11-22:22", 42);

        room = new Room(roomCode, name, capacity, hasWhiteboard, hasTV, rights, building);

    }

    @Test
    void constructorTest() {
        assertNotNull(room);
    }

    @Test
    void getRoomCodeTest() {
        assertEquals(roomCode, room.getRoomCode());
    }

    @Test
    void getNameTest() {
        assertEquals(name, room.getName());
    }

    @Test
    void getCapacityTest() {
        assertEquals(capacity, room.getCapacity());
    }

    @Test
    void hasWhiteboardTest() {
        assertEquals(hasWhiteboard, room.isHasWhiteboard());
    }

    @Test
    void isHasTvTest() {
        assertEquals(hasTV, room.isHasTV());
    }

    @Test
    void getRightsTest() {
        assertEquals(rights, room.getRights());
    }

    @Test
    void getBuildingTest() {
        assertEquals(building, room.getBuilding());
    }

    @Test
    void setRoomCodeTest() {
        room.setRoomCode("testRoomCode");
        assertEquals("testRoomCode", room.getRoomCode());
    }

    @Test
    void setNameTest() {
        room.setName("testName");
        assertEquals("testName", room.getName());
    }

    @Test
    void setCapacityTest() {
        room.setCapacity(123);
        assertEquals(123, room.getCapacity());
    }

    @Test
    void setHasWhiteboardTest() {
        room.setHasWhiteboard(false);
        assertEquals(false, room.isHasWhiteboard());
    }

    @Test
    void setHasTvTest() {
        room.setHasTV(false);
        assertEquals(false, room.isHasTV());
    }

    /*
    @Test
    void setRightsTest() {
        //TODO DOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
    }
    */

    @Test
    void setBuildingTest() {
        // TODO fix to work with images
        /*
        Building building = new Building(987, "Other Building", "Everywhere", "00:00-24:00", 42);
        room.setBuilding(building);
        assertEquals(building, room.getBuilding());
         */
    }

    @Test
    void equalsTest() {
        Room copyRoom = new Room(roomCode, name, capacity, hasWhiteboard, hasTV, rights, building);
        assertEquals(room, copyRoom);
    }

    @Test
    void hashCodeTest() {
        assertTrue(Integer.class.isInstance(room.hashCode()));
    }
}