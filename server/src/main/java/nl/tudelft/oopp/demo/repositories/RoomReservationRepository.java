package nl.tudelft.oopp.demo.repositories;

import java.util.List;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.entities.reservation.RoomReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoomReservationRepository extends JpaRepository<RoomReservation, Integer> {

    List<RoomReservation> findAllByUser(User user);
    
    List<RoomReservation> findAllByRoom(Room room);

    void deleteById(Integer id);
}
