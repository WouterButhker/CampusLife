package nl.tudelft.oopp.demo.entities;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Weekdays {

    private List<String> openingHours;

    public static final String CLOSED = "Closed";

    /**
     * The empty constructor for creating a new empty Weekdays Object.
     */
    public Weekdays() {
        this.openingHours = new ArrayList<>(7);

        for (int i = 0; i < 7; i++) {
            openingHours.add(CLOSED);
        }
    }

    /**
     * The constructor used for creating a Weekdays Object with existing opening hours.
     * Will always return a List with size 7, even if the parameter is wrong.
     * In case of a parameter that is too long, it will cut off the end.
     * In case of a parameter that is too short, it will fill the List up with CLOSED.
     * @param openingHoursUnparsed The String containing the opening hours
     *                             that are separated by a comma.
     */
    public Weekdays(String openingHoursUnparsed) {
        try {
            openingHours = Arrays.asList(openingHoursUnparsed.split(", ")).subList(0, 7);
        } catch (IndexOutOfBoundsException e) {
            openingHours = new ArrayList<>();
            String[] strings = openingHoursUnparsed.split(", ");
            openingHours.addAll(Arrays.asList(strings));
            for (int i = strings.length; i < 7; i++) {
                openingHours.add(CLOSED);
            }
        }

    }

    public List<String> getWeekdays() {
        return openingHours;
    }

    /**
     * A method that checks if all of the opening hours in the week have correct opening hours.
     * @return A boolean depending on the correctness of the opening hours.
     */
    public boolean checkCorrectness() {
        for (int i = 0; i < 7; i++) {
            try {
                String[] strings = openingHours.get(i).split("-");
                if (strings.length > 2) {
                    return false;
                }
                String left = strings[0];
                String right = strings[1];
                if (left.compareTo(right) >= 0) {
                    return false;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                if (!openingHours.get(i).equals(CLOSED)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Method that sets the selected day to Closed.
     * @param id The day that has to be set to Closed.
     */
    public void setClosed(int id) {
        openingHours.set(id, CLOSED);
    }

    @Override
    public String toString() {
        String res = openingHours.toString().replace("[", "");
        res = res.replace("]", "");
        return res;
    }


}
