package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.util.List;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.image.BuildingImage;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.image.BuildingImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping(path = "/buildings")
public class BuildingController {

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private BuildingImageRepository buildingImageRepository;

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

    @Modifying
    @PutMapping(value = "/image/{buildingCode}")
    ResponseEntity<BuildingImage> uploadFile(@PathVariable Integer buildingCode,
                                             @RequestParam("file") MultipartFile file)
            throws IOException {
        if (!buildingRepository.existsBuildingByBuildingCode(buildingCode)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Building building = buildingRepository.findBuildingByBuildingCode(buildingCode);

        if (buildingImageRepository.existsByBuilding(building)) {
            buildingImageRepository.deleteByBuilding(building);
        }
        String fileName = ImageController.checkFile(file);
        BuildingImage buildingImage = new BuildingImage(fileName, file.getContentType(),
                file.getBytes(), building);
        buildingImageRepository.save(buildingImage);
        return new ResponseEntity<>(buildingImage, HttpStatus.OK);
    }

    @GetMapping("/image/getUrl/{buildingCode}")
    String getUrl(@PathVariable Integer buildingCode) {
        Building building = buildingRepository.findBuildingByBuildingCode(buildingCode);
        if (buildingImageRepository.existsByBuilding(building)) {
            BuildingImage buildingImage = buildingImageRepository.findByBuilding(building);
            return ImageController.getUrl("/buildings/image/downloadFile/", buildingImage);
        }
        return null;
    }

    @GetMapping("/image/downloadFile/{imageId}")
    ResponseEntity<Resource> downloadFile(@PathVariable String imageId) {
        return ImageController.downloadFile(buildingImageRepository.findByImageId(imageId));
    }

}
