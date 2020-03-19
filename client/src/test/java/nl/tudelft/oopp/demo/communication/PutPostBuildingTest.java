package nl.tudelft.oopp.demo.communication;

import static org.junit.jupiter.api.Assertions.*;

import nl.tudelft.oopp.demo.entities.Building;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PutPostBuildingTest {

    private Integer code;
    private String name;
    private String location;
    private String openingHours;
    private Integer bikes;
    private String image;
    private Building building;

    @BeforeEach
    void doBeforeEach() {
        AuthenticationCommunication.login("admin", "admin");
    }

    @BeforeEach
    void preparation() {
        code = 123;
        name = "The Arena";
        location = "CityStreetRoute";
        openingHours = "15:00-17:00, 01:00-19:00, 15:00-17:00, "
                + "15:00-17:00, 15:00-17:00, 15:00-17:00, 15:00-17:00";
        bikes = 5;
        image = "/images/main-screen-default-building.jpg";
        building = new Building(code, name, location, openingHours, bikes);
                //image,
    }

    @Test
    public void testSaveBuilding() {
        System.out.println("OUTPUT: " + BuildingCommunication.saveBuilding(building));
        assertNotNull(code);
    }

    @Test
    public void testUpdateBuilding() {
        System.out.println("OUTPUT: " + BuildingCommunication.updateBuilding(building));
        assertNotNull(code);
    }

}
