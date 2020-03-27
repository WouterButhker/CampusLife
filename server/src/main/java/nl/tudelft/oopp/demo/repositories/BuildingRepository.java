package nl.tudelft.oopp.demo.repositories;

import java.util.List;
import nl.tudelft.oopp.demo.entities.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BuildingRepository extends JpaRepository<Building, Integer> {
    @Query("SELECT buildingCode, name FROM Building") /// building_code, name FROM building;
    List<String> findAllBuildingsCodeAndName();

    @Modifying
    @Query("DELETE FROM Building b WHERE b.buildingCode = ?1")
    Integer deleteBuildingWithCode(Integer myBuilding);

    @Query("SELECT COUNT(buildingCode) FROM Building")
    Integer countAllBuildings();

    @Query("SELECT b FROM Building b WHERE b.bikes IS NOT NULL")

    List<Building> findAllBuildingsWithBikeStation();

    // @Query("DELETE FROM building where buildingCode = 3;")
    // String removeBuilding()

    @Query("SELECT b FROM Building b WHERE b.buildingCode = ?1")
    List<Building> getBuildingByCode(Integer buildingCode);
}
