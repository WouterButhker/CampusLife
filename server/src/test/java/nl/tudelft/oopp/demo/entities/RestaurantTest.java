package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RestaurantTest {

    private Integer id;
    private String name;
    private Building building;
    private Integer buildingCode;
    private String description;
    private Restaurant restaurant;

    @BeforeEach
    void setUpper() {
        id = 123;
        name = "test restaurant";
        building = new Building(4, "Test Building", "Somewhere", "11:11-22:22", 42);
        buildingCode = 4;
        description = "tasty nom nom";
        restaurant = new Restaurant(id, name, building, description);
    }

    @Test
    void constructorTest() {
        Restaurant rest = new Restaurant();
        assertNotNull(rest);
    }

    @Test
    void getIdTest() {
        assertEquals(id, restaurant.getId());
    }

    @Test
    void setIdTest() {
        restaurant.setId(12345);
        assertEquals(12345, restaurant.getId().intValue());
    }

    @Test
    void getBuildingTest() {
        assertEquals(building, restaurant.getBuilding());
    }

    @Test
    void setBuildingTest() {
        Building testBuilding = new Building(33, "Some", "Body", "Once told me", 2);
        restaurant.setBuilding(testBuilding);
        assertEquals(testBuilding, restaurant.getBuilding());
    }

    @Test
    void getBuildingCodeTest() {
        assertEquals(buildingCode, restaurant.getBuildingCode());
    }

    @Test
    void getBuildingCodeTest2() {
        restaurant.setBuildingCode(null);
        assertEquals(buildingCode, restaurant.getBuildingCode());
    }

    @Test
    void setBuildingCodeTest() {
        restaurant.setBuildingCode(2525);
        assertEquals(2525, restaurant.getBuildingCode().intValue());
    }

    @Test
    void getNameTest() {
        assertEquals(name, restaurant.getName());
    }

    @Test
    void setNameTest() {
        restaurant.setName("the best restaurant");
        assertEquals("the best restaurant", restaurant.getName());
    }

    @Test
    void getDescriptionTest() {
        assertEquals(description, restaurant.getDescription());
    }

    @Test
    void setDescriptionTest() {
        restaurant.setDescription("yes");
        assertEquals("yes", restaurant.getDescription());
    }

    @Test
    void toStringTest() {
        assertEquals("{123, test restaurant, 4, tasty nom nom}", restaurant.toString());
    }

    @Test
    void equalsTestTrue() {
        Restaurant otherRestaurant = new Restaurant(id, name, building, description);
        assertTrue(otherRestaurant.equals(restaurant));
    }

    @Test
    void equalsTestTrue2() {
        Restaurant otherRestaurant = new Restaurant(id, name, building, description);
        assertTrue(otherRestaurant.equals(otherRestaurant));
    }

    @Test
    void equalsTestFalse() {
        Object notARestaurant = new Object();
        assertFalse(restaurant.equals(notARestaurant));
    }
}