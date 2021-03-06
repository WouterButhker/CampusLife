package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.*;

import nl.tudelft.oopp.demo.communication.AuthenticationCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BuildingTest {

    private Integer code;
    private String name;
    private String location;
    private String openingHours;
    private Integer bikes;
    private Building building;


    @BeforeEach
    void setUpper() {
        code = 42069;
        name = "The Arena";
        location = "CityStreetRoute";
        openingHours = "08:00-22:00, 08:00-22:00, 08:00-22:00, 08:00-22:00, "
                + "08:00-22:00, 08:00-22:00, 08:00-22:00";
        bikes = 5;
        building = new Building(code, name, location, openingHours, bikes);
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
        int num = 1337;
        building.setCode(num);
        assertEquals(num, building.getCode().intValue());
    }

    @Test
    void getBuildingCodeTest() {
        assertEquals(code, building.getBuildingCode());
    }

    @Test
    void setBuildingCodeTest() {
        building.setBuildingCode(321123);
        assertEquals(321123, building.getBuildingCode().intValue());
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
    void getBikesTest() {
        assertEquals(bikes, building.getBikes());
    }

    @Test
    void setBikesTest() {
        building.setBikes(405);
        assertEquals(405, building.getBikes().intValue());
    }

    @Test
    void toStringTest() {
        assertEquals("{" + code + ", " + name + ", " + location
                + ", " + openingHours + ", " + bikes + "}", building.toString());
    }

    @Test
    void getNameAndCodeTest() {
        assertEquals("42069 The Arena", building.getNameAndCode());
    }

    @Test
    void equalsTest() {
        Building buildingCopy = new Building(code, name, location, openingHours, bikes); //image,
        assertTrue(building.equals(buildingCopy));
    }

    @Test
    void equalsTestFalse() {
        assertFalse(building.equals(new Object()));
    }

    @Test
    void hashCodeTest() {
        assertTrue(Integer.class.isInstance(building.hashCode()));
    }
}