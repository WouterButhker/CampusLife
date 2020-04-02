package nl.tudelft.oopp.demo.entities;

import java.util.Objects;

public class Food {

    private Integer id;
    private String name;
    private Integer restaurant;
    private Double price;

    /**
     * Creates a new Food object.
     * @param id the code of the food item
     * @param name a String with the full name of the food item
     * @param restaurant the restaurant where it is sold
     * @param price the price at which it is sold
     */
    public Food(Integer id,
                String name,
                Integer restaurant,
                Double price) {
        this.id = id;
        this.name = name;
        this.restaurant = restaurant;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Integer restaurant) {
        this.restaurant = restaurant;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Food)) {
            return false;
        }
        Food food = (Food) o;
        return id.equals(food.id)
                && name.equals(food.name)
                && restaurant.equals(food.restaurant)
                && price.equals(food.price);
    }
}
