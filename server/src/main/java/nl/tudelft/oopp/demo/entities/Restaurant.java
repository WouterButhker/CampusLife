package nl.tudelft.oopp.demo.entities;

import java.util.Objects;
import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "restaurant")
public class Restaurant {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "building")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Building building;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    public Restaurant() {

    }

    /**
     * Constructor for a restaurant.
     * @param id the id of the restaurant
     * @param building the id of the building where it is located
     * @param name the name
     * @param description a description of the restaurant
     */
    public Restaurant(Integer id,
                      Building building,
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

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
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
                && building.equals(restaurant.building)
                && description.equals(restaurant.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, building, description);
    }
}
