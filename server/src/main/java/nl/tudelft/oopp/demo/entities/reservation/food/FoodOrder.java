package nl.tudelft.oopp.demo.entities.reservation.food;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.entities.reservation.Reservation;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "food_order")
public class FoodOrder extends Reservation {

    @ManyToOne
    @JoinColumn(name = "restaurant")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "foodOrder")
    @OnDelete(action = OnDeleteAction.CASCADE)
    Set<FoodOrderQuantity> quantities;

    @ManyToOne
    @JoinColumn(name = "room_code")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Room room;        // room code

    @JsonInclude
    @Transient
    private List<List<Integer>> foodsList;

    public FoodOrder() {

    }

    /**
     * Creates a new FoodOrder object for pickup.
     * @param userId the userID
     * @param date the date of the foodorder
     * @param timeSlot the prefered time of delivery/pickup
     * @param restaurant the restaurant the order was placed at
     */
    public FoodOrder(User user, String date, String timeSlot, Restaurant restaurant) {
        super(user, date, timeSlot);
        this.restaurant = restaurant;
    }

    /**
     * Creates a new FoodOrder object for delivery.
     * @param user the userID
     * @param date the date of the foodorder
     * @param timeSlot the prefered time of delivery/pickup
     * @param restaurant the restaurant the order was placed at
     * @param room the room to deliver the food to
     */
    public FoodOrder(User user, String date, String timeSlot,
                     Restaurant restaurant, Room room) {
        super(user, date, timeSlot);
        this.restaurant = restaurant;
        this.room = room;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Restaurant getRestaurant() {
        return this.restaurant;
    }


    @Override
    public String toString() {
        return "Id: " + this.getId() + " user: " + getUser()
                + " date: " + this.getDate() + " time: "
                + this.getTimeSlot() + " restaurant: " + this.restaurant;
    }

    public List<List<Integer>> getFoodsList() {
        return foodsList;
    }


}
