package nl.tudelft.oopp.demo.testfolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.tudelft.oopp.demo.entities.Building;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BuildingTest {

    private Integer code;
    private String name;
    private String location;
    private String openingHours;
    private String image;
    private Integer bikes;
    private Building building;

    @BeforeEach
    void setUpper() {
        code = 42069;
        name = "The Arena";
        location = "CityStreetRoute";
        openingHours = "08:00-22:00";
        image = "https://cdn.bulbagarden.net/upload/3/36/Canalave_Gym_anime.png";
        bikes = 5;
        building = new Building(code, name, location, openingHours, image, bikes);
    }

    @Test
    void constructorTest() {
        assertNotNull(building);
    }

    @Test
    void getCodeTest() {
        assertEquals(code, building.getCode());
    }

    @Test
    void setCodeTest() {
        building.setCode(1337);
        assertEquals(1337, building.getCode());
    }

    @Test
    void getNameTest() {
        assertEquals(name, building.getName());
    }

    @Test
    void setNameTest() {
        building.setName("Canalave gym");
        assertEquals("Canalave gym", building.getName());
    }

    @Test
    void getLocationTest() {
        assertEquals(location, building.getLocation());
    }

    @Test
    void setLocationTest() {
        building.setLocation("Canalave City");
        assertEquals("Canalave City", building.getLocation());
    }

    @Test
    void getOpeningHoursTest() {
        assertEquals(openingHours, building.getOpeningHours());
    }

    @Test
    void setOpeningHoursTest() {
        building.setOpeningHours("09:00-21:61");
        assertEquals("09:00-21:61", building.getOpeningHours());
    }

    @Test
    void getImageTest() {
        assertEquals(image, building.getImage());
    }

    @Test
    void setImageTest() {
        building.setImage("https://cdn.bulbagarden.net/upload/7/79/Canalave_Gym_Battlefield.png");
        assertEquals("https://cdn.bulbagarden.net/upload/7/79/Canalave_Gym_Battlefield.png", building.getImage());
    }

    @Test
    void getBikesTest() {
        assertEquals(bikes, building.getBikes());
    }

    @Test
    void toStringTest() {
        assertEquals("{" + code + ", " + name + ", " + location
                + ", " + openingHours + ", " + "image" + "}", building.toString());
    }

    @Test
    void equalsTest() {
        Building buildingCopy = new Building(code, name, location, openingHours, image, bikes);
        assertEquals(building, buildingCopy);
    }

    @Test
    void hashCodeTest() {
        assertTrue(Integer.class.isInstance(building.hashCode()));
    }
}