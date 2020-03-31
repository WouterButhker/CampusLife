package nl.tudelft.oopp.demo.entities;

import java.util.List;
import java.util.Objects;

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
    }

    /**
     * Creates a new FoodOrder object for pickup
     * @param user the userID
     * @param date the date of the foodorder
     * @param timeSlot the prefered time of delivery/pickup
     * @param restaurant the restaurant the order was placed at
     */
    public FoodOrder(User user, String date, String timeSlot,
                     Restaurant restaurant) {
        super(user, date, timeSlot);
        this.restaurant = restaurant;

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

    public List<List<Integer>> getFoodsList() {
        return foodsList;
    }


}
