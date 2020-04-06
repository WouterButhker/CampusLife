package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.*;

import nl.tudelft.oopp.demo.communication.AuthenticationCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoomTest {

    private String code;
    private String name;
    private Integer capacity;
    private boolean hasWhiteboard;
    private boolean hasTV;
    private Integer rights;
    private Integer buildingCode;
    private Room room;
    private Building building;

    @BeforeEach
    void setUpper() {
        code = "69";
        name = "TestRoom";
        capacity = 69;
        hasWhiteboard = true;
        hasTV = true;
        rights = 2;
        buildingCode = 42;
        String openingHours = "15:00-17:00, 01:00-19:00, 15:00-17:00, "
                + "15:00-17:00, 15:00-17:00, 15:00-17:00, 15:00-17:00";
        building = new Building(buildingCode, "building", "asb", openingHours, 12);
        room = new Room(code, name, capacity, hasWhiteboard, hasTV, rights, building);
    }

    @Test
    void constructorTest() {
        assertNotNull(room);
    }

    @Test
    void getCodeTest() {
        assertEquals(code, room.getRoomCode());
    }

    @Test
    void setCodeTest() {
        room.setRoomCode("42");
        assertEquals("42", room.getRoomCode());
    }

    @Test
    void getNameTest() {
        assertEquals(name, room.getName());
    }

    @Test
    void setNameTest() {
        room.setName("otherRoom");
        assertEquals("otherRoom", room.getName());
    }

    @Test
    void getCapacityTest() {
        assertEquals(capacity, room.getCapacity());
    }

    @Test
    void setCapacityTest() {
        room.setCapacity(50);
        assertEquals(50, room.getCapacity().intValue());
    }

    @Test
    void hasWhiteboardTest() {
        assertEquals(hasWhiteboard, room.isHasWhiteboard());
    }

    @Test
    void setHasWhiteboardTest() {
        room.setHasWhiteboard(false);
        assertEquals(false, room.isHasWhiteboard());
    }

    @Test
    void hasTvTest() {
        assertEquals(true, room.isHasTV());
    }

    @Test
    void setHasTvTest() {
        room.setHasTV(false);
        assertEquals(false, room.isHasTV());
    }

    @Test
    void getRightsTest() {
        assertEquals(rights, room.getRights());
    }

    @Test
    void setRightsTest() {
        room.setRights(0);
        assertEquals(0, room.getRights().intValue());
    }

    @Test
    void getBuildingCodeTest() {
        assertEquals(buildingCode, room.getBuilding().getCode());
    }

    @Test
    void setBuildingCodeTest() {
        room.getBuilding().setCode(123123);
        assertEquals(123123, room.getBuilding().getCode().intValue());
    }

    @Test
    void getBuildingTest() {
        assertEquals(building, room.getBuilding());
    }

    @Test
    void setBuildingTest() {
        Building otherBuilding = new Building(1, "Test", "Test street",
                "06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00, "
                        + "06:00-18:00, 19:00-21:00", 45);
        room.setBuilding(otherBuilding);
        assertEquals(otherBuilding, room.getBuilding());
    }

    @Test
    void equalsTestTrue() {
        Room copyRoom = new Room(code, name, capacity, hasWhiteboard, hasTV, rights, building);
        assertEquals(room, copyRoom);
    }

    @Test
    void equalsTestTrue2() {
        assertEquals(room, room);
    }

    @Test
    void equalsTestFalse() {
        assertFalse(room.equals(new Object()));
    }

    @Test
    void hashCodeTest() {
        assertTrue(Integer.class.isInstance(room.hashCode()));
    }
}