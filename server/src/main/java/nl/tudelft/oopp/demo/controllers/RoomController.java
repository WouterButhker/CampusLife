package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/rooms")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping(path = "/all")
    public List<Room> getAll() {
       return roomRepository.findAll();
    }

    @GetMapping(path = "/add")
    public @ResponseBody String addNewUser(@RequestParam String roomCode, @RequestParam String name, @RequestParam Integer capacity, @RequestParam boolean hasWhiteboard, @RequestParam boolean hasTV, @RequestParam Integer rights, @RequestParam Integer building){
        //String roomCode, String name, Integer capacity, boolean hasWhiteboard, boolean hasTV, Integer rights, Integer building
        Room room = new Room(roomCode, name, capacity, hasWhiteboard, hasTV, rights, building);
        roomRepository.save(room);
        return "Saved";
    }

}
