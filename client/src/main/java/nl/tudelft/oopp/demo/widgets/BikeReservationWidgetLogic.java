package nl.tudelft.oopp.demo.widgets;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Weekdays;
import nl.tudelft.oopp.demo.entities.reservation.BikeReservation;

public class BikeReservationWidgetLogic {

    /**
     * Method that calculates the start and end of the opening hours of a Building.
     * @param selected The selected Building from which the opening hours are taken
     * @param selectedDate The date for which the opening hours are taken
     * @return An array with 2 values, the opening time and the closing time
     *      or a null if the Building is closed.
     */
    public int[] computeAvailabilities(Building selected, Calendar selectedDate) {
        if (selected == null) {
            return null;
        }
        int day = selectedDate.get(Calendar.DAY_OF_WEEK);
        //some modulo shenanigans because weekDays[0] is monday but Calendar doesn't have the same
        day = day + 5;
        day = day % 7;
        Weekdays openingHoursWeek = new Weekdays(selected.getOpeningHours());
        String openingHoursDay = openingHoursWeek.getWeekdays().get(day);
        //if closed
        if (openingHoursDay.equals(Weekdays.CLOSED)) {
            return null;
        }
        String begin = openingHoursDay.split("-")[0];
        String end = openingHoursDay.split("-")[1];
        int beginTime = Integer.parseInt(begin.split(":")[0]);
        int endTime = Integer.parseInt(end.split(":")[0]);
        int[] res = new int[2];
        res[0] = beginTime;
        res[1] = endTime;
        return res;
    }

    /**
     * Method that calculates the amount of bikes reservable at a Building.
     * @param bikeReservations The List of BikeReservations which might have an impact on the
     *                         amount of available bikes
     * @param bikes The amount of bikes according to the database
     * @param timeSelected The time selected on which we are going to calculate the amount of bikes
     * @param building The building for which we are going to calculate the amount of bikes
     * @return An updated amount of bikes at that Building.
     */
    public int calculateNumBikes(List<BikeReservation> bikeReservations, int bikes,
                                 Calendar timeSelected, Building building) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy-HH:mm");
        if (bikeReservations == null) {
            return bikes;
        }
        for (int j = 0; j < bikeReservations.size(); j++) {
            BikeReservation bikeReservation = bikeReservations.get(j);
            String[] times = bikeReservation.getTimeSlot().split("-");
            String pickUp = times[0];
            String dropOff = times[1];
            Date pickUpDate = null;
            Date dropOffDate = null;
            try {
                pickUpDate = sdf.parse(bikeReservation.getDate() + "-" + pickUp);
                dropOffDate = sdf.parse(bikeReservation.getDate() + "-" + dropOff);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //the pickup time is before the selected time
            if (timeSelected.getTime().before(pickUpDate)) {
                //we don't do anything
            }
            //the bike isn't dropped off yet
            if (building.equals(bikeReservation.getDropOffBuilding())
                    && timeSelected.getTime().before(dropOffDate)) {
                bikes--;
            }
            if (bikes < 0) {
                bikes = 0;
            }
        }
        return bikes;
    }
}
