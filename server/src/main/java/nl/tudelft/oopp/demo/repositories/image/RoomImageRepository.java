package nl.tudelft.oopp.demo.repositories.image;

import java.util.List;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.image.RoomImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RoomImageRepository extends JpaRepository<RoomImage, Integer> {
    List<RoomImage> findByRoom(Room room);

    boolean existsByRoom(Room room);

    RoomImage findByImageId(String imageId);

    @Transactional
    void deleteByRoom(Room room);

    @Transactional
    String deleteByImageId(String imageId);
}
