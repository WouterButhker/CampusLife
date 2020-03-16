package nl.tudelft.oopp.demo.entities;

<<<<<<< HEAD
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
=======
import javax.persistence.*;
>>>>>>> fb522ae5880d396bc477bb07e24d74b0f1df07e5

@Entity
@Table(name = "restaurant")
public class Restaurant {

    @Id
<<<<<<< HEAD
    @Column(name = "name")
    private String name;

    @Column(name = "buildingCode")
    private Integer buildingCode;

    @Column(name = "openingHours")
    private String openingHours;

    /*
    @Column(name = "menu")
    private String menu;
     */
=======
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "building")
    private Integer building;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
>>>>>>> fb522ae5880d396bc477bb07e24d74b0f1df07e5

    public Restaurant() {

    }

    /**
<<<<<<< HEAD
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

=======
     * Constructor for a restaurant.
     * @param id the id of the restaurant
     * @param building the id of the building where it is located
     * @param name the name
     * @param description a description of the restaurant
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
>>>>>>> fb522ae5880d396bc477bb07e24d74b0f1df07e5
}
