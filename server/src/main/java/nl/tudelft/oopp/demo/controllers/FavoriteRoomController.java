package nl.tudelft.oopp.demo.controllers;

import java.util.List;
import nl.tudelft.oopp.demo.entities.*;
import nl.tudelft.oopp.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/favoriterooms")
public class FavoriteRoomController {

    @Autowired
    private FavoriteRoomRepository favoriteRoomRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomRepository roomRepository;

    /**
     * Adds a new favorite room to the database.
     * @param favoriteRoom the favorite room
     * @return the favorite room instance
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    FavoriteRoom addFavoriteRoom(@RequestBody FavoriteRoom favoriteRoom) {
        User user = favoriteRoom.getUser();
        Room room = favoriteRoom.getRoom();

        FavoriteRoom found = favoriteRoomRepository.findBy(user, room);

        if (found != null) {
            return found;
        } else {
            return favoriteRoomRepository.save(favoriteRoom);
        }
    }

    /**
     * Get favorite rooms of user.
     * @param userId the user id
     * @return a list of favorite rooms of userId
     */
    @GetMapping(value = "/user/{userId}")
    List<FavoriteRoom> getFavoriteRooms(@PathVariable Integer userId) {
        User user = userRepository.findById(userId).get();

        return favoriteRoomRepository.allFavoriteRoomsOfUser(user);
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<Integer> deleteFavoriteRoom(@PathVariable Integer id) {
        boolean exists = favoriteRoomRepository.findById(id).isPresent();

        if (!exists) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        favoriteRoomRepository.deleteById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping(value = "/user/{userId}/room/{roomId}")
    FavoriteRoom getFavoriteRoom(
            @PathVariable Integer userId,
            @PathVariable String roomId) {
        User user = userRepository.findById(userId).get();
        Room room = roomRepository.findById(roomId).get();

        return favoriteRoomRepository.findBy(user, room);
    }
}
