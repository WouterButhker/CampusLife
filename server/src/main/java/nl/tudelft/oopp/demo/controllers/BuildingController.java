package nl.tudelft.oopp.demo.controllers;

import java.util.List;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/buildings")
public class BuildingController {

    @Autowired
    private BuildingRepository buildingRepository;

    @GetMapping
    public List<Building> getAll() {
        return buildingRepository.findAll();
    }

    @GetMapping(path = "/bikes")
    public List<Building> getBuildingsWithBikeStation() {
        return buildingRepository.findAllBuildingsWithBikeStation();
    }

    @PostMapping
    Building saveBuilding(@RequestBody Building building) {
        if (buildingRepository.existsBuildingByBuildingCode(building.getBuildingCode())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Building already exists!");
        }
        return buildingRepository.save(building);
    }

    @PutMapping
    Building updateBuilding(@RequestBody Building building) {
        if (!buildingRepository.existsBuildingByBuildingCode(building.getBuildingCode())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Building does not exists!");
        }
        return buildingRepository.save(building);

    }

    @GetMapping(path = "/{buildingCode}")
    Building getBuildingByCode(@PathVariable Integer buildingCode) {
        return buildingRepository.getBuildingByBuildingCode(buildingCode);
    }

    @DeleteMapping(value = "/{buildingCode}")
    ResponseEntity<Integer> deleteBuilding(@PathVariable Integer buildingCode) {
        boolean exists = buildingRepository.existsBuildingByBuildingCode(buildingCode);
        if (!exists) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        buildingRepository.deleteById(buildingCode);
        return new ResponseEntity<>(buildingCode, HttpStatus.OK);
    }

}
