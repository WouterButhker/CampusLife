package nl.tudelft.oopp.demo;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.entities.reservation.Reservation;
import nl.tudelft.oopp.demo.entities.reservation.RoomReservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DataJpaTest
public class RoomReservationTest {

    private User user;
    private Building building;
    private Room room;
    private RoomReservation reservation;

    @BeforeEach
    private void setup() {
        user = new User("user","pass", "Student");
        building = new Building(99, "Aula",
                "Delft",
                "20:00-21:00, 20:00-21:00, 20:00-21:00,"
                        + " 20:00-21:00, 20:00-21:00, 20:00-21:00, 20:00-21:00",
                20);
        room = new Room("4", "Auditorium",1040, false, true, 2, building);
        reservation = new RoomReservation(user, room, "18/03/2020",
                "17/03/2020,22:00 - 17/03/2020,23:00");
    }

    @Test
    void testRoomReservationToString() {
        String out = "Roomreservation{user: user{name:user, role: Student}, "
                + "date: 18/03/2020, "
                + "timeslot: 17/03/2020,22:00 - 17/03/2020,23:00, "
                + "room: room{roomcode: 4, name: Auditorium, capacity: 1040, "
                    + "rights: 2, hasTV: true, hasWhiteboard: false, "
                    + "building: building{buildingcode: 99, name: Aula, location: Delft, "
                        + "opening hours: 20:00-21:00, 20:00-21:00, 20:00-21:00, "
                            + "20:00-21:00, 20:00-21:00, 20:00-21:00, 20:00-21:00, "
                        + "bikes: 20}}}";
        assert reservation.toString().equals(out);
    }

    @Test
    void testGetRoom() {
        assert reservation.getRoom().equals(room);
    }

    @Test
    void testSetRoom() {
        Room room1 = new Room("5", "Pi", 320, false, true, 2, building);
        reservation.setRoom(room1);
        assert reservation.getRoom().equals(room1);
    }

    @Test
    void testEquals() {
        RoomReservation reservation1 = new RoomReservation(user, room,
                "18/03/2020",
                "17/03/2020,22:00 - 17/03/2020,23:00");
        assert reservation.equals(reservation1);
    }

    @Test
    void testNotEquals() {
        RoomReservation reservation1 = new RoomReservation(user, room,
                "21/03/2020",
                "17/03/2020,22:00 - 17/03/2020,23:00");
        assertNotEquals(reservation1, reservation);
    }
}
