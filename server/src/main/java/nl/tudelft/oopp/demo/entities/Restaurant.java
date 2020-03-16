package nl.tudelft.oopp.demo.entities;


import java.util.Objects;
import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "restaurant")
public class Restaurant {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "building")
    private Integer buildingCode;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    public Restaurant() {

    }

    /**
     * Constructor for a restaurant.
     * @param id the id of the restaurant
     * @param buildingCode the id of the building where it is located
     * @param name the name
     * @param description a description of the restaurant
     */
    public Restaurant(Integer id,
                      Integer buildingCode,
                      String name,
                      String description) {
        this.id = id;
        this.buildingCode = buildingCode;
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
        return buildingCode;
    }

    public void setBuilding(Integer building) {
        this.buildingCode = building;
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
                && description.equals(restaurant.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, buildingCode, description);
    }
}
