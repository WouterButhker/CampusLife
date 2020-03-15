package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.BikeReservation;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.BikeReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/bikeReservations")
public class BikeReservationController {

    @Autowired
    private BikeReservationRepository bikeReservationRepository;

    @GetMapping(path = "/all")
    public List<BikeReservation> getAll() {
        return bikeReservationRepository.findAll();
    }

    @GetMapping(path = "/add")
    public @ResponseBody String addNewBikeReservation(@RequestParam User user,
                                                      @RequestParam Building building,
                                                      @RequestParam String date,
                                                      @RequestParam String slot) {
        BikeReservation bikeReservation = new BikeReservation(user, building, date, slot);
        bikeReservationRepository.save(bikeReservation);
        return "Saved";
    }
}
