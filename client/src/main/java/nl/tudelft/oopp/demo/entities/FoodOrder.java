package nl.tudelft.oopp.demo.entities;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import java.util.*;

public class FoodOrder {
    private Integer id;
    private Integer restaurant;
    private Integer user;
    private List<List<Integer>> foodsList;

    /**
     * Creates a new FoodOrder object.
     * @param id the id of the order
     * @param restaurant the restaurant where the order is done
     * @param user the id of the user that ordered
     */
    public FoodOrder(Integer id,
                     Integer restaurant,
                     Integer user) {
        this.id = id;
        this.restaurant = restaurant;
        this.user = user;
        this.foodsList = new ArrayList<>();
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

    /**
     * Adds a food to the order.
     * @param food the food to be added
     */
    public void addFood(Food food) {
        for (List<Integer> pair : foodsList) {
            if (pair.get(0).equals(food.getId())) {
                int quantity = pair.get(1);
                pair.set(1, quantity + 1);
                return;
            }
        }
        foodsList.add(Arrays.asList(food.getId(), 1));
    }

    /**
     * Removes a food from the order.
     * @param food the food to be removed
     */
    public void removeFood(Food food) {
        for (int i = 0; i < foodsList.size(); i++) {
            List<Integer> pair = foodsList.get(i);
            if (pair.get(0).equals(food.getId())) {
                int quantity = pair.get(1);
                pair.set(1, quantity - 1);

                if (quantity == 1) {
                    foodsList.remove(pair);
                }
                return;
            }
        }
    }

    public List<List<Integer>> getFoodsList() {
        return foodsList;
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
        return Objects.hash(id, restaurant, user);
    }
}
