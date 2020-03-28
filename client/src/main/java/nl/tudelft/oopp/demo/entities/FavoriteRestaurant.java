package nl.tudelft.oopp.demo.entities;

public class FavoriteRestaurant {

    private Integer id;
    private Integer restaurant;
    private Integer user;

    /**
     * Makes a new FavoriteRestaurant object.
     * @param id the id of the favorite pair in the database
     * @param restaurant the id of the restaurant
     * @param user the id of the user
     */
    public FavoriteRestaurant(Integer id, Integer restaurant, Integer user) {
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

    public Integer getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Integer restaurant) {
        this.restaurant = restaurant;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }
}
