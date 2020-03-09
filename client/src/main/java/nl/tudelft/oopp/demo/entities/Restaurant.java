package nl.tudelft.oopp.demo.entities;

import java.util.Objects;

public class Restaurant {
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
    }
}
