package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, String> {
    Image getImageById(String id);
}
