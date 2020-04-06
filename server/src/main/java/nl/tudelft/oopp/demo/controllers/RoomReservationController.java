package nl.tudelft.oopp.demo.controllers;

import java.util.List;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.entities.reservation.RoomReservation;
import nl.tudelft.oopp.demo.repositories.RoomReservationRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/roomReservations")
public class RoomReservationController {

    @Autowired
    private RoomReservationRepository roomReservationRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/all")
    public List<RoomReservation> getAll() {
        return roomReservationRepository.findAll();
    }

    @GetMapping(path = "/allForUser")
    public List<RoomReservation> getAllForUser(@RequestParam Integer user) {
        User actualUser = userRepository.findUserById(user);
        return roomReservationRepository.findAllByUser(actualUser);
    }

    @GetMapping(path = "/allForRoom")
    public List<RoomReservation> getAllForRoom(@RequestParam Room room) {
        return roomReservationRepository.findAllByRoom(room);
    }

    /**
     * Add a new RoomReservation to the database.
     * @param user the User that makes the reservation
     * @param room the Room that is reserved
     * @param slot the time at which the Room is reserved
     * @return
     */
    @GetMapping(path = "/add")
    public @ResponseBody Integer addNewReservation(@RequestParam Integer user,
                                                  @RequestParam Room room,
                                                  @RequestParam String slot) {
        String timeSlot = slot.substring(11, 19) + slot.substring(30);
        String date = slot.substring(0, 10);
        System.out.println(date);
        System.out.println(timeSlot);
        User actualUser = userRepository.findUserById(user);
        RoomReservation reservation = new RoomReservation(actualUser, room, date, slot);
        roomReservationRepository.save(reservation);
        return reservation.getId();
    }

    @GetMapping("/myReservations")
    public @ResponseBody List<RoomReservation> getMyReservations(@RequestParam int user) {
        User actualUser = userRepository.findUserById(user);
        return roomReservationRepository.findAllByUser(actualUser);
    }

    @Transactional
    @GetMapping(path = "/delete")
    void deleteReservation(@RequestParam Integer id) {
        roomReservationRepository.deleteById(id);
    }
}
