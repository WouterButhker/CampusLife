package nl.tudelft.oopp.demo.controllers;

import java.util.List;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.entities.reservation.PersonalReservation;
import nl.tudelft.oopp.demo.entities.reservation.RoomReservation;
import nl.tudelft.oopp.demo.repositories.PersonalReservationRepository;
import nl.tudelft.oopp.demo.repositories.RoomReservationRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/personalReservations")
public class PersonalReservationController {

    @Autowired
    private PersonalReservationRepository personalReservationRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/all")
    public List<PersonalReservation> getAll() {
        return personalReservationRepository.findAll();
    }

    /**
     * Add a new PersonalReservation to the database.
     * @param user the User that makes the reservation
     * @param slot the time at which the Room is reserved
     * @param activity the activity you are planning to do
     * @return
     */
    @GetMapping(path = "/add")
    public @ResponseBody String addNewReservation(@RequestParam Integer user,
                                                  @RequestParam String slot,
                                                  @RequestParam String activity) {
        String timeSlot = slot.substring(11, 19) + slot.substring(30);
        String date = slot.substring(0, 10);
        System.out.println(date);
        System.out.println(timeSlot);
        User actualUser = userRepository.findUserById(user);
        PersonalReservation reservation = new PersonalReservation(actualUser, date, slot, activity);
        System.out.println(reservation);
        personalReservationRepository.save(reservation);
        //personalReservationRepository.save(reservation);
        return "Saved";
    }

    @GetMapping("/myReservations")
    public @ResponseBody List<PersonalReservation> getMyReservations(@RequestParam int user) {
        User actualUser = userRepository.findUserById(user);
        return personalReservationRepository.findAllByUser(actualUser);
    }

    @Transactional
    @GetMapping(path = "/delete")
    void deleteReservation(@RequestParam Integer id) {
        personalReservationRepository.deleteById(id);
    }
}
