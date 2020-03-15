package nl.tudelft.oopp.demo.entities;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "food_order_junction")
public class FoodOrderJunction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "orderId", updatable = false, nullable = false)
    private Integer orderId;

    @Column(name = "foodId", updatable = false, nullable = false)
    private Integer foodId;

    @Column(name = "quantity")
    private Integer quantity;

    public FoodOrderJunction() {

    }

    /**
     * Creates a FoodOrderJunction.
     * @param id the id
     * @param orderId the orderId
     * @param foodId the foodId
     * @param quantity the quantity
     */
    public FoodOrderJunction(Integer id, Integer orderId, Integer foodId, Integer quantity) {
        this.id = id;
        this.orderId = orderId;
        this.foodId = foodId;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
