package nl.tudelft.oopp.demo.testFolder;

import nl.tudelft.oopp.demo.entities.Building;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildingTest {

    private Integer code;
    private String name;
    private String location;
    private String openingHours;
    private String image;
    private Integer bikes;
    private Building b;

    @BeforeEach
    void setUpper() {
        code = 42069;
        name = "The Arena";
        location = "CityStreetRoute";
        openingHours = "08:00-22:00";
        image = "https://cdn.bulbagarden.net/upload/3/36/Canalave_Gym_anime.png";
        bikes = 5;
        b = new Building(code, name, location, openingHours, image, bikes);
    }

    @Test
    void constructorTest() {
        assertNotNull(b);
    }

    @Test
    void getCodeTest() {
        assertEquals(code, b.getCode());
    }

    @Test
    void setCodeTest() {
        b.setCode(1337);
        assertEquals(1337, b.getCode());
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
    void getImageTest() {
        assertEquals(image, b.getImage());
    }

    @Test
    void setImageTest() {
        b.setImage("https://cdn.bulbagarden.net/upload/7/79/Canalave_Gym_Battlefield.png");
        assertEquals("https://cdn.bulbagarden.net/upload/7/79/Canalave_Gym_Battlefield.png", b.getImage());
    }

    @Test
    void getBikesTest() {
        assertEquals(bikes, b.getBikes());
    }

    @Test
    void toStringTest() {
        assertEquals("{" + code + ", " + name + ", " + location + ", " + openingHours + ", " + "image" + "}", b.toString());
    }

    @Test
    void equalsTest() {
        Building bCopy = new Building(code, name, location, openingHours, image, bikes);
        assertEquals(b, bCopy);
    }

    @Test
    void hashCodeTest() {
        assertTrue(Integer.class.isInstance(b.hashCode()));
    }
}