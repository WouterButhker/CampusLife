package nl.tudelft.oopp.demo.repositories;

import java.util.List;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.entities.reservation.PersonalReservation;
import nl.tudelft.oopp.demo.entities.reservation.RoomReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalReservationRepository extends JpaRepository<PersonalReservation, Integer> {

    List<PersonalReservation> findAllByUser(User user);

    void deleteById(Integer id);
}
