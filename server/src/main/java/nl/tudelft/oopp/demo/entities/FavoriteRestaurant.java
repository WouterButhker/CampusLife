package nl.tudelft.oopp.demo.entities;

import javax.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "favoriteRestaurant")
public class FavoriteRestaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "restaurant")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "user")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public FavoriteRestaurant() {

    }

    /**
     * Makes a new FavoriteRestaurant object.
     * @param id the id of the favorite pair in the database
     * @param restaurant the id of the restaurant
     * @param user the id of the user
     */
    public FavoriteRestaurant(Integer id, Restaurant restaurant, User user) {
        this.id = id;
        this.restaurant = restaurant;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
