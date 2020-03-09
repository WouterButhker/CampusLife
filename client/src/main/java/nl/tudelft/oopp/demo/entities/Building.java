package nl.tudelft.oopp.demo.entities;

import java.util.Objects;
import javafx.scene.image.Image;

public class Building {

    private Integer code;
    private String name;
    private String location;
    private String openingHours;
    private String image;
    private Integer bikes;

    /**
     * Creates a new Building object.
     * @param code the number of the Building
     * @param name a String with the full name of the Building
     * @param location a String with the street
     * @param openingHours a String with format hh:mm-hh:mm
     * @param image a photo of the building
     * @param bikes the amount of bikes at the building, null if building has no bike station
     */
    public Building(Integer code, String name, String location, String openingHours, String image, Integer bikes) {
        this.code = code;
        this.name = name;
        this.location = location;
        this.openingHours = openingHours;
        this.image = image;
        this.bikes = bikes;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getBikes() {
        return bikes;
    }

    public void setBikes(Integer bikes) {
        this.bikes = bikes;
    }

    public String toString() {
        return "{" + code + ", " + name + ", "
                + location + ", " + openingHours + ", " + "image" + "Bikes: " + bikes + "}";
    }

    public String getNameAndCode() {
        return this.code + " " + this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Building)) {
            return false;
        }
        Building building = (Building) o;
        return code.equals(building.code)
                && name.equals(building.name)
                && location.equals(building.location)
                && openingHours.equals(building.openingHours)
                && image.equals(building.image)
                && bikes.equals(building.bikes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, location, openingHours, image, bikes);
    }
}
