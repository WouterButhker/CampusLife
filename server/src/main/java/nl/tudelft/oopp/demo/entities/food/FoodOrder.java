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
                && Objects.equals(room, foodOrder.room)
                && Objects.equals(foodsList, foodOrder.foodsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), restaurant, quantities, room, foodsList);
    }

    @Override
    public String toString() {
        return "food order{" + super.toString()
                + ", restaurant: " + this.restaurant
                + ", delivery room: " + this.room
                + "}";
    }

    public List<List<Integer>> getFoodsList() {
        return foodsList;
    }


}
