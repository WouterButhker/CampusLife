package nl.tudelft.oopp.demo;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    private String roomCode;
    private String name;
    private Integer capacity;
    private boolean hasWhiteboard;
    private boolean hasTV;
    private Integer rights;
    private Building building;
    private Room r;


    @BeforeEach
    void setUpper() {
        roomCode = "69";
        name = "TestRoom";
        capacity = 69;
        hasWhiteboard = true;
        hasTV = true;
        rights = 2;
        building = new Building(123, "Test Building", "Somewhere", "11:11-22:22");

        r = new Room(roomCode, name, capacity, hasWhiteboard, hasTV, rights, building);
    }

    @Test
    void constructorTest() {
        assertNotNull(r);
    }

    @Test
    void getRoomCodeTest() {
        assertEquals(roomCode, r.getRoomCode());
    }

    @Test
    void getNameTest() {
        assertEquals(name, r.getName());
    }

    @Test
    void getCapacityTest() {
        assertEquals(capacity, r.getCapacity());
    }

    @Test
    void hasWhiteboardTest() {
        assertEquals(hasWhiteboard, r.isHasWhiteboard());
    }

    @Test
    void isHasTVTest() {
        assertEquals(hasTV, r.isHasTV());
    }

    @Test
    void getRightsTest() {
        assertEquals(rights, r.getRights());
    }

    @Test
    void getBuildingTest() {
        assertEquals(building, r.getBuilding());
    }

    @Test
    void setRoomCodeTest() {
        r.setRoomCode("testRoomCode");
        assertEquals("testRoomCode", r.getRoomCode());
    }

    @Test
    void setNameTest() {
        r.setName("testName");
        assertEquals("testName", r.getName());
    }

    @Test
    void setCapacityTest() {
        r.setCapacity(123);
        assertEquals(123, r.getCapacity());
    }

    @Test
    void setHasWhiteboardTest() {
        r.setHasWhiteboard(false);
        assertEquals(false, r.isHasWhiteboard());
    }

    @Test
    void setHasTVTest() {
        r.setHasTV(false);
        assertEquals(false, r.isHasTV());
    }

//    @Test
//    void setRightsTest() {
//        TOOOOOOOOOOOOOOOO DOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
//    }

    @Test
    void setBuildingTest() {
        Building b = new Building(987, "Other Building", "Everywhere", "00:00-24:00");
        r.setBuilding(b);
        assertEquals(b, r.getBuilding());
    }

    @Test
    void equalsTest() {
        Room copyRoom = new Room(roomCode, name, capacity, hasWhiteboard, hasTV, rights, building);
        assertEquals(r, copyRoom);
    }

    @Test
    void hashCodeTest() {
        assertTrue(Integer.class.isInstance(r.hashCode()));
    }
}