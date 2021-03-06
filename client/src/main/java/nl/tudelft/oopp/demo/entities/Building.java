package nl.tudelft.oopp.demo.entities;

public class Building {

    private Integer buildingCode;
    private String name;
    private String location;
    private String openingHours;
    private Integer bikes;

    /**
     * Creates a new Building object.
     * @param buildingCode the number of the Building
     * @param name a String with the full name of the Building
     * @param location a String with the street
     * @param openingHours a String with format hh:mm-hh:mm for
     *                     every day of the week separated by a ","
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

    public Integer getCode() {
        return buildingCode;
    }

    public void setCode(Integer buildingCode) {
        this.buildingCode = buildingCode;
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

    public String toString() {
        return "{" + buildingCode + ", " + name + ", "
                + location + ", " + openingHours + ", " + bikes + "}";
    }

    public String getNameAndCode() {
        return this.buildingCode + " " + this.name;
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
}
