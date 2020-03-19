package nl.tudelft.oopp.demo.entities.reservation.food;

import com.fasterxml.jackson.annotation.JsonInclude;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.entities.reservation.Reservation;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "food_order")
public class FoodOrder extends Reservation {


    @Column(name = "restaurant")
    private Integer restaurant;

    @OneToMany(mappedBy = "foodOrder")
    @OnDelete(action = OnDeleteAction.CASCADE)
    Set<FoodOrderQuantity> quantities;


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
    public FoodOrder(User user, String date, String timeSlot, Integer restaurant) {
        super(user, date, timeSlot);
        this.restaurant = restaurant;
    }

    public FoodOrder(int userId, String date, String timeSlot, int restaurant) {
        super(new User(userId), date, timeSlot);
        this.restaurant = restaurant;
    }


    public int getRestaurant() {
        return this.restaurant;
    }


    @Override
    public String toString() {
        return "Id: " + this.getId() + " user: " + getUser() + " date: " + this.getDate() + " time: " + this.getTimeSlot() + " restaurant: " + this.restaurant;
    }

    public List<List<Integer>> getFoodsList() {
        return foodsList;
    }


}
