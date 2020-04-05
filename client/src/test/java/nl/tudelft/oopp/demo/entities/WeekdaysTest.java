package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WeekdaysTest {

    private List<String> openingHours;
    public static final String CLOSED = "Closed";
    private Weekdays weekdays;

    @BeforeEach
    void setUpper() {
        this.openingHours = new ArrayList<>(7);
        for (int i = 0; i < 7; i++) {
            openingHours.add(CLOSED);
        }
        weekdays = new Weekdays();
    }

    @Test
    void constructorTest() {
        assertNotNull(weekdays);
    }

    @Test
    void constructorTest2() {
        Weekdays testDays = new Weekdays("09:00-17:00, 09:00-17:00, 09:00-17:00"
                + ", 09:00-17:00, 09:00-17:00, 10:00-16:00, 10:00-16:00");
        assertNotNull(testDays);
    }

    @Test
    void constructorTest3() {
        Weekdays testDays = new Weekdays("09:00-17:00, 09:00-17:00, 09:00-17:00"
                + ", 09:00-17:00, 09:00-17:00");
        assertNotNull(testDays);
    }

    @Test
    void getWeekdaysTest() {
        assertEquals(openingHours, weekdays.getWeekdays());
    }

    @Test
    void checkCorrectnessTestTrue() {
        Weekdays testDays = new Weekdays("09:00-17:00, 09:00-17:00, 09:00-17:00"
                + ", 09:00-17:00, 09:00-17:00, 10:00-16:00, 10:00-16:00");
        assertTrue(testDays.checkCorrectness());
    }

    @Test
    void checkCorrectnessTestFalse() {
        Weekdays testDays = new Weekdays("9:00-17:00, 9:00-17:00, 9:00-17:00"
                + ", 9:00-17:00, 9:00-17:00, 10:00-16:00, 10:00-16:00");
        assertFalse(testDays.checkCorrectness());
    }

    @Test
    void checkCorrectnessTestFalse2() {
        Weekdays testDays = new Weekdays("12:00-15:00-19:00, 9:00-17:00, 9:00-17:00"
                + ", 9:00-17:00, 9:00-17:00, 10:00-16:00, 10:00-16:00");
        assertFalse(testDays.checkCorrectness());
    }

    @Test
    void checkCorrectnessTestFalse3() {
        Weekdays testDays = new Weekdays("12:00, 9:00-17:00, 9:00-17:00"
                + ", 9:00-17:00, 9:00-17:00, 10:00-16:00, 10:00-16:00");
        assertFalse(testDays.checkCorrectness());
    }

    @Test
    void isOpenAtTest() {
        assertFalse(weekdays.isOpenAt(3, 13, 15));

        Weekdays testDays = new Weekdays("09:00-17:00, 09:00-17:00, 09:00-17:00"
                + ", 09:00-17:00, 09:00-17:00, 10:00-16:00, 10:00-16:00");
        assertTrue(testDays.isOpenAt(4, 10, 30));
    }

    @Test
    void setClosedTest() {
        Weekdays testDays = new Weekdays("09:00-17:00, 09:00-17:00, 09:00-17:00"
                + ", 09:00-17:00, 09:00-17:00, 10:00-16:00, 10:00-16:00");
        testDays.setClosed(0);
        testDays.setClosed(6);
        assertEquals(List.of("Closed", "09:00-17:00", "09:00-17:00", "09:00-17:00", "09:00-17:00",
                "10:00-16:00", "Closed"), testDays.getWeekdays());
    }

    @Test
    void toStringTest() {
        Weekdays testDays = new Weekdays("09:00-17:00, 09:00-17:00, 09:00-17:00"
                + ", 09:00-17:00, 09:00-17:00, Closed, Closed");
        assertEquals("09:00-17:00, 09:00-17:00, 09:00-17:00, 09:00-17:00, 09:00-17:00,"
                + " Closed, Closed", testDays.toString());
    }
}