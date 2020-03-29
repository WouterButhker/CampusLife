package nl.tudelft.oopp.demo.entities;

import java.util.Objects;

public class Restaurant {

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
    }
}
