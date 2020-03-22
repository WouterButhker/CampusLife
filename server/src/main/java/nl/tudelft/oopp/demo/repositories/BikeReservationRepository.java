package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.BikeReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BikeReservationRepository extends JpaRepository<BikeReservation, Integer> {

    boolean existsBikeReservationById(Integer id);
}
