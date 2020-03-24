package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.entities.image.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface UserImageRepository extends JpaRepository<UserImage, Integer> {
    UserImage findByUser(User user);

    boolean existsByUser(User user);

    UserImage findByImageId(String imageId);
}
