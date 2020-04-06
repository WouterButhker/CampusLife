package nl.tudelft.oopp.demo.entities.reservation;

import static org.junit.jupiter.api.Assertions.*;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoomReservationTest {

    private User user;
    private Building building;
    private Room room;
    private String date;
    private String timeSlot;
    private RoomReservation rr;

    @BeforeEach
    void setUpper() {
        user = new User(123);
        building = new Building(54, "building", "asb", "always closed", 12);
        room = new Room("A1", "Auditorium", 200, true, true, 2, building);
        date = "1-1-2020";
        timeSlot = "11:00-15:00";
        rr = new RoomReservation(user, room, date, timeSlot);
    }

    @Test
    void emptyConstructorTest() {
        assertNotNull(new RoomReservation());
    }

    @Test
    void constructorTest() {
        assertNotNull(rr);
    }

    @Test
    void getRoomTest() {
        assertEquals(room, rr.getRoom());
    }

    @Test
    void setRoomTest() {
        Room testRoom = new Room("code", "name", 33, false, false, 3, new Building());
        rr.setRoom(testRoom);
        assertEquals(testRoom, rr.getRoom());
    }

    @Test
    void equalsSameTest() {
        assertTrue(rr.equals(rr));
    }

    @Test
    void equalsNotInstanceofTest() {
        assertFalse(rr.equals(new Object()));
    }

    @Test
    void equalsNotSameSuperTest() {
        RoomReservation testRes = new RoomReservation(new User(9), room, date, "lol");
        assertFalse(rr.equals(testRes));
    }

    @Test
    void equalsSameCopyTest() {
        RoomReservation copyReservation = new RoomReservation(user, room, date, timeSlot);
        assertTrue(rr.equals(copyReservation));
    }

    @Test
    void hashCodeTest() {
        assertTrue(Integer.class.isInstance(rr.hashCode()));
    }

    @Test
    void toStringTest() {
        assertEquals(
                "Roomreservation{user: user{id: 123, name:null, role: Student}, date: 1-1-2020,"
                + " timeslot: 11:00-15:00, room: room{roomcode: A1, name: Auditorium, capacity:"
                + " 200, rights: 2, hasTV: true, hasWhiteboard: true, building: building{"
                + "buildingcode: 54, name: building, location: asb, opening hours: always closed"
                + ", bikes: 12}}}", rr.toString());
    }
}