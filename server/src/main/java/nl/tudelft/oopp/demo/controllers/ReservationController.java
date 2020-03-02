package nl.tudelft.oopp.demo.controllers;

import java.util.List;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Reservation;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.ReservationRepository;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
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


    /**
     * Add a new Reservation to the database.
     * @param user the User that makes the reservation
     * @param room the Room that is reserved
     * @param timeSlot the time at which the Room is reserved
     * @return
     */
    @GetMapping(path = "/add")
    public @ResponseBody String addNewReservation(@RequestParam User user,
                                                  @RequestParam Room room,
                                                  @RequestParam String timeSlot) {
        Reservation reservation = new Reservation(user, room, timeSlot);
        reservationRepository.save(reservation);
        return "Saved";
    }

}
