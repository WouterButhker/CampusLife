package nl.tudelft.oopp.demo.controllers;

import java.util.List;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.entities.reservation.BikeReservation;
import nl.tudelft.oopp.demo.entities.reservation.RoomReservation;
import nl.tudelft.oopp.demo.repositories.BikeReservationRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/all")
    public List<BikeReservation> getAll() {
        return bikeReservationRepository.findAll();
    }

    @PostMapping
    BikeReservation addNewBikeReservation(@RequestBody BikeReservation bikeReservation) {
        return bikeReservationRepository.save(bikeReservation);
    }

    @GetMapping(path = "/myReservations")
    public List<BikeReservation> getAllForUser(@RequestParam Integer user) {
        User actualUser = userRepository.findUserById(user);
        return bikeReservationRepository.findAllByUser(actualUser);
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
