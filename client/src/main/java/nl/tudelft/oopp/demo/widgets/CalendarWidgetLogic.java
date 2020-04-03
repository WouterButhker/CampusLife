package nl.tudelft.oopp.demo.widgets;

import java.util.Calendar;

public class CalendarWidgetLogic {

    /**
     * Compares the day, month and year of 2 java calendars.
     * @param a Calendar a
     * @param b Calendar b
     * @return True if a and b have the same day, month and year
     */
    public boolean haveSameDate(Calendar a, Calendar b) {
        boolean sameDay = a.get(Calendar.DAY_OF_MONTH) == b.get(Calendar.DAY_OF_MONTH);
        boolean sameMonth = a.get(Calendar.MONTH) == b.get(Calendar.MONTH);
        boolean sameYear = a.get(Calendar.YEAR) == b.get(Calendar.YEAR);
        return sameDay && sameMonth && sameYear;
    }

    /**
     * Removes the time part of a java Calendar.
     * @param a the calendar for which the time part should be removed
     */
    public void removeTime(Calendar a) {
        a.set(Calendar.HOUR_OF_DAY, 0);
        a.set(Calendar.MINUTE, 0);
        a.set(Calendar.SECOND, 0);
        a.set(Calendar.MILLISECOND, 0);
    }

    /**
     * Returns the day of week with monday being 0.
     * @param a the calendar for which the day of week should be retrieved
     * @return the day of week starting with monday as 0
     */
    public int getDayOfWeek(Calendar a) {
        int dayOfWeek = a.get(Calendar.DAY_OF_WEEK) - 2;
        if (dayOfWeek == -1) {
            dayOfWeek = 6;
        }
        return dayOfWeek;
    }

    /**
     * Returns the difference in days between 2 calendars.
     * @param a Calendar a
     * @param b Calendar b
     * @return the difference in days like a - b
     */
    public long getDayDiff(Calendar a, Calendar b) {
        return (a.getTimeInMillis() - b.getTimeInMillis())
                / (24 * 60 * 60 * 1000);
    }

    /**
     * Calculates if Calendar a is at most futureDays from the present.
     * @param a compared date
     * @param futureDays maximum future range (exclusive)
     * @return True if a is at most futureDays from the present
     */
    public boolean isDayWithinFuture(Calendar a, int futureDays) {
        removeTime(a);
        Calendar present = Calendar.getInstance();
        removeTime(present);
        long dayDiff = getDayDiff(a, present);
        return 0 <= dayDiff && dayDiff < futureDays;
    }
}
