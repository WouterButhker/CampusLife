package nl.tudelft.oopp.demo.widgets;

import static org.junit.jupiter.api.Assertions.*;

import nl.tudelft.oopp.demo.entities.Weekdays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class WeekdaysTest {

    private Weekdays weekdays;

    @BeforeEach
    void init() {
        String openingHours = "12:00-24:00, 12:00-24:00, 12:00-24:00, 12:00-24:00,"
                + " 12:00-24:00, 12:00-24:00, 12:00-24:00";
        weekdays = new Weekdays(openingHours);
    }

    @Test
    void filledConstructorTest() {
        assertNotNull(weekdays);
    }

    @Test
    void halfConstructorTest() {
        Weekdays test = new Weekdays("12:00-24:00, 12:00-24:00, 12:00-24:00");
        assertEquals(Weekdays.CLOSED, test.getWeekdays().get(4));
    }

    @Test
    void emptyConstructorTest() {
        Weekdays test = new Weekdays();
        assertNotNull(test);
    }

    @Test
    void toStringTest() {
        String toString = "12:00-24:00, 12:00-24:00, 12:00-24:00, "
                + "12:00-24:00, 12:00-24:00, 12:00-24:00, 12:00-24:00";
        assertEquals(toString, weekdays.toString());
    }

    @Test
    void setClosedTest() {
        weekdays.setClosed(2);
        String closed = weekdays.getWeekdays().get(2);
        assertEquals(Weekdays.CLOSED, closed);
    }

    @Test
    void checkCorrectnessFalseWrongOpeningHoursTest() {
        weekdays.getWeekdays().set(2, "15:00-14:00");
        assertFalse(weekdays.checkCorrectness());
    }

    @Test
    void checkCorrectnessFalseSameOpeningHoursTest() {
        weekdays.getWeekdays().set(2, "15:00-15:00");
        assertFalse(weekdays.checkCorrectness());
    }

    @Test
    void checkCorrectnessFalseWrongStringTest() {
        weekdays.getWeekdays().set(2, "Clesod"); //Typo on purpose
        assertFalse(weekdays.checkCorrectness());
    }

    @Test
    void checkCorrectnessFalseTooManyOpeningHoursTest() {
        weekdays.getWeekdays().set(2, "15:00-16:00-17:00");
        assertFalse(weekdays.checkCorrectness());
    }

    @Test
    void checkCorrectnessTrueTest() {
        assertTrue(weekdays.checkCorrectness());
    }

    @Test
    void checkCorrectnessClosedTest() {
        Weekdays test = new Weekdays();
        assertTrue(test.checkCorrectness());
    }

    @Test
    void lengthLongTest() {
        Weekdays test = new Weekdays("12:00-24:00, 12:00-24:00, 12:00-24:00, 12:00-24:00, "
                + "12:00-24:00, 12:00-24:00, 12:00-24:00, 12:00-24:00, 12:00-24:00, 12:00-24:00,"
                + " 12:00-24:00, 12:00-24:00, 12:00-24:00, 12:00-24:00");
        assertEquals(7, test.getWeekdays().size());
    }

    @Test
    void lengthShortTest() {
        Weekdays test = new Weekdays("12:00-24:00, 12:00-24:00, 12:00-24:00, 12:00-24:00,"
                + " 12:00-24:00, 12:00-24:00");
        assertEquals(7, test.getWeekdays().size());
    }
}
