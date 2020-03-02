package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Reservation;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.ReservationRepository;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/reservations")
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping(path = "/all")
    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }


    @GetMapping(path = "/add")
    public @ResponseBody String addNewReservation(@RequestParam User user,
                                                  @RequestParam Room room,
                                                  @RequestParam String timeSlot) {
        Reservation reservation = new Reservation(user, room, timeSlot);
        reservationRepository.save(reservation);
        return "Saved";
    }

}
