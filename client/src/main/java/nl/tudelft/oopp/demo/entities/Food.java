package nl.tudelft.oopp.demo.entities;

import java.util.Objects;

public class Food {

    private Integer code;
    private String name;
    private Integer restaurant;
    private Double price;

    /**
     * Creates a new Food object.
     * @param code the code of the food item
     * @param name a String with the full name of the food item
     * @param restaurant the restaurant where it is sold
     * @param price the price at which it is sold
     */
    public Food(Integer code,
                String name,
                Integer restaurant,
                Double price) {
        this.code = code;
        this.name = name;
        this.restaurant = restaurant;
        this.price = price;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
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
        return code.equals(food.code)
                && name.equals(food.name)
                && restaurant.equals(food.restaurant)
                && price.equals(food.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, restaurant, price);
    }
}
