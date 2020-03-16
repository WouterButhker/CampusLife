package nl.tudelft.oopp.demo.repositories;

import java.util.List;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<RoomReservation, Integer> {
    @Query("SELECT r FROM Reservation r WHERE r.user = ?1")
    List<Reservation> getMyReservations(User user);
    
    List<RoomReservation> findAllByUser(User user);

    List<RoomReservation> findAllByRoom(Room room);

    void deleteById(Integer id);
}
