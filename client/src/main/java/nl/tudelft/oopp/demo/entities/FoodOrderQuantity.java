package nl.tudelft.oopp.demo.entities;

import nl.tudelft.oopp.demo.entities.reservation.FoodOrder;

public class FoodOrderQuantity {
    private FoodOrder foodOrder;
    private Food food;
    private int quantity;

    /**
     * Creates a food order quantity.
     * This holds a food-foodOrder pair and holds
     * the quantity of the food bought in the order
     */
    public FoodOrderQuantity() {

    }

    public FoodOrder getFoodOrder() {
        return foodOrder;
    }

    public void setFoodOrder(FoodOrder foodOrder) {
        this.foodOrder = foodOrder;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
