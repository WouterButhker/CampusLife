package nl.tudelft.oopp.demo.testfolder;

import static org.junit.jupiter.api.Assertions.*;

import nl.tudelft.oopp.demo.communication.AuthenticationCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.entities.reservation.BikeReservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BikeReservationTest {


    private User user;
    private Building pickUpBuilding;
    private Building dropOffBuilding;
    private String date;
    private String timeSlot;

    private BikeReservation bikeReservation;

//    @BeforeEach
//    void login() {
//        AuthenticationCommunication.login("admin", "admin");
//    }

    @BeforeEach
    void init() {
        user = new User(5);
        pickUpBuilding = new Building(1, "Test", "Test street", "06:00-18:00, 06:00-18:00,"
                + " 06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00, 19:00-21:00", 45);
        dropOffBuilding = new Building(2, "Test2", "Test street2", "06:00-18:00, 06:00-18:00, "
                + "06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00, 19:00-21:00", 32);
        date = "22/03/2020";
        timeSlot = "14:00-19:00";
        bikeReservation = new BikeReservation(user, pickUpBuilding, dropOffBuilding,
                date, timeSlot);
    }

    @Test
    void constructorTest() {
        assertNotNull(bikeReservation);
    }

    //    @Test
    //    void getIdTest() {
    //        Integer test = 2;
    //        assertEquals(test, bikeReservation.getId());
    //    }
    //
    //    @Test
    //    void setIdTest() {
    //        bikeReservation.setId(3);
    //        Integer test = 3;
    //        assertEquals(test, bikeReservation.getId());
    //    }
    //
    //    @Test
    //    void getUserTest() {
    //        Integer test = 5;
    //        assertEquals(test, bikeReservation.getUser());
    //    }
    //
    //    @Test
    //    void setUserTest() {
    //        bikeReservation.setUser(6);
    //        Integer test = 6;
    //        assertEquals(test, bikeReservation.getUser());
    //    }

    @Test
    void getPickUpBuildingTest() {
        Building test = new Building(1, "Test", "Test street",
                "06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00,"
                        + " 06:00-18:00, 06:00-18:00, 19:00-21:00", 45);
        assertEquals(test, bikeReservation.getPickUpBuilding());
    }

    @Test
    void setPickUpBuildingTest() {
        Building test = new Building(3, "Changed", "Test street",
                "06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00,"
                        + " 06:00-18:00, 19:00-21:00", 45);
        bikeReservation.setPickUpBuilding(test);
        assertEquals(test, bikeReservation.getPickUpBuilding());
    }

    @Test
    void getDropOffBuildingTest() {
        Building test = new Building(2, "Test2", "Test street2",
                "06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00,"
                        + " 06:00-18:00, 19:00-21:00", 32);
        assertEquals(test, bikeReservation.getDropOffBuilding());
    }

    @Test
    void setDropOffBuildingTest() {
        Building test = new Building(4, "Test2Changed", "Test street2",
                "06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00, "
                        + "06:00-18:00, 19:00-21:00", 32);
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
        BikeReservation test = new BikeReservation(user, pickUpBuilding,
                dropOffBuilding, date, timeSlot);
        assertEquals(test, bikeReservation);
    }

    @Test
    void notEqualsTest() {
        BikeReservation test = new BikeReservation(new User(6), pickUpBuilding,
                dropOffBuilding, date, timeSlot);
        assertNotEquals(test, bikeReservation);
    }

    @Test
    void nullEqualsTest() {
        assertNotEquals(null, bikeReservation);
    }

    @Test
    void toStringTest() {
        String out = "bike reservation{user: Id: 5 user: null pass: null role: Student,"
                + " date: 22/03/2020, timeslot: 14:00-19:00, "
                + "pickup building: {1, Test, Test street, 06:00-18:00, 06:00-18:00, "
                + "06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00, 19:00-21:00,"
                + " image, 45}, dropoff building: {2, Test2, Test street2, 06:00-18:00,"
                + " 06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00, "
                + "19:00-21:00, image, 32}}";
        assertEquals(bikeReservation.toString(), out);
    }

    @Test
    void toDisplayStringTest() {
        String out = "Bike reservation | Pickup location: Test"
                + " | Dropoff location: Test2 | Date: 22/03/2020 | Timeslot: 14:00-19:00";
        assertEquals(bikeReservation.toDisplayString(), out);
    }

    @Test
    void toDisplayStringAdminTest() {
        String out = "Bike reservation | Pickup location: Test | "
                + "Dropoff location: Test2 | Date: 22/03/2020 | Timeslot: 14:00-19:00 | user: 5";
        assertEquals(bikeReservation.toDisplayStringAdmin(), out);
    }

    @Test
    void toDisplayStringAdminWithUsernameTest() {
        String out = "Bike reservation | Pickup location: Test | "
                + "Dropoff location: Test2 | Date: 22/03/2020 | Timeslot: 14:00-19:00 | user: 5 "
                + "(Wouter)";
        bikeReservation.getUser().setUsername("Wouter");
        assertEquals(bikeReservation.toDisplayStringAdmin(), out);
    }
}
