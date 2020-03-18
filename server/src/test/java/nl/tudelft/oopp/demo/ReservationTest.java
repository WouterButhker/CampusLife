package nl.tudelft.oopp.demo;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ReservationTest {

    @Test
    public void testRoomReservationToString() {
        User user = new User("user","pass", "Student");
        Building building = new Building(99, "Aula", "Delft", "20:00-21:00, 20:00-21:00, 20:00-21:00, 20:00-21:00, 20:00-21:00, 20:00-21:00, 20:00-21:00",20);
        Room room = new Room("4", "Auditorium",1040, false, true, 2, building);
        RoomReservation res = new RoomReservation(user, room, "18/03/2020", "17/03/2020,22:00 - 17/03/2020,23:00");
        System.out.println(res.toString());
    }
}
