package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.*;

import nl.tudelft.oopp.demo.repositories.BuildingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BuildingTest {

    private Integer code;
    private String name;
    private String location;
    private String openingHours;
    private Integer bikes;
    private Building building;

    @BeforeEach
    void setUpper() {
        // TODO fix to work with images

        code = 42069;
        name = "The Arena";
        location = "CityStreetRoute";
        openingHours = "08:00-22:00";
        bikes = 5;
        building = new Building(code, name, location, openingHours, bikes);

    }

    @Test
    void emptyConstructorTest() {
        assertNotNull(new Building());
    }

    @Test
    void constructorTest() {
        assertNotNull(building);
    }

    @Test
    void getBuildingCodeTest() {
        assertEquals(code, building.getBuildingCode());
    }

    @Test
    void setBuildingCodeTest() {
        building.setBuildingCode(1337);
        assertEquals(1337, building.getBuildingCode());
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
        Integer bikeAmount = 22;
        building.setBikes(bikeAmount);
        assertEquals(bikeAmount, building.getBikes());
    }

    @Test
    void toStringTest() {
        assertEquals("building{buildingcode: 42069, name: The Arena, location: CityStreetRoute"
                + ", opening hours: 08:00-22:00, bikes: 5}", building.toString());
    }

    @Test
    void equalsTest() {
        // TODO fix to work with images
        /*
        Building buildingCopy = new Building(code, name, location, openingHours, bikes);
        assertEquals(building, buildingCopy);
         */
    }

    @Test
    void hashCodeTest() {
        assertTrue(Integer.class.isInstance(building.hashCode()));
    }

    @Test
    public void saveAndRetrieveQuote() {
        // TODO rename
        // TODO fix to work with images
        /*
        Integer buildingCode = 1;
        String name = "Delete Me";
        String location = "Please";
        String openingHours = "08:00-22:00";
        Building building = new Building(buildingCode, name, location, openingHours, bikes);
        buildingRepository.save(building);

        Building building2 = buildingRepository.getOne((Integer) 1);
        Assertions.assertEquals(building, building2);
        System.out.println(building2.toString());
         */
    }

    @Test
    void equalsSameBuildingTest() {
        assertTrue(building.equals(building));
    }

    @Test
    void equalsNotSameObject() {
        assertFalse(building.equals("abc"));
    }

    @Test
    void equalsWithSomeOtherBuilding() {
        Building building2 = new Building(code, name, location, openingHours, bikes);
        assertTrue(building.equals(building2));
    }
}