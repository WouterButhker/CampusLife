package nl.tudelft.oopp.demo.controllers;


import com.sipios.springsearch.anotation.SearchSpec;
import java.util.List;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping(path = "/rooms")
public class RoomController {
    @Autowired
    private final RoomRepository roomRepository;

    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @GetMapping(path = "/getFilteredRooms")
    public List<Room> getFilteredRooms(@SearchSpec Specification<Room> specs) {
        return roomRepository.findAll(Specification.where(specs));
    }

    @GetMapping(path = "/all")
    public List<Room> getAll(@SearchSpec Specification<Room> specs) {
        return roomRepository.findAll(Specification.where(specs));
    }

    @GetMapping(path = "/getAllRoomsFromBuilding")
    public List<Room> getAllRoomsFromBuilding(@SearchSpec Specification<Room> specs) {
        return roomRepository.findAll(Specification.where(specs));
    }

    /**
     * Adds a new room in the database.
     * @param roomCode the abreviation of the room
     * @param name the actual name of the room
     * @param capacity how many spaces when empty
     * @param hasWhiteboard if the room has a whiteboard
     * @param hasTV if the room has a TV
     * @param rights (minimum) required to reserve this room
     * @param building in which the room is
     */
    @GetMapping(path = "/add")
    public @ResponseBody String addNewRoom(@RequestParam String roomCode,
                                           @RequestParam String name,
                                           @RequestParam Integer capacity,
                                           @RequestParam boolean hasWhiteboard,
                                           @RequestParam boolean hasTV,
                                           @RequestParam Integer rights,
                                           @RequestParam Building building) {
        Room room = new Room(roomCode, name, capacity, hasWhiteboard, hasTV, rights, building);
        roomRepository.save(room);
        return "Saved";
    }

    @GetMapping(path = "/getRoomNamesFromBuilding")
    public List<String> getAllRoomNamesFromBuilding(@RequestParam Building building) {
        return roomRepository.allRoomNamesFromBuilding(building);
    }

    @Transactional
    @GetMapping(path = "/delete")
    public Integer deleteRoom(@RequestParam String roomCode) {
        return roomRepository.deleteRoomWithCode(roomCode);
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
}
