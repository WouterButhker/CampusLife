package nl.tudelft.oopp.demo.controllers;

import java.util.List;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Reservation;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.ReservationRepository;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/reservations")
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping(path = "/all")
    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    @GetMapping(path = "/allForUser")
    public List<Reservation> getAllForUser(@RequestParam User user) {
        return reservationRepository.findAllByUser(user);
    }

    @GetMapping(path = "/allForRoom")
    public List<Reservation> getAllForRoom(@RequestParam Room room) {
        return reservationRepository.findAllByRoom(room);
    }

    /**
     * Add a new Reservation to the database.
     * @param user the User that makes the reservation
     * @param room the Room that is reserved
     * @param slot the time at which the Room is reserved
     * @return
     */
    @GetMapping(path = "/add")
    public @ResponseBody String addNewReservation(@RequestParam User user,
                                                  @RequestParam Room room,
                                                  @RequestParam String slot) {
        String timeSlot = slot.substring(11, 19) + slot.substring(30);
        String date = slot.substring(0, 10);
        System.out.println(date);
        System.out.println(timeSlot);
        Reservation reservation = new Reservation(user, room, date, slot);
        reservationRepository.save(reservation);
        return "Saved";
    }

    @GetMapping("/myReservations")
    public @ResponseBody List<Reservation> getMyReservations(@RequestParam User user) {
        return reservationRepository.getMyReservations(user);
    }

    @Transactional
    @GetMapping(path = "/delete")
    void deleteReservation(@RequestParam Integer id) {
        reservationRepository.deleteById(id);
    }
}
