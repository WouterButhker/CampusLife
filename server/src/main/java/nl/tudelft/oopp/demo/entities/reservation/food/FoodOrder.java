package nl.tudelft.oopp.demo.entities.reservation.food;

import com.fasterxml.jackson.annotation.JsonInclude;
import nl.tudelft.oopp.demo.entities.reservation.Reservation;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    public FoodOrder(Integer id,
                     Integer restaurant,
                     Integer user) {
        this.restaurant = restaurant;
    }



    @Override
    public String toString() {
        return null;
    }

    public List<List<Integer>> getFoodsList() {
        return foodsList;
    }
}
