package nl.tudelft.oopp.demo.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "food_order")
public class FoodOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "restaurant")
    private Integer restaurant;

    @Column(name = "user")
    private Integer user;

    @JsonInclude
    @Transient
    private List<List<Integer>> foodsList;

    public FoodOrder() {

    }

    /**
     * Constructor for a food order.
     * @param id the id of the order
     * @param restaurant the id of the restaurant where it is sold
     * @param user the user who placed the order
     */
    public FoodOrder(Integer id,
                     Integer restaurant,
                     Integer user) {
        this.id = id;
        this.restaurant = restaurant;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Integer restaurant) {
        this.restaurant = restaurant;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public List<List<Integer>> getFoodsList() {
        return foodsList;
    }

    public void setFoodsList(List<List<Integer>> foodsList) {
        this.foodsList = foodsList;
    }
}
