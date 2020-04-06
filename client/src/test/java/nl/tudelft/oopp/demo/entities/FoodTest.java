package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FoodTest {

    private Integer id;
    private String name;
    private Integer restaurant;
    private Double price;
    private Food testFood;

    @BeforeEach
    void setUpper() {
        id = 123;
        name = "Test food";
        restaurant = 5;
        price = 42.0;
        testFood = new Food(id, name, restaurant, price);
    }

    @Test
    void constructorTest() {
        assertNotNull(testFood);
    }

    @Test
    void getIdTest() {
        assertEquals(id, testFood.getId());
    }

    @Test
    void setIdTest() {
        testFood.setId(22);
        assertEquals(Integer.valueOf(22), testFood.getId());
    }

    @Test
    void getNameTest() {
        assertEquals(name, testFood.getName());
    }

    @Test
    void setNameTest() {
        testFood.setName("other name");
        assertEquals("other name", testFood.getName());
    }

    @Test
    void getRestaurantTest() {
        assertEquals(restaurant, testFood.getRestaurant());
    }

    @Test
    void setRestaurantTest() {
        testFood.setRestaurant(12);
        assertEquals(12, testFood.getRestaurant().intValue());
    }

    @Test
    void getPriceTest() {
        assertEquals(price, testFood.getPrice());
    }

    @Test
    void setPriceTest() {
        price = 12.50;
        testFood.setPrice(price);
        assertEquals(price, testFood.getPrice());
    }

    @Test
    void equalsTrueTest() {
        Food otherFood = new Food(id, name, restaurant, price);
        assertTrue(otherFood.equals(testFood));
    }

    @Test
    void equalsWithItselfTest() {
        assertTrue(testFood.equals(testFood));
    }

    @Test
    void equalsFalseTest() {
        assertFalse(testFood.equals(new Object()));
    }
}