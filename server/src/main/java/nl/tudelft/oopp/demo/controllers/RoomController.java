package nl.tudelft.oopp.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/rooms")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping(path = "/all")
    public List<Room> getAll() {
        return roomRepository.findAll();
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


    @GetMapping(path = "/getRoomsFromBuilding")
    public List<Room> getAllRoomsFromBuilding(@RequestParam Building building) {
        return roomRepository.allRoomsFromBuilding(building);
    }

    @GetMapping(path = "/getRoomNamesFromBuilding")
    public List<String> getAllRoomNamesFromBuilding(@RequestParam Building building) {
        return roomRepository.allRoomNamesFromBuilding(building);
    }
}
