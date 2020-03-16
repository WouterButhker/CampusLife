package nl.tudelft.oopp.demo.entities;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "building")
public class Building {

    @Id
    @Column(name = "buildingCode")
    private Integer buildingCode;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "openingHours")
    private String openingHours;

    @Column(name = "bikes")
    private Integer bikes;

    public Building() {

    }

    /**
     * Constructor for a building.
     * @param buildingCode the number of the building
     * @param name the actual name of the building
     * @param location the street
     * @param openingHours format aa:bb-cc:dd for every day of the week separated by a ","
     * @param bikes the amount of bikes at the building, null if building has no bike station
     */
    public Building(Integer buildingCode,
                    String name,
                    String location,
                    String openingHours,
                    Integer bikes) {
        this.buildingCode = buildingCode;
        this.name = name;
        this.location = location;
        this.openingHours = openingHours;
        this.bikes = bikes;
    }

    public Integer getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(Integer buildingCode) {
        this.buildingCode = buildingCode;
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

    public Integer getBikes() {
        return bikes;
    }

    public void setBikes(Integer bikes) {
        this.bikes = bikes;
    }

    /**
     * Makes a Building object into a String.
     * @return String representation of a Building
     */
    public String toString() {
        return "[\"buildingCode\":\"" + buildingCode + "\",\"name\":\"" + name
                + "\",\"location\":\"" + location + "\",\"openingHours\":\"" + openingHours
                + "\",\"bikes\":\"" + bikes + "\"]";
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
        return buildingCode.equals(building.buildingCode)
                && name.equals(building.name)
                && location.equals(building.location)
                && openingHours.equals(building.openingHours)
                && bikes.equals(building.bikes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buildingCode, name, location, openingHours, bikes);
    }
}
