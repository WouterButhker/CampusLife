package nl.tudelft.oopp.demo.repositories;

import java.util.List;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RoomRepository extends JpaRepository<Room, String> {

    @Query("SELECT r From Room r WHERE r.building = ?1")
    List<Room> allRoomsFromBuilding(Building myBuilding);

    @Query("SELECT r.name From Room r WHERE r.building = ?1")
    List<String> allRoomNamesFromBuilding(Building myBuilding);

    @Modifying
    @Query("DELETE FROM Room r WHERE r.roomCode = ?1")
    Integer deleteRoomWithCode(String myRoom);

    @Query("SELECT r from Room r WHERE r.hasTV = true AND r.building = ?1")
    List<Room> allRoomsWithTV(Building myBuilding);

    @Query("SELECT r from Room r WHERE r.hasWhiteboard = true AND r.building = ?1")
    List<Room> allRoomsWithWhiteBoard(Building myBuilding);

    @Query("SELECT r from Room r WHERE r.rights <= ?2 AND r.building = ?1")
    List<Room> allRoomsWithRights(Building myBuilding, Integer myRoomRights);

    @Query("SELECT r from Room r WHERE r.capacity >= ?2 AND r.capacity <= ?3 AND r.building = ?1")
    List<Room> allRoomsWithCapacity(Building myBuilding, Integer lowerCapacity, Integer upperCapacity);

    @Query("SELECT r from Room r WHERE r.rights <= ?2 AND r.building = ?1 AND r.hasWhiteboard = true")
    List<Room> allRoomsWithWhiteBoardAndRights(Building myBuilding, Integer myRoomRights);

    @Query("SELECT r from Room r WHERE r.rights <= ?2 AND r.building = ?1 AND r.hasTV = true")
    List<Room> allRoomsWithTVAndRights(Building myBuilding, Integer myRoomRights);

    @Query("SELECT r from Room r WHERE r.capacity >= ?2 AND r.capacity <= ?3 AND r.building = ?1 AND r.rights <= ?4")
    List<Room> allRoomsWithCapacityAndRights(Building myBuilding, Integer lowerCapacity, Integer upperCapacity, Integer myRights);

    @Query("SELECT r from Room r WHERE r.capacity >= ?2 AND r.capacity <= ?3 AND r.building = ?1 AND r.rights <= ?4 AND r.hasWhiteboard = true")
    List<Room> allRoomsWithCapacityAndRightsAndWhiteBoard(Building myBuilding, Integer lowerCapacity, Integer upperCapacity, Integer myRights);

    @Query("SELECT r from Room r WHERE r.capacity >= ?2 AND r.capacity <= ?3 AND r.building = ?1 AND r.rights <= ?4 AND r.hasTV = true")
    List<Room> allRoomsWithCapacityAndRightsAndTV(Building myBuilding, Integer lowerCapacity, Integer upperCapacity, Integer myRights);

    @Query("SELECT r from Room r WHERE r.rights <= ?2 AND r.building = ?1 AND r.hasWhiteboard = true AND r.hasTV = true")
    List<Room> allRoomsWithRightsAndWhiteBoardAndTV(Building building, Integer myRights);

    @Query("SELECT r from Room r WHERE r.capacity >= ?2 AND r.capacity <= ?3 AND r.building = ?1 AND r.rights <= ?4 AND r.hasWhiteboard = true AND r.hasTV = true")
    List<Room> allRoomsWithCapacityAndRightsAndWhiteBoardAndTV(Building myBuilding, Integer lowerCapacity, Integer upperCapacity, Integer myRights);

    @Query("SELECT r FROM Room r WHERE "
            + "r.building = ?1 AND "
            + "r.rights <= ?2 AND "
            + "r.hasTV = ?3 AND "
            + "r.hasWhiteboard = ?4 AND "
            + "r.capacity >= ?5 AND "
            + "r.capacity <= ?6")
    List<Room> getFilteredRooms(Building myBuilding, Integer myRights, Boolean hasTV, Boolean hasWhiteboard, Integer minCap, Integer maxCap);
}
