package nl.tudelft.oopp.demo.controllers;

import java.util.List;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
     * @param openingHours format aa:bb-cc:dd for every day of the week separated by a ","
     * @return Saved
     */
    @GetMapping(path = "/add")
    public @ResponseBody
    String addNewBuilding(@RequestParam Integer buildingCode,
                          @RequestParam String name,
                          @RequestParam String location,
                          @RequestParam String openingHours,
                          @RequestParam Integer bikes) {
        Building building = new Building(buildingCode, name, location, openingHours, bikes);
        buildingRepository.save(building);
        return "Saved";
    }

    //@RequestMapping(path = "/addBuilding", method = RequestMethod.POST,
    //consumes = "application/json", produces = "application/json")
    //@PostMapping(path = "/addBuilding", consumes = "application/json",
    //produces = "application/json")
    //public void addBuilding(@RequestBody Building building) {
    //buildingRepository.save(building);
    //}

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

    @Transactional
    @GetMapping(path = "/delete")
    public Integer deleteBuilding(@RequestParam Integer buildingCode) {
        return buildingRepository.deleteBuildingWithCode(buildingCode);
    }

    @GetMapping(path = "/count")
    public Integer countBuildings() {
        return buildingRepository.countAllBuildings();
    }
}
