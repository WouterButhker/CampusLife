package nl.tudelft.oopp.demo.entities.reservation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import nl.tudelft.oopp.demo.entities.Food;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.entities.User;

public class FoodOrder extends Reservation {

    private Restaurant restaurant;
    private RoomReservation reservation;
    private List<List<Integer>> foodsList;

    /**
     * Creates a new FoodOrder object for delivery.
     * @param user the userID
     * @param date the date of the foodorder
     * @param timeSlot the prefered time of delivery/pickup
     * @param restaurant the restaurant the order was placed at
     * @param reservation the room to deliver the food to
     */
    public FoodOrder(User user, String date, String timeSlot,
                     Restaurant restaurant, RoomReservation reservation) {
        super(user, date, timeSlot);
        this.restaurant = restaurant;
        this.reservation = reservation;
        this.foodsList = new ArrayList<>();
    }

    /**
     * Creates a new FoodOrder object for pickup.
     * @param user the userID
     * @param date the date of the foodorder
     * @param timeSlot the prefered time of delivery/pickup
     * @param restaurant the restaurant the order was placed at
     */
    public FoodOrder(User user, String date, String timeSlot,
                     Restaurant restaurant) {
        super(user, date, timeSlot);
        this.restaurant = restaurant;
        this.foodsList = new ArrayList<>();
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

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public RoomReservation getRoom() {
        return reservation;
    }

    public void setRoom(RoomReservation room) {
        this.reservation = room;
    }

    public Restaurant getRestaurant() {
        return this.restaurant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FoodOrder)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        FoodOrder foodOrder = (FoodOrder) o;
        return Objects.equals(restaurant, foodOrder.restaurant)
                && Objects.equals(reservation, foodOrder.reservation)
                && Objects.equals(foodsList, foodOrder.foodsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), restaurant, reservation, foodsList);
    }

    @Override
    public String toString() {
        return "food order{" + super.toString()
                + ", restaurant: " + this.restaurant
                + ", delivery room: " + this.reservation
                + "}";
    }

    @Override
    public String toDisplayString() {
        String foodItems = "Fix this in FoodOrder entity";
        for (List<Integer> pair: this.foodsList) {
            // TODO
        }
        return "Food order | " + this.restaurant.getName()
                + " | Order: " + foodItems;
    }

    public List<List<Integer>> getFoodsList() {
        return foodsList;
    }


}
