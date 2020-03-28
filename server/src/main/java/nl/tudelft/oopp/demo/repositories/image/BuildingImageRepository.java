package nl.tudelft.oopp.demo.repositories.image;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.image.BuildingImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface BuildingImageRepository extends JpaRepository<BuildingImage, Integer> {
    BuildingImage findByBuilding(Building building);

    boolean existsByBuilding(Building building);

    BuildingImage findByImageId(String imageId);

    @Transactional
    void deleteByBuilding(Building building);
}
