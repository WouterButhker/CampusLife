package nl.tudelft.oopp.demo.controllers;

import java.util.List;
import nl.tudelft.oopp.demo.entities.BikeReservation;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.BikeReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @PutMapping
    BikeReservation updateBikeReservation(@RequestBody BikeReservation bikeReservation) {
        if (!bikeReservationRepository.existsBikeReservationById(bikeReservation.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Bike reservation does not exist!");
        }
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


}
