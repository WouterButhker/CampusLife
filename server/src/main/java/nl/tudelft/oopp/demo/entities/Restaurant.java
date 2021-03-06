package nl.tudelft.oopp.demo.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import javax.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "restaurant")
public class Restaurant {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "building")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Building building;

    @Column(name = "description")
    private String description;

    @Transient
    private Integer buildingCode;

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
                      String name,
                      Building building,
                      String description) {
        this.id = id;
        this.name = name;
        this.building = building;
        this.buildingCode = building.getBuildingCode();
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    //@JsonIgnore
    public Building getBuilding() {
        return building;
    }

    //@JsonIgnore
    public void setBuilding(Building building) {
        this.building = building;
        this.buildingCode = building.getBuildingCode();
    }

    /**
     * Getter for the building code.
     *
     * @return the building code
     */
    @JsonProperty(value = "buildingCode")
    public Integer getBuildingCode() {
        if (buildingCode == null && building != null) {
            return building.getBuildingCode();
        } else {
            return buildingCode;
        }
    }

    @JsonProperty(value = "buildingCode")
    public void setBuildingCode(Integer buildingCode) {
        this.buildingCode = buildingCode;
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

    public String toString() {
        return "{" + id + ", " + name + ", "
                + buildingCode + ", " + description + "}";
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
}
