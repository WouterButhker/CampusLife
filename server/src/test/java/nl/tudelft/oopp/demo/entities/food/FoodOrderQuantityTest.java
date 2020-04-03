package nl.tudelft.oopp.demo.entities.food;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FoodOrderQuantityTest {

    private FoodOrderQuantityKey id;
    private Food food;
    private FoodOrder foodOrder;
    private int quantity;
    private FoodOrderQuantity foodOrderQuantity;

    @BeforeEach
    void setUpper() {
        id = new FoodOrderQuantityKey(12, 90);
        food = new Food(12, 5, "Test name", 1.99);
        foodOrder = new FoodOrder(123, "1-1-1970", "10:00-20:00", 5);
        quantity = 69;
        foodOrderQuantity = new FoodOrderQuantity(food, foodOrder, quantity);
        foodOrderQuantity.setId(id);
    }

    @Test
    void emptyConstructorTest() {
        assertNotNull(new FoodOrderQuantity());
    }

    @Test
    void constructorTest() {
        assertNotNull(foodOrderQuantity);
    }

    @Test
    void constructorTest2() {
        assertNotNull(new FoodOrderQuantity(food, foodOrder));
    }

    @Test
    void getIdTest() {
        assertEquals(id, foodOrderQuantity.getId());
    }

    @Test
    void setIdTest() {
        FoodOrderQuantityKey testKey = new FoodOrderQuantityKey(456, 789);
        foodOrderQuantity.setId(testKey);
        assertEquals(testKey, foodOrderQuantity.getId());
    }

    @Test
    void getFoodTest() {
        assertEquals(food, foodOrderQuantity.getFood());
    }

    @Test
    void setFoodTest() {
        Food testFood = new Food(456, 789, "Test food", 6.99);
        foodOrderQuantity.setFood(testFood);
        assertEquals(testFood, foodOrderQuantity.getFood());
    }

    @Test
    void getFoodOrderTest() {
        assertEquals(foodOrder, foodOrderQuantity.getFoodOrder());
    }

    @Test
    void setFoodOrderTest() {
        FoodOrder testFoodOrder = new FoodOrder(123, "now", "slot", 456);
        foodOrderQuantity.setFoodOrder(testFoodOrder);
        assertEquals(testFoodOrder, foodOrderQuantity.getFoodOrder());
    }

    @Test
    void getQuantityTest() {
        assertEquals(quantity, foodOrderQuantity.getQuantity());
    }

    @Test
    void setQuantityTest() {
        foodOrderQuantity.setQuantity(30);
        assertEquals(30, foodOrderQuantity.getQuantity());
    }

    @Test
    void equalsSameTest() {
        assertTrue(foodOrderQuantity.equals(foodOrderQuantity));
    }

    @Test
    void equalsNotInstanceOfTest() {
        assertFalse(foodOrderQuantity.equals(new Object()));
    }

    @Test
    void equalsOtherTestTrue() {
        FoodOrderQuantity testFoodOrderQuantity = new FoodOrderQuantity(food, foodOrder, quantity);
        foodOrderQuantity.setId(id);
        assertTrue(foodOrderQuantity.equals(testFoodOrderQuantity));
    }
}