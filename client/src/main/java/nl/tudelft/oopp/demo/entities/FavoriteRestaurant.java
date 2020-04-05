package nl.tudelft.oopp.demo.entities;

public class FavoriteRestaurant {

    private Integer id;
    private Restaurant restaurant;
    private User user;

    /**
     * Makes a new FavoriteRestaurant object.
     * @param id the id of the favorite pair in the database
     * @param restaurant the restaurant
     * @param user the user
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
