package nl.tudelft.oopp.demo.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FoodOrder {

    private Integer id;
    private Integer restaurant;
    private Integer userId;
    private Map<Food, Integer> food;

    /**
     * Creates a new FoodOrder object.
     * @param id the id of the order
     * @param restaurant the restaurant where the order is done
     * @param userId the id of the user that ordered
     */
    public FoodOrder(Integer id,
                     Integer restaurant,
                     Integer userId) {
        this.id = id;
        this.restaurant = restaurant;
        this.userId = userId;
        this.food = new HashMap<>();
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void addFood(Food food) {
        if (this.food.containsKey(food)) {
            int quantity = this.food.get(food);
            this.food.put(food, quantity + 1);
        } else {
            this.food.put(food, 1);
        }
    }

    public void removeFood(Food food) {
        if (this.food.containsKey(food)) {
            int quantity = this.food.get(food) - 1;
            if (quantity == 0) {
                this.food.remove(food);
            } else {
                this.food.put(food, quantity);
            }
        }
    }

    public Map<Food, Integer> getFood() {
        return food;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FoodOrder)) {
            return false;
        }
        FoodOrder food = (FoodOrder) o;
        return getId().equals(((FoodOrder) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, restaurant, userId);
    }
}
