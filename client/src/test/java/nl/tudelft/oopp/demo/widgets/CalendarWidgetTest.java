package nl.tudelft.oopp.demo.widgets;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import nl.tudelft.oopp.demo.widgets.CalendarWidgetLogic;
import org.junit.jupiter.api.Test;

public class CalendarWidgetTest {

    @Test
    public void testHaveSameDateWithSameDayAndMonthAndYear() {
        Calendar a = Calendar.getInstance();
        Calendar b = Calendar.getInstance();

        a.set(Calendar.YEAR, 2000);
        b.set(Calendar.YEAR, 2000);

        a.set(Calendar.MONTH, 11);
        b.set(Calendar.MONTH, 11);

        a.set(Calendar.DAY_OF_MONTH, 16);
        b.set(Calendar.DAY_OF_MONTH, 16);

        CalendarWidgetLogic logic = new CalendarWidgetLogic();
        assertTrue(logic.haveSameDate(a, b));
    }

    @Test
    public void testHaveSameMonthWithSameDayAndMonthAndDifferentYear() {
        Calendar a = Calendar.getInstance();
        Calendar b = Calendar.getInstance();

        a.set(Calendar.YEAR, 2000);
        b.set(Calendar.YEAR, 2001);

        a.set(Calendar.MONTH, 11);
        b.set(Calendar.MONTH, 11);

        a.set(Calendar.DAY_OF_MONTH, 16);
        b.set(Calendar.DAY_OF_MONTH, 16);

        CalendarWidgetLogic logic = new CalendarWidgetLogic();
        assertFalse(logic.haveSameDate(a, b));
    }

    @Test
    public void testHaveSameMonthWithDifferentMonthAndSameDayAndYear() {
        Calendar a = Calendar.getInstance();
        Calendar b = Calendar.getInstance();

        a.set(Calendar.YEAR, 2000);
        b.set(Calendar.YEAR, 2000);

        a.set(Calendar.MONTH, 10);
        b.set(Calendar.MONTH, 11);

        a.set(Calendar.DAY_OF_MONTH, 16);
        b.set(Calendar.DAY_OF_MONTH, 16);

        CalendarWidgetLogic logic = new CalendarWidgetLogic();
        assertFalse(logic.haveSameDate(a, b));
    }

    @Test
    public void testRemoveTime() {
        Calendar a = Calendar.getInstance();
        CalendarWidgetLogic logic = new CalendarWidgetLogic();
        logic.removeTime(a);

        assertEquals(0, a.get(Calendar.HOUR_OF_DAY));
        assertEquals(0, a.get(Calendar.MINUTE));
        assertEquals(0, a.get(Calendar.SECOND));
        assertEquals(0, a.get(Calendar.MILLISECOND));
    }

    @Test
    public void testRemoveTimeComparingDates() {
        Calendar a = Calendar.getInstance();
        Calendar b = Calendar.getInstance();

        a.set(Calendar.YEAR, 2000);
        b.set(Calendar.YEAR, 2000);

        a.set(Calendar.MONTH, 11);
        b.set(Calendar.MONTH, 11);

        a.set(Calendar.DAY_OF_MONTH, 16);
        b.set(Calendar.DAY_OF_MONTH, 16);

        CalendarWidgetLogic logic = new CalendarWidgetLogic();
        logic.removeTime(a);
        logic.removeTime(b);
        assertEquals(a.getTimeInMillis(), b.getTimeInMillis());
    }

    @Test
    public void testGetDayOfWeekOfMonday() {
        Calendar a = Calendar.getInstance();
        a.set(2020, Calendar.MARCH, 2);

        CalendarWidgetLogic logic = new CalendarWidgetLogic();
        assertEquals(0, logic.getDayOfWeek(a));
    }

    @Test
    public void testGetDayOfWeekOfSunday() {
        Calendar a = Calendar.getInstance();
        a.set(2020, Calendar.MARCH, 1);

        CalendarWidgetLogic logic = new CalendarWidgetLogic();
        assertEquals(6, logic.getDayOfWeek(a));
    }

    @Test
    public void testDayDiffWhenDatesAreEqual() {
        Calendar a = Calendar.getInstance();
        Calendar b = Calendar.getInstance();

        a.set(2020, Calendar.MARCH, 2);
        b.set(2020, Calendar.MARCH, 2);

        CalendarWidgetLogic logic = new CalendarWidgetLogic();
        logic.removeTime(a);
        logic.removeTime(b);
        assertEquals(0, logic.getDayDiff(a, b));
    }

    @Test
    public void testDayDiffWhenDatesAreOneAwayInFuture() {
        Calendar a = Calendar.getInstance();
        Calendar b = Calendar.getInstance();

        a.set(2020, Calendar.MARCH, 2);
        b.set(2020, Calendar.MARCH, 3);

        CalendarWidgetLogic logic = new CalendarWidgetLogic();
        logic.removeTime(a);
        logic.removeTime(b);
        assertEquals(1, logic.getDayDiff(b, a));
    }

    @Test
    public void testDayDiffWhenDatesAreOneAwayInPast() {
        Calendar a = Calendar.getInstance();
        Calendar b = Calendar.getInstance();

        a.set(2020, Calendar.MARCH, 2);
        b.set(2020, Calendar.MARCH, 1);

        CalendarWidgetLogic logic = new CalendarWidgetLogic();
        logic.removeTime(a);
        logic.removeTime(b);
        assertEquals(-1, logic.getDayDiff(b, a));
    }

    @Test
    public void testIsDayWithinFutureWhenDayIsSame() {
        Calendar a = Calendar.getInstance();

        CalendarWidgetLogic logic = new CalendarWidgetLogic();
        assertFalse(logic.isDayWithinFuture(a, 0));
        assertTrue(logic.isDayWithinFuture(a, 1));
    }

    @Test
    public void testIsDayWithinFutureWhenDayIsOneDayAhead() {
        Calendar a = Calendar.getInstance();
        a.add(Calendar.DAY_OF_MONTH, 1);

        CalendarWidgetLogic logic = new CalendarWidgetLogic();
        assertFalse(logic.isDayWithinFuture(a, 1));
        assertTrue(logic.isDayWithinFuture(a, 2));
    }
}
