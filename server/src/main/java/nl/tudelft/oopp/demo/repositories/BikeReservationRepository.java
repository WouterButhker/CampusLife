package nl.tudelft.oopp.demo.repositories;

import java.util.List;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.entities.reservation.BikeReservation;
import nl.tudelft.oopp.demo.entities.reservation.RoomReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BikeReservationRepository extends JpaRepository<BikeReservation, Integer> {

    boolean existsBikeReservationById(Integer id);

    List<BikeReservation> findAllByUser(User user);
}
