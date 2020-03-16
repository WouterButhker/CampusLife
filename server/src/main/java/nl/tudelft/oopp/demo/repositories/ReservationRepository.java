package nl.tudelft.oopp.demo.repositories;

import java.util.List;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReservationRepository extends JpaRepository<RoomReservation, Integer> {
    @Query("SELECT r FROM RoomReservation r WHERE r.user = ?1")
    List<RoomReservation> getMyReservations(User user);

    List<RoomReservation> findAllByUser(User user);
    
    List<RoomReservation> findAllByRoom(Room room);

    void deleteById(Integer id);
}
