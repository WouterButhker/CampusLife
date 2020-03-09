package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.RoomImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomImageRepository extends JpaRepository<RoomImage, String> {
}
