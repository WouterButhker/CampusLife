package nl.tudelft.oopp.demo.entities.food;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FoodOrderQuantityKeyTest {

    private int foodId;
    private int orderId;
    private FoodOrderQuantityKey foodOrderQuantityKey;

    @BeforeEach
    void setUpper() {
        foodId = 12;
        orderId = 90;
        foodOrderQuantityKey = new FoodOrderQuantityKey(foodId, orderId);
    }

    @Test
    void constructorTest() {
        assertNotNull(foodOrderQuantityKey);
    }

    @Test
    void emptyConstructorTest() {
        assertNotNull(new FoodOrderQuantityKey());
    }

    @Test
    void getFoodIdTest() {
        assertEquals(foodId, foodOrderQuantityKey.getFoodId());
    }

    @Test
    void setFoodIdTest() {
        foodOrderQuantityKey.setFoodId(33);
        assertEquals(33, foodOrderQuantityKey.getFoodId());
    }

    @Test
    void getOrderIdTest() {
        assertEquals(orderId, foodOrderQuantityKey.getOrderId());
    }

    @Test
    void setOrderIdTest() {
        foodOrderQuantityKey.setOrderId(44);
        assertEquals(44, foodOrderQuantityKey.getOrderId());
    }

    @Test
    void equalsSameTest() {
        assertTrue(foodOrderQuantityKey.equals(foodOrderQuantityKey));
    }

    @Test
    void equalsNotInstanceOfTest() {
        assertFalse(foodOrderQuantityKey.equals(new Object()));
    }

    @Test
    void equalsOtherTestTrue() {
        assertTrue(foodOrderQuantityKey.equals(new FoodOrderQuantityKey(foodId, orderId)));
    }
}