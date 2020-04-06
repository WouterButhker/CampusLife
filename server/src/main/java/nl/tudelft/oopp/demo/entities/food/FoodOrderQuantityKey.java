package nl.tudelft.oopp.demo.entities.food;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;


@Embeddable
public class FoodOrderQuantityKey implements Serializable {

    @Column(name = "food_id")
    private int foodId;

    @Column(name = "order_id")
    private int orderId;

    public FoodOrderQuantityKey() {

    }

    public FoodOrderQuantityKey(int foodId, int orderId) {
        this.foodId = foodId;
        this.orderId = orderId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FoodOrderQuantityKey)) {
            return false;
        }
        FoodOrderQuantityKey that = (FoodOrderQuantityKey) o;
        return foodId == that.foodId
                && orderId == that.orderId;
    }
}
