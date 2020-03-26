package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.image.RoomImage;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import nl.tudelft.oopp.demo.repositories.image.RoomImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/rooms")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomImageRepository roomImageRepository;

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

    @Modifying
    @PutMapping(value = "/image/{roomCode}")
    ResponseEntity<RoomImage> uploadFile(@PathVariable String roomCode,
                                         @RequestParam("file") MultipartFile file)
            throws IOException {
        if (!roomRepository.existsRoomByRoomCode(roomCode)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Room room = roomRepository.findRoomByRoomCode(roomCode);
        if (roomImageRepository.existsByRoom(room)) {
            roomImageRepository.deleteByRoom(room);
        }
        String fileName = ImageController.checkFile(file);
        RoomImage roomImage = new RoomImage(fileName, file.getContentType(), file.getBytes(), room);
        roomImageRepository.save(roomImage);
        return new ResponseEntity<>(roomImage, HttpStatus.OK);
    }

    @GetMapping("/image/getUrl/{roomCode}")
    String getUrl(@PathVariable String roomCode) {
        Room room = roomRepository.findRoomByRoomCode(roomCode);
        if (roomImageRepository.existsByRoom(room)) {
            RoomImage roomImage = roomImageRepository.findByRoom(room);
            return ImageController.getUrl("/rooms/image/downloadFile/", roomImage);
        }
        return null;
    }

    @GetMapping("/image/downloadFile/{imageId}")
    ResponseEntity<Resource> downloadFile(@PathVariable String imageId) {
        return ImageController.downloadFile(roomImageRepository.findByImageId(imageId));
    }

}
