package nl.tudelft.oopp.demo;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class BuildingTest {
    @Autowired
    private BuildingRepository buildingRepository;

    private Integer code;
    private String name;
    private String location;
    private String openingHours;
    private Building b;

    @BeforeEach
    void setUpper() {
        code = 42069;
        name = "The Arena";
        location = "CityStreetRoute";
        openingHours = "08:00-22:00";

        b = new Building(code, name, location, openingHours);
    }

    @Test
    void constructorTest() {
        assertNotNull(b);
    }

    @Test
    void getBuildingCodeTest() {
        assertEquals(code, b.getBuildingCode());
    }

    @Test
    void setBuildingCodeTest() {
        b.setBuildingCode(1337);
        assertEquals(1337, b.getBuildingCode());
    }

    @Test
    void getNameTest() {
        assertEquals(name, b.getName());
    }

    @Test
    void setNameTest() {
        b.setName("Canalave gym");
        assertEquals("Canalave gym", b.getName());
    }

    @Test
    void getLocationTest() {
        assertEquals(location, b.getLocation());
    }

    @Test
    void setLocationTest() {
        b.setLocation("Canalave City");
        assertEquals("Canalave City", b.getLocation());
    }

    @Test
    void getOpeningHoursTest() {
        assertEquals(openingHours, b.getOpeningHours());
    }

    @Test
    void setOpeningHoursTest() {
        b.setOpeningHours("09:00-21:61");
        assertEquals("09:00-21:61", b.getOpeningHours());
    }

    @Test
    void toStringTest() {
        assertEquals("[\"buildingCode\":\"" + code + "\",\"name\":\"" + name
                + "\",\"location\":\"" + location + "\",\"openingHours\":\"" + openingHours + "\"]"
                , b.toString());
    }

    @Test
    void equalsTest() {
        Building bCopy = new Building(code, name, location, openingHours);
        assertEquals(b, bCopy);
    }

    @Test
    void hashCodeTest() {
        assertTrue(Integer.class.isInstance(b.hashCode()));
    }

    @Test
    public void saveAndRetrieveQuote() {
        Integer buildingCode = 1;
        String name = "Delete Me";
        String location = "Please";
        String openingHours = "08:00-22:00";
        Building building = new Building(buildingCode, name, location, openingHours);
        buildingRepository.save(building);

        Building building2 = buildingRepository.getOne((Integer) 1);
        Assertions.assertEquals(building, building2);
        System.out.println(building2.toString());
    }
}