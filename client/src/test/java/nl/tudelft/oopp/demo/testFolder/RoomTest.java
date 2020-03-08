package nl.tudelft.oopp.demo.testFolder;

import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    private String code;
    private String name;
    private Integer capacity;
    private boolean hasWhiteboard;
    private boolean hasTV;
    private Integer rights;
    private Integer buildingCode;
    private Room r;

    @BeforeEach
    void doBeforeEach() {
        ServerCommunication.login("random", "admin");
    }

    @BeforeEach
    void setUpper() {
        code = "69";
        name = "TestRoom";
        capacity = 69;
        hasWhiteboard = true;
        hasTV = true;
        rights = 2;
        buildingCode = 42;

        r = new Room(code, name, capacity, hasWhiteboard, hasTV, rights, buildingCode);
    }

    @Test
    void constructorTest() {
        assertNotNull(r);
    }

    @Test
    void getCodeTest() {
        assertEquals(code, r.getCode());
    }

    @Test
    void setCodeTest() {
        r.setCode("42");
        assertEquals("42", r.getCode());
    }

    @Test
    void getNameTest() {
        assertEquals(name, r.getName());
    }

    @Test
    void setNameTest() {
        r.setName("otherRoom");
        assertEquals("otherRoom", r.getName());
    }

    @Test
    void getCapacityTest() {
        assertEquals(capacity, r.getCapacity());
    }

    @Test
    void setCapacityTest() {
        r.setCapacity(50);
        assertEquals(50, r.getCapacity());
    }

    @Test
    void hasWhiteboardTest() {
        assertEquals(hasWhiteboard, r.isHasWhiteboard());
    }

    @Test
    void setHasWhiteboardTest() {
        r.setHasWhiteboard(false);
        assertEquals(false, r.isHasWhiteboard());
    }

    @Test
    void hasTVTest() {
        assertEquals(true, r.isHasTV());
    }

    @Test
    void setHasTVTest() {
        r.setHasTV(false);
        assertEquals(false, r.isHasTV());
    }

    @Test
    void getRightsTest() {
        assertEquals(rights, r.getRights());
    }

//    @Test
//    void setRightsTest() {
//        TOOOOOOOO DOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
//    }

    @Test
    void getBuildingCodeTest() {
        assertEquals(buildingCode, r.getBuildingCode());
    }

    @Test
    void setBuildingCodeTest() {
        r.setBuildingCode(123123);
        assertEquals(123123, r.getBuildingCode());
    }

    @Test
    void equalsTest() {
        Room copyRoom = new Room(code, name, capacity, hasWhiteboard, hasTV, rights, buildingCode);
        assertEquals(r, copyRoom);
    }

    @Test
    void hashCodeTest() {
        assertTrue(Integer.class.isInstance(r.hashCode()));
    }
}