package nl.tudelft.oopp.demo.widgets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import nl.tudelft.oopp.demo.entities.BikeReservation;
import nl.tudelft.oopp.demo.entities.Building;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BikeReservationWidgetTest {

    private BikeReservationWidgetLogic bikeReservationWidgetLogic;
    private Building building;
    private Calendar timeSelected = Calendar.getInstance();

    @BeforeEach
    void init() {
        bikeReservationWidgetLogic = new BikeReservationWidgetLogic();
        building = new Building(3, "Test", "Test road",
                "12:00-24:00, 12:00-24:00, 12:00-24:00, 12:00-24:00, "
                        + "12:00-24:00, 12:00-24:00, 12:00-24:00", 15);
        //MONDAY
        timeSelected.set(Calendar.DAY_OF_WEEK, 1);
        //Date = 23/03/2020 12:00
        timeSelected.set(2020, Calendar.MARCH, 23, 12, 0);
    }

    @Test
    void computeAvailabilitiesOpenTest() {
        int[] test = bikeReservationWidgetLogic.computeAvailabilities(building, timeSelected);
        assertEquals(12, test[0]);
        assertEquals(24, test[1]);
    }

    @Test
    void computeAvailabilitiesClosedTest() {
        building.setOpeningHours("Closed, Closed, Closed, Closed, Closed, Closed, Closed");
        int[] test = bikeReservationWidgetLogic.computeAvailabilities(building, timeSelected);
        assertNull(test);
    }

    @Test
    void computeAvailabilitiesNoBuildingSelectedTest() {
        int[] test = bikeReservationWidgetLogic.computeAvailabilities(null, timeSelected);
        assertNull(test);
    }

    @Test
    void calculateNumBikesTest() {
        List<BikeReservation> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            BikeReservation bikeReservation = new BikeReservation(1, 1, building, building,
                    "23/03/2020", "12:00-14:00");
            list.add(bikeReservation);
        }
        int bikes = building.getBikes();
        bikes = bikeReservationWidgetLogic.calculateNumBikes(list, bikes, timeSelected, building);
        assertEquals(11, bikes);
    }

    @Test
    void calculateNumBikesNoReservationsTest() {
        int bikes = 15;
        bikes = bikeReservationWidgetLogic.calculateNumBikes(null, bikes, timeSelected, building);
        assertEquals(15, bikes);
    }
}
