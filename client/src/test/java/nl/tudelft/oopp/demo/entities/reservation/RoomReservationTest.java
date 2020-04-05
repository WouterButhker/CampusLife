package nl.tudelft.oopp.demo.entities.reservation;

import static org.junit.jupiter.api.Assertions.*;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.entities.reservation.RoomReservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoomReservationTest {

    private int id;
    private User user;
    private Building building;
    private Room room;
    private String date;
    private String timeSlot;
    private RoomReservation rr;

    @BeforeEach
    void setUpper() {
        id = 21;
        user = new User(123);
        building = new Building(54, "building", "asb", "always closed", 12);
        room = new Room("A1", "Auditorium", 200, true, true, 2, building);
        date = "1-1-2020";
        timeSlot = "11:00-15:00";
        rr = new RoomReservation(user, room, date, timeSlot);
        rr.setId(id);
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
        assertEquals(99, rr.getId());
    }

    @Test
    void getUserTest() {
        assertEquals(user, rr.getUser());
    }

    @Test
    void setUserTest() {
        User testUser = new User(45);
        rr.setUser(testUser);
        assertEquals(testUser, rr.getUser());
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
    void equalsSameTest() {
        assertEquals(rr, rr);
    }

    @Test
    void equalsNotInstanceOfTest() {
        assertFalse(rr.equals(new Object()));
    }

    @Test
    void equalsNotSameTest() {
        RoomReservation test = new RoomReservation(new User(23), room, "date", "timeSlot");
        assertFalse(rr.equals(test));
    }

    @Test
    void equalsSameButOtherObjectTest() {
        RoomReservation test = new RoomReservation(user, room, date, timeSlot);
        test.setId(id);
        assertTrue(rr.equals(test));
    }

    @Test
    void hashCodeTest() {
        assertTrue(Integer.class.isInstance(rr.hashCode()));
    }

    @Test
    void toStringTest() {
        assertTrue(String.class.isInstance(rr.toString()));
    }

    @Test
    void toDisplayStringTest() {
        assertEquals("Room reservation | Room: Auditorium | "
                + "Date: 1-1-2020 | Timeslot: 11:00-15:00", rr.toDisplayString());
    }
}