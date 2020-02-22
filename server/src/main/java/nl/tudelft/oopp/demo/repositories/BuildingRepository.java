package nl.tudelft.oopp.demo.repositories;

import java.util.List;
import nl.tudelft.oopp.demo.entities.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BuildingRepository extends JpaRepository<Building, Integer> {
    @Query("SELECT buildingCode, name FROM Building") /// building_code, name FROM building;
    List<String> findAllBuildingsCodeAndName();
}
