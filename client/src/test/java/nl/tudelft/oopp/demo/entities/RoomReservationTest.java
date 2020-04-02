package nl.tudelft.oopp.demo.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomReservationTest {

    private Integer id;
    private Integer user;
    private Building building;
    private Room room;
    /// DATE???
    private String timeSlot;
    private RoomReservation rr;

    @BeforeEach
    void setUpper() {
        id = 21;
        user = 123;
        building = new Building(54, "building", "asb", "always closed", 12);
        room = new Room("A1", "Auditorium", 200, true, true, 2, building);
        timeSlot = "11:00-15:00";
        rr = new RoomReservation(id, user, room, timeSlot);
    }

    @Test
    void constructorTest() {
        assertNotNull(rr);
    }

    @Test
    void getIdTest() {
        assertEquals(id, rr.getId());
    }

    @Test
    void setIdTest() {
        rr.setId(99);
        assertEquals(99, rr.getId().intValue());
    }

    @Test
    void getUserTest() {
        assertEquals(user, rr.getUser());
    }

    @Test
    void setUserTest() {
        rr.setUser(88);
        assertEquals(88, rr.getUser().intValue());
    }

    @Test
    void getRoomTest() {
        assertEquals(room, rr.getRoom());
    }

    @Test
    void setRoomTest() {
        Building testBuilding = new Building(0, "", "", "", 0);
        Room testRoom = new Room("","",0, true, true, 0, testBuilding);
        rr.setRoom(testRoom);
        assertEquals(testRoom, rr.getRoom());
    }

    @Test
    void getTimeSlotTest() {
        assertEquals(timeSlot, rr.getTimeSlot());
    }

    @Test
    void setTimeSlotTest() {
        rr.setTimeSlot("never >:D");
        assertEquals("never >:D", rr.getTimeSlot());
    }

    @Test
    void equalsTest() {
        assertEquals(rr, rr);
        assertFalse(rr.equals(new Object()));
        assertEquals(rr, new RoomReservation(id, user, room, timeSlot));
    }
}