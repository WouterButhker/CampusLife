package nl.tudelft.oopp.demo.controllers;

import java.util.List;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/buildings")
public class BuildingController {

    @Autowired
    private BuildingRepository buildingRepository;

    @GetMapping(path = "/all")
    public List<Building> getAll() {
        return buildingRepository.findAll();
    }

    /**
     * Adds a new building to the database.
     * @param buildingCode the number of the building
     * @param name the actual (full) name
     * @param location street
     * @param openingHours format aa:bb-cc:dd
     * @return Saved
     */
    @GetMapping(path = "/add")
    public @ResponseBody String addNewBuilding(@RequestParam Integer buildingCode,
                                           @RequestParam String name,
                                           @RequestParam String location,
                                           @RequestParam String openingHours) {
        Building room = new Building(buildingCode, name, location, openingHours);
        buildingRepository.save(room);
        return "Saved";
    }

    /**
     * Get a list of codes and names of all the buildings.
     * @return a list with format "number name"
     */
    @GetMapping(path = "/code+name")
    public List<String> getBuildingsCodeAndName() {
        List<String> response = buildingRepository.findAllBuildingsCodeAndName();
        for (int i = 0; i < response.size(); i++) {
            String current = response.get(i);
            response.set(i, current.replace(',', ' '));
        }
        return response;
    }
}
