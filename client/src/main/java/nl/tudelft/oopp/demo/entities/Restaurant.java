package nl.tudelft.oopp.demo.entities;

import java.util.Objects;

public class Restaurant {

    private int id;
    private String name;
    private Integer building;
    private String description;

    /**
     * Creates a new Restaurant object.
     *
     * @param id the ID of the restaurant
     * @param name         String with the name of the Restaurant
     * @param building the code of the building the Restaurant is in
     * @param description a String with format hh:mm-hh:mm
     */
    public Restaurant(int id, String name,
                      Integer building,
                      String description) {
        this.id = id;
        this.name = name;
        this.building = building;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBuildingCode() {
        return building;
    }

    public void setBuildingCode(Integer building) {
        this.building = building;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Restaurant)) {
            return false;
        }
        Restaurant restaurant = (Restaurant) o;
        return id == restaurant.id
                && name.equals(restaurant.name)
                && building.equals(restaurant.building)
                && description.equals(restaurant.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, building, description);
    }

}
