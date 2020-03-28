package nl.tudelft.oopp.demo.entities;

public abstract class Reservation {

    private int id;

    private String date;

    private String timeSlot;

    /// TODO ADD JAVADOC COMMENT PLEASE.
    /**
     * ADD JAVADOC COMMENT PLEASE.
     * @param userID ADD JAVADOC COMMENT PLEASE.
     * @param date ADD JAVADOC COMMENT PLEASE.
     * @param timeSlot ADD JAVADOC COMMENT PLEASE.
     */
    public Reservation(int userID, String date, String timeSlot) {
        this.id = userID;
        this.date = date;
        this.timeSlot = timeSlot;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public abstract String toString();
}
