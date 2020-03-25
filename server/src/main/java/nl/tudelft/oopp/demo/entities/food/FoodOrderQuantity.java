package nl.tudelft.oopp.demo.entities.food;

import java.util.Objects;
import javax.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "food_order_quantity")
public class FoodOrderQuantity {

    @EmbeddedId
    private FoodOrderQuantityKey id;

    @ManyToOne
    @MapsId("foodId")
    @JoinColumn(name = "food_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Food food;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private FoodOrder foodOrder;

    private int quantity;

    public FoodOrderQuantity() {

    }

    public FoodOrderQuantity(Food food, FoodOrder foodOrder) {
        this.food = food;
        this.foodOrder = foodOrder;
        this.id = new FoodOrderQuantityKey(food.getId(), foodOrder.getId());
    }

    public FoodOrderQuantity(Food food, FoodOrder foodOrder, int quantity) {
        this.food = food;
        this.foodOrder = foodOrder;
        this.id = new FoodOrderQuantityKey(food.getId(), foodOrder.getId());
        this.quantity = quantity;
    }

    public FoodOrderQuantityKey getId() {
        return id;
    }

    public void setId(FoodOrderQuantityKey id) {
        this.id = id;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public FoodOrder getFoodOrder() {
        return foodOrder;
    }

    public void setFoodOrder(FoodOrder foodOrder) {
        this.foodOrder = foodOrder;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FoodOrderQuantity)) return false;
        FoodOrderQuantity that = (FoodOrderQuantity) o;
        return food.equals(that.food)
                && foodOrder.equals(that.foodOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(food, foodOrder);
    }
}
