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
}
