package nl.tudelft.oopp.demo;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Quote;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import nl.tudelft.oopp.demo.repositories.QuoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.web.bind.annotation.RequestParam;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class BuildingTest {
    @Autowired
    private BuildingRepository buildingRepository;

    @Test
    public void saveAndRetrieveQuote() {
        Integer buildingCode = 1;
        String name = "Delete Me";
        String location = "Please";
        String openingHours = "08:00-22:00";
        Building building = new Building(buildingCode, name, location, openingHours);
        buildingRepository.save(building);

        Building building2 = buildingRepository.getOne((Integer) 1);
        assertEquals(building, building2);
        System.out.println(building2.toString());
    }
}