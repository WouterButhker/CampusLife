package nl.tudelft.oopp.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/rooms")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping(path = "/getRoomsFromBuilding")
    public List<Room> getAllRoomsFromBuilding(@RequestParam Building building) {
        return roomRepository.allRoomsFromBuilding(building);
    }

    @GetMapping(path = "/getRoomNamesFromBuilding")
    public List<String> getAllRoomNamesFromBuilding(@RequestParam Building building) {
        return roomRepository.allRoomNamesFromBuilding(building);
    }

    @GetMapping(path = "/filter/rights")
    public List<Room> getAllRoomsWithTV(@RequestParam Building building,
                                        @RequestParam Integer rights) {
        return roomRepository.allRoomsWithRights(building, rights);
    }

    @GetMapping(path = "/filter/getRoomsWithCapacity")
    public List<Room> getAllRoomsWithCapacity(@RequestParam Building building,
                                              @RequestParam Integer lowerCapacity,
                                              @RequestParam Integer upperCapacity) {
        return roomRepository.allRoomsWithCapacity(building, lowerCapacity, upperCapacity);
    }

    @GetMapping(path = "/filter/getRoomsWithTV")
    public List<Room> getAllRoomsWithTv(@RequestParam Building building) {
        return roomRepository.allRoomsWithTV(building);
    }

    @GetMapping(path = "/filter/getRoomsWithWhiteBoard")
    public List<Room> getAllRoomsWithWhiteBoard(@RequestParam Building building) {
        return roomRepository.allRoomsWithWhiteBoard(building);
    }

    @GetMapping(path = "/filter/getFilteredRoomsFromBuilding")
    public List<Room> getFilteredRoomsFromBuilding(@RequestParam Building myBuilding,
                                       @RequestParam Integer myRights,
                                       @RequestParam Boolean hasTV,
                                       @RequestParam Boolean hasWhiteboard,
                                       @RequestParam Integer minCap,
                                       @RequestParam Integer maxCap) {
        return roomRepository.getFilteredRoomsFromBuilding(myBuilding, myRights,
                        hasTV, hasWhiteboard, minCap, maxCap);
    }

    @GetMapping(path = "/filter/getAllFilteredRooms")
    public List<Room> getAllFilteredRooms(@RequestParam Integer myRights,
                                          @RequestParam Boolean hasTV,
                                          @RequestParam Boolean hasWhiteboard,
                                          @RequestParam Integer minCap,
                                          @RequestParam Integer maxCap) {
        return roomRepository.getAllFilteredRooms(myRights,
                hasTV, hasWhiteboard, minCap, maxCap);
    }


    /*
    REFACTORED STUFF BELOW
     */
    @GetMapping
    public List<Room> getAll() {
        return roomRepository.findAll();
    }

    @PostMapping
    Room saveRoom(@RequestBody Room room) {
        if (roomRepository.existsRoomByRoomCode(room.getRoomCode())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room already exists!");
        }
        return roomRepository.save(room);
    }

    @PutMapping
    Room updateRoom(@RequestBody Room room) {
        if (!roomRepository.existsRoomByRoomCode(room.getRoomCode())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room does not exists!");
        }
        return roomRepository.save(room);
    }

    @DeleteMapping(value = "/{roomCode}")
    ResponseEntity<String> deleteRoom(@PathVariable String roomCode) {
        boolean exists = roomRepository.existsRoomByRoomCode(roomCode);
        if (!exists) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        roomRepository.deleteById(roomCode);
        return new ResponseEntity<>(roomCode, HttpStatus.OK);
    }

}
