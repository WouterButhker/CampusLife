package nl.tudelft.oopp.demo.entities;

import java.util.Objects;

public class Restaurant {
<<<<<<< HEAD
    private String name;
    private Integer buildingCode;
    private String openingHours;

    /**
     * Creates a new Restaurant object
     *
     * @param name         String with the name of the Restaurant
     * @param buildingCode the code of the building the Restaurant is in
     * @param openingHours a String with format hh:mm-hh:mm
     */
    public Restaurant(String name,
                      Integer buildingCode,
                      String openingHours) {
        this.name = name;
        this.buildingCode = buildingCode;
        this.openingHours = openingHours;
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

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
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
        return name.equals(restaurant.name)
                && buildingCode == restaurant.buildingCode
                && openingHours.equals(restaurant.openingHours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, buildingCode, openingHours);
=======

    private Integer id;
    private Integer building;
    private String name;
    private String description;

    /**
     * Creates a new Restaurant object.
     * @param id the id of the restaurant
     * @param building the building id where the restaurant is located
     * @param name then name of the restaurant
     * @param description then description of the restaurant
     */
    public Restaurant(Integer id,
                      Integer building,
                      String name,
                      String description) {
        this.id = id;
        this.building = building;
        this.name = name;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBuilding() {
        return building;
    }

    public void setBuilding(Integer building) {
        this.building = building;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
>>>>>>> fb522ae5880d396bc477bb07e24d74b0f1df07e5
    }
}
