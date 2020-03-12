package nl.tudelft.oopp.demo.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Weekdays {

    private List<String> openingHours;

    /**
     * The empty constructor for creating a new empty Weekdays Object.
     */
    public Weekdays() {
        this.openingHours = new ArrayList<>(7);

        for (int i = 0; i < 7; i++) {
            openingHours.add("Closed");
        }
    }

    /**
     * The constructor used for creating a Weekdays Object with existing opening hours.
     * @param openingHoursUnparsed The String containing the opening hours
     *                             that are separated by a comma.
     */
    public Weekdays(String openingHoursUnparsed) {
        openingHours = new ArrayList<>(7);

        for (int i = 0; i < 7; i++) {
            openingHours = Arrays.asList(openingHoursUnparsed.split(", "));
        }

    }

    public List<String> getWeekdays() {
        return openingHours;
    }

    public String getMonday() {
        return openingHours.get(0);
    }

    public void setMonday(String newOpeningHours) {
        openingHours.set(0, newOpeningHours);
    }

    public String getTuesday() {
        return openingHours.get(1);
    }

    public void setTuesday(String newOpeningHours) {
        openingHours.set(1, newOpeningHours);
    }

    public String getWednesday() {
        return openingHours.get(2);
    }

    public void setWednesday(String newOpeningHours) {
        openingHours.set(2, newOpeningHours);
    }

    public String getThursday() {
        return openingHours.get(3);
    }

    public void setThursday(String newOpeningHours) {
        openingHours.set(3, newOpeningHours);
    }

    public String getFriday() {
        return openingHours.get(4);
    }

    public void setFriday(String newOpeningHours) {
        openingHours.set(4, newOpeningHours);
    }

    public String getSaturday() {
        return openingHours.get(5);
    }

    public void setSaturday(String newOpeningHours) {
        openingHours.set(5, newOpeningHours);
    }

    public String getSunday() {
        return openingHours.get(6);
    }

    public void setSunday(String newOpeningHours) {
        openingHours.set(6, newOpeningHours);
    }


    /**
     * A method that checks if all of the opening hours in the week have correct opening hours.
     * @return A boolean depending on the correctness of the opening hours.
     */
    public boolean checkCorrectness() {
        for (int i = 0; i < 7; i++) {
            if (!openingHours.get(i).equals("Closed") && openingHours.get(i).split("-")[0]
                    .compareTo(openingHours.get(i).split("-")[1]) >= 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        String res = openingHours.toString().replace("[", "");
        res = res.replace("]", "");
        return res;
    }


}
