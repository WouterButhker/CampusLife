package nl.tudelft.oopp.demo.entities.reservation;

import static org.junit.jupiter.api.Assertions.*;

import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BikeReservationTest {

    private User user;
    private Building pickUpBuilding;
    private Building dropOffBuilding;
    private String date;
    private String timeSlot;
    private Building testBuilding;
    private BikeReservation bikeReservation;

    @BeforeEach
    void setUpper() {
        user = new User(5);
        pickUpBuilding = new Building(1, "Test", "Test street", "06:00-18:00, 06:00-18:00,"
                + " 06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00, 19:00-21:00", 45);
        dropOffBuilding = new Building(2, "Test2", "Test street2", "06:00-18:00, 06:00-18:00, "
                + "06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00, 19:00-21:00", 32);
        date = "22/03/2020";
        timeSlot = "14:00-19:00";
        bikeReservation = new BikeReservation(user, pickUpBuilding, dropOffBuilding, date,
                timeSlot);
        testBuilding = new Building(3, "House", "Place", "08:00-13:00",2);
    }

    @Test
    void constructorTest() {
        assertNotNull(bikeReservation);
    }

    @Test
    void emptyConstructorTest() {
        assertNotNull(new BikeReservation());
    }

    @Test
    void getPickUpBuildingTest() {
        assertEquals(pickUpBuilding, bikeReservation.getPickUpBuilding());
    }

    @Test
    void setPickUpBuildingTest() {
        bikeReservation.setPickUpBuilding(testBuilding);
        assertEquals(testBuilding, bikeReservation.getPickUpBuilding());
    }

    @Test
    void getDropOffBuildingTest() {
        assertEquals(dropOffBuilding, bikeReservation.getDropOffBuilding());
    }

    @Test
    void setDropOffBuildingTest() {
        bikeReservation.setDropOffBuilding(testBuilding);
        assertEquals(testBuilding, bikeReservation.getDropOffBuilding());
    }

    @Test
    void equalsSameTest() {
        assertTrue(bikeReservation.equals(bikeReservation));
    }

    @Test
    void equalsNotInstanceofTest() {
        assertFalse(bikeReservation.equals(new Object()));
    }

    @Test
    void equalsNotSameSuperTest() {
        BikeReservation testBikeRes = new BikeReservation(new User(124),
                pickUpBuilding, testBuilding, "4-4-2020", timeSlot);
        assertFalse(bikeReservation.equals(testBikeRes));
    }

    @Test
    void equalsCopyTest() {
        BikeReservation copyBikeReservation = new BikeReservation(user, pickUpBuilding,
                dropOffBuilding, date, timeSlot);
        assertTrue(bikeReservation.equals(copyBikeReservation));
    }

    @Test
    void hashCodeTest() {
        assertTrue(Integer.class.isInstance(bikeReservation.hashCode()));
    }

    @Test
    void toStringTest() {
        assertEquals(
                "bike reservation{user: user{id: 5, name:null, role: Student}, date: 22/03/2020"
                + ", timeslot: 14:00-19:00, pickup building: building{buildingcode: 1, name: Test"
                + ", location: Test street, opening hours: 06:00-18:00, 06:00-18:00, 06:00-18:00,"
                + " 06:00-18:00, 06:00-18:00, 06:00-18:00, 19:00-21:00, bikes: 45}, dropoff"
                + " building: building{buildingcode: 2, name: Test2, location: Test street2,"
                + " opening hours: 06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00"
                + ", 06:00-18:00, 19:00-21:00, bikes: 32}}", bikeReservation.toString());
    }
}