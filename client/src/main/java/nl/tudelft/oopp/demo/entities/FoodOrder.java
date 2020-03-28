package nl.tudelft.oopp.demo.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FoodOrder extends Reservation {
    private int restaurant;
    private List<List<Integer>> foodsList;
    private int deliveryRoom;

    /**
     * Creates a new FoodOrder object for pickup.
     * @param userId the userID
     * @param date the date of the foodorder
     * @param timeSlot the prefered time of delivery/pickup
     * @param restaurant the restaurant the order was placed at
     */
    public FoodOrder(int userId, String date, String timeSlot, int restaurant) {
        super(userId, date, timeSlot);
        this.restaurant = restaurant;
        this.foodsList = new ArrayList<>();

    }

    /**
     * Creates a new FoodOrder object for delivery.
     * @param userId the userID
     * @param date the date of the foodorder
     * @param timeSlot the prefered time of delivery/pickup
     * @param restaurant the restaurant the order was placed at
     * @param deliveryRoom the room to deliver the food to
     */
    public FoodOrder(int userId, String date, String timeSlot,
                     int restaurant, int deliveryRoom) {
        super(userId, date, timeSlot);
        this.restaurant = restaurant;
        this.foodsList = new ArrayList<>();
        this.deliveryRoom = deliveryRoom;
    }


    public int getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(int restaurant) {
        this.restaurant = restaurant;
    }


    @Override
    public String toString() {
        return null;
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

}
