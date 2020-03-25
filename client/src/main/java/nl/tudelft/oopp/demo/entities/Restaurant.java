package nl.tudelft.oopp.demo.entities;

import java.util.Objects;

public class Restaurant {

    private Integer id;
    private String name;
    private Integer buildingCode;
    private String description;

    /**
     * Creates a new Restaurant object.
     *
     * @param id the ID of the restaurant
     * @param name         String with the name of the Restaurant
     * @param buildingCode the code of the building the Restaurant is in
     * @param description a String containing the description of the restaurant
     */
    public Restaurant(Integer id, String name,
                      Integer buildingCode,
                      String description) {
        this.id = id;
        this.name = name;
        this.buildingCode = buildingCode;
        this.description = description;
    }

    public Integer getId() {
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
        return buildingCode;
    }

    public void setBuildingCode(Integer buildingCode) {
        this.buildingCode = buildingCode;
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
                && buildingCode.equals(restaurant.buildingCode)
                && description.equals(restaurant.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, buildingCode, description);
    }

}
