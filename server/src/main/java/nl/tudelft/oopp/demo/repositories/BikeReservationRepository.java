package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.entities.reservation.BikeReservation;
import nl.tudelft.oopp.demo.entities.reservation.RoomReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BikeReservationRepository extends JpaRepository<BikeReservation, Integer> {

    boolean existsBikeReservationById(Integer id);

    List<BikeReservation> findAllByUser(User user);
}
