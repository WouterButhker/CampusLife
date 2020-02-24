package nl.tudelft.oopp.demo.repositories;

import java.util.List;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BuildingRepository extends JpaRepository<Building, Integer> {
    @Query("SELECT buildingCode, name FROM Building") /// building_code, name FROM building;
    List<String> findAllBuildingsCodeAndName();

    // @Modifying
    // @Query("delete from Book b where b.title=:title")
    // void deleteBooks(@Param("title") String title);

    @Modifying
    @Query("DELETE FROM Building b WHERE b.buildingCode = ?1")
    Integer deleteBuildingWithCode(Integer myBuilding);

    // @Query("DELETE FROM building where buildingCode = 3;")
    // String removeBuilding()
}
