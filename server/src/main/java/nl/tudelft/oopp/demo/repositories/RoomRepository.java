package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface RoomRepository extends JpaRepository<Room, String>, JpaSpecificationExecutor<Room> {

    @Query("SELECT r From Room r WHERE r.building = ?1")
    List<Room> allRoomsFromBuilding(Building myBuilding);

    @Query("SELECT r.name From Room r WHERE r.building = ?1")
    List<String> allRoomNamesFromBuilding(Building myBuilding);

    @Query("SELECT r from Room r WHERE r.hasTV = true AND r.building = ?1")
    List<Room> allRoomsWithTV(Building myBuilding);

    @Query("SELECT r from Room r WHERE r.hasWhiteboard = true AND r.building = ?1")
    List<Room> allRoomsWithWhiteBoard(Building myBuilding);

    @Query("SELECT r from Room r WHERE r.rights <= ?2 AND r.building = ?1")
    List<Room> allRoomsWithRights(Building myBuilding, Integer myRoomRights);

    @Query("SELECT r from Room r WHERE r.capacity >= ?2 AND r.capacity <= ?3 AND r.building = ?1")
    List<Room> allRoomsWithCapacity(Building myBuilding, Integer lowerCapacity,
                                    Integer upperCapacity);

    @Query(value = "SELECT r FROM Room r WHERE "
            + "r.building = :myBuilding AND "
            + "r.rights <= :myRights AND "
            + "(NOT :hasTV OR r.hasTV = :hasTV) AND "
            + "(NOT :hasWhiteboard OR r.hasWhiteboard = :hasWhiteboard) AND "
            + "r.capacity >= :minCap AND "
            + "r.capacity <= :maxCap", nativeQuery = true)
    List<Room> getFilteredRoomsFromBuilding(@Param("myBuilding") Building myBuilding,
                                            @Param("myRights") Integer myRights,
                                            @Param("hasTV") Boolean hasTV,
                                            @Param("hasWhiteboard") Boolean hasWhiteboard,
                                            @Param("minCap") Integer minCap,
                                            @Param("maxCap") Integer maxCap);

    @Query("SELECT r FROM Room r WHERE "
            + "r.rights <= ?1 AND "
            + "r.hasTV = ?2 AND "
            + "r.hasWhiteboard = ?3 AND "
            + "r.capacity >= ?4 AND "
            + "r.capacity <= ?5")
    List<Room> getAllFilteredRooms(Integer myRights, Boolean hasTV,
                                Boolean hasWhiteboard, Integer minCap, Integer maxCap);

    boolean existsRoomByRoomCode(String roomCode);

    Room findRoomByRoomCode(String roomCode);
}
