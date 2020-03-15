package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.BikeReservation;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.BikeReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping(consumes = "application/json", produces = "application/json")
    BikeReservation addNewBikeReservation(@RequestBody BikeReservation bikeReservation) {
        return bikeReservationRepository.save(bikeReservation);
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<Integer> deleteBikeReservation(@PathVariable Integer id) {
        boolean exists = bikeReservationRepository.findById(id).isPresent();

        if (!exists) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        bikeReservationRepository.deleteById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping(path = "/add")
    public @ResponseBody String addNewBikeReservation(@RequestParam User user,
                                                      @RequestParam Building pickUpBuilding,
                                                      @RequestParam Building dropOffBuilding,
                                                      @RequestParam String date,
                                                      @RequestParam String slot) {
        BikeReservation bikeReservation = new BikeReservation(user, pickUpBuilding,
                dropOffBuilding, date, slot);
        bikeReservationRepository.save(bikeReservation);
        return "Saved";
    }
}
