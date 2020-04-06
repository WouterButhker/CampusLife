package nl.tudelft.oopp.demo.entities.food;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.entities.reservation.Reservation;
import nl.tudelft.oopp.demo.entities.reservation.RoomReservation;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "food_order")
public class FoodOrder extends Reservation {

    @ManyToOne
    @JoinColumn(name = "restaurant", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @OneToMany(mappedBy = "foodOrder")
    @OnDelete(action = OnDeleteAction.CASCADE)
    Set<FoodOrderQuantity> quantities;

    @ManyToOne
    @JoinColumn(name = "room_reservation")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RoomReservation reservation;        // room code

    @JsonInclude
    @Transient
    private List<List<Integer>> foodsList;

    public FoodOrder() {

    }

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
                && Objects.equals(quantities, foodOrder.quantities)
                && Objects.equals(reservation, foodOrder.reservation)
                && Objects.equals(foodsList, foodOrder.foodsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), restaurant, quantities, reservation, foodsList);
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

    public void setFoodsList(List<List<Integer>> foodsList) {
        this.foodsList = foodsList;
    }


}
