package nl.tudelft.oopp.demo.entities;

import java.util.Objects;
import javafx.scene.image.Image;

public class Building {

    private Integer code;
    private String name;
    private String location;
    private String openingHours;
    private String image;

    /**
     * Creates a new Building object.
     * @param code the number of the Building
     * @param name a String with the full name of the Building
     * @param location a String with the street
     * @param openingHours a String with format hh:mm-hh:mm
     * @param image a photo of the building
     */
    public Building(Integer code, String name, String location, String openingHours, String image) {
        this.code = code;
        this.name = name;
        this.location = location;
        this.openingHours = openingHours;
        this.image = image;
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

    public String toString() {
        return "{" + code + ", " + name + ", "
                + location + ", " + openingHours + ", " + "image" + "}";
    }

    public String getNameAndCode() {
        return this.name + " " + this.code;
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
                && image.equals(building.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, location, openingHours, image);
    }
}