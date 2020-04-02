package nl.tudelft.oopp.demo.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

    private Integer id;
    private String name;
    private Integer buildingCode;
    private String description;
    private Restaurant restaurant;

    @BeforeEach
    void setUpper() {
        id = 123;
        name = "test restaurant";
        buildingCode = 4;
        description = "tasty nom nom";
        restaurant = new Restaurant(id, name, buildingCode, description);
    }

    @Test
    void constructorTest() {
        assertNotNull(restaurant);
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
    void getNameTest() {
        assertEquals(name, restaurant.getName());
    }

    @Test
    void setNameTest() {
        restaurant.setName("the best restaurant");
        assertEquals("the best restaurant", restaurant.getName());
    }

    @Test
    void getBuildingCodeTest() {
        assertEquals(buildingCode, restaurant.getBuildingCode());
    }

    @Test
    void setBuildingCodeTest() {
        restaurant.setBuildingCode(2525);
        assertEquals(2525, restaurant.getBuildingCode().intValue());
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
        Restaurant otherRestaurant = new Restaurant(id, name, buildingCode, description);
        assertTrue(otherRestaurant.equals(restaurant));
    }

    @Test
    void equalsTestTrue2() {
        Restaurant otherRestaurant = new Restaurant(id, name, buildingCode, description);
        assertTrue(otherRestaurant.equals(otherRestaurant));
    }

    @Test
    void equalsTestFalse() {
        Object notARestaurant = new Object();
        assertFalse(restaurant.equals(notARestaurant));
    }
}