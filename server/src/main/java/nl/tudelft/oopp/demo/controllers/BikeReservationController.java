package nl.tudelft.oopp.demo.controllers;

import java.util.List;
import nl.tudelft.oopp.demo.entities.BikeReservation;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.BikeReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/bikeReservations")
public class BikeReservationController {

    @Autowired
    private BikeReservationRepository bikeReservationRepository;

    @GetMapping("/all")
    public List<BikeReservation> getAll() {
        return bikeReservationRepository.findAll();
    }

    /**
     * Add new bike reservation to the database.
     * @param user the user
     * @param building the building
     * @param slot the timeslot
     * @return "Saved"
     */
    @GetMapping("/addBike")
    public @ResponseBody String addNewBikeReservation(@RequestParam User user,
                                                      @RequestParam Building building,
                                                      @RequestParam String slot) {
        String timeSlot = slot.substring(11, 19) + slot.substring(30);
        String date = slot.substring(0, 10);
        BikeReservation bikeReservation = new BikeReservation(user, building, date, slot);
        bikeReservationRepository.save(bikeReservation);
        return "Saved";
    }
}
