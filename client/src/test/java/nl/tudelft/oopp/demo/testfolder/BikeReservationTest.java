package nl.tudelft.oopp.demo.testfolder;

import nl.tudelft.oopp.demo.communication.AuthenticationCommunication;
import nl.tudelft.oopp.demo.entities.BikeReservation;
import nl.tudelft.oopp.demo.entities.Building;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BikeReservationTest {

    private int id;
    private int user;
    private Building pickUpBuilding;
    private Building dropOffBuilding;
    private String date;
    private String timeSlot;

    private BikeReservation bikeReservation;

    @BeforeEach
    void login() {
        AuthenticationCommunication.login("admin", "admin");
    }

    @BeforeEach
    void init() {
        id = 2;
        user = 5;
        pickUpBuilding = new Building(1, "Test", "Test street",
                "06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00, 19:00-21:00",
                "image", 45);
        dropOffBuilding = new Building(2, "Test2", "Test street2",
                "06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00, 19:00-21:00",
                "image", 32);
        date = "22/03/2020";
        timeSlot = "14:00-19:00";
        bikeReservation = new BikeReservation(id, user, pickUpBuilding, dropOffBuilding, date, timeSlot);
    }

    @Test
    void constructorTest() {
        assertNotNull(bikeReservation);
    }

    @Test
    void getIdTest() {
        assertEquals(2, bikeReservation.getId());
    }

    @Test
    void setIdTest() {
        bikeReservation.setId(3);
        assertEquals(3, bikeReservation.getId());
    }

    @Test
    void getUserTest() {
        assertEquals(5, bikeReservation.getUser());
    }

    @Test
    void setUserTest() {
        bikeReservation.setUser(6);
        assertEquals(6, bikeReservation.getUser());
    }

    @Test
    void getPickUpBuildingTest() {
        Building test = new Building(1, "Test", "Test street",
                "06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00, 19:00-21:00",
                "image", 45);
        assertEquals(test, bikeReservation.getPickUpBuilding());
    }

    @Test
    void setPickUpBuildingTest() {
        Building test = new Building(3, "Changed", "Test street",
                "06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00, 19:00-21:00",
                "image", 45);
        bikeReservation.setPickUpBuilding(test);
        assertEquals(test, bikeReservation.getPickUpBuilding());
    }

    @Test
    void getDropOffBuildingTest() {
        Building test = new Building(2, "Test2", "Test street2",
                "06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00, 19:00-21:00",
                "image", 32);
        assertEquals(test, bikeReservation.getDropOffBuilding());
    }

    @Test
    void setDropOffBuildingTest() {
        Building test = new Building(4, "Test2Changed", "Test street2",
                "06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00, 19:00-21:00",
                "image", 32);
        bikeReservation.setDropOffBuilding(test);
        assertEquals(test, bikeReservation.getDropOffBuilding());
    }

    @Test
    void getDateTest() {
        assertEquals("22/03/2020", bikeReservation.getDate());
    }

    @Test
    void setDateTest() {
        String newDate = "23/03/2020";
        bikeReservation.setDate(newDate);
        assertEquals(newDate, bikeReservation.getDate());
    }

    @Test
    void getTimeSlotTest() {
        assertEquals("14:00-19:00", bikeReservation.getTimeSlot());
    }

    @Test
    void setTimeSlotTest() {
        String newSlot = "12:00-15:00";
        bikeReservation.setTimeSlot(newSlot);
        assertEquals(newSlot, bikeReservation.getTimeSlot());
    }

    @Test
    void equalsSameObjectTest() {
        assertEquals(bikeReservation, bikeReservation);
    }

    @Test
    void equalsDifferentObjectsSameBikeReservationTest() {
        BikeReservation test = new BikeReservation(id, user, pickUpBuilding, dropOffBuilding, date, timeSlot);
        assertEquals(test, bikeReservation);
    }

    @Test
    void notEqualsTest() {
        BikeReservation test = new BikeReservation(6, 7, pickUpBuilding, dropOffBuilding, date, timeSlot);
        assertNotEquals(test, bikeReservation);
    }

    @Test
    void nullEqualsTest() {
        assertNotEquals(null, bikeReservation);
    }
}
