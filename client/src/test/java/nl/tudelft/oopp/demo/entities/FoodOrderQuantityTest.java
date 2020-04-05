package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.*;

import nl.tudelft.oopp.demo.entities.reservation.FoodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FoodOrderQuantityTest {

    private Restaurant restaurant;
    private FoodOrder foodOrder;
    private Food food;
    private int quantity;
    private FoodOrderQuantity foodOrderQuantity;

    @BeforeEach
    void setUpper() {
        restaurant = new Restaurant(789, "test restaurant", 50, "d");
        foodOrder = new FoodOrder(new User(123), "1-1-1970", "10:00-20:00", restaurant);
        food = new Food(12, "Test name", 5, 1.99);
        quantity = 5;
        foodOrderQuantity = new FoodOrderQuantity();
    }

    @Test
    void getFoodOrder() {
        assertEquals(null, foodOrderQuantity.getFoodOrder());
    }

    @Test
    void setFoodOrder() {
        foodOrderQuantity.setFoodOrder(foodOrder);
        assertEquals(foodOrder, foodOrderQuantity.getFoodOrder());
    }

    @Test
    void getFood() {
        assertEquals(null, foodOrderQuantity.getFood());
    }

    @Test
    void setFood() {
        foodOrderQuantity.setFood(food);
        assertEquals(food, foodOrderQuantity.getFood());
    }

    @Test
    void getQuantity() {
        assertEquals(0, foodOrderQuantity.getQuantity());
    }

    @Test
    void setQuantity() {
        foodOrderQuantity.setQuantity(quantity);
        assertEquals(quantity, foodOrderQuantity.getQuantity());
    }
}