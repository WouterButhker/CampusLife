package nl.tudelft.oopp.demo.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FoodOrder extends Reservation {
    private Integer restaurant;
    private List<List<Integer>> foodsList;

    /**
     * Creates a new FoodOrder object.
     * @param restaurant the restaurant where the order is done
     */
    public FoodOrder(int userId, String date, String timeSlot, Integer restaurant) {
        super(userId, date, timeSlot);
        this.restaurant = restaurant;
        this.foodsList = new ArrayList<>();
    }


    public Integer getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Integer restaurant) {
        this.restaurant = restaurant;
    }


    @Override
    public String toString() {
        return null;
    }


    /**
     * Adds a food to the order.
     *
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
     *
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

}
