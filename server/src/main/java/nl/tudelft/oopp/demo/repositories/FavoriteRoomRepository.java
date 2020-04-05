package nl.tudelft.oopp.demo.repositories;

import java.util.List;
import nl.tudelft.oopp.demo.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FavoriteRoomRepository extends JpaRepository<FavoriteRoom, Integer> {

    @Query("SELECT r From FavoriteRoom r WHERE r.user = ?1 AND r.room = ?2")
    FavoriteRoom findBy(User user, Room room);

    @Query("SELECT r From FavoriteRoom r WHERE r.user = ?1")
    List<FavoriteRoom> allFavoriteRoomsOfUser(User user);
}