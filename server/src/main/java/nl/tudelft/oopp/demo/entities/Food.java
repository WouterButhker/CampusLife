package nl.tudelft.oopp.demo.entities;

import javax.persistence.*;

@Entity
@Table(name = "food")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "restaurant")
    private Integer restaurant;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    public Food() {

    }

    /**
     * Constructor for a food.
     * @param id the id of the food
     * @param restaurant the id of the restaurant where it is sold
     * @param name the name
     * @param price the price of a single unit of this food
     */
    public Food(Integer id,
                Integer restaurant,
                String name,
                Double price) {
        this.id = id;
        this.restaurant = restaurant;
        this.name = name;
        this.price = price;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
