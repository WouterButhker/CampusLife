package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Integer> {}
