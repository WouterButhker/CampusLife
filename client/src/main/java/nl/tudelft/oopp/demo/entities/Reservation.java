package nl.tudelft.oopp.demo.entities;

public abstract class Reservation {

    protected Integer id;

    protected Integer userId; //Change to User object

    protected String date;

    protected String timeSlot;

    /**
     * parent constructor to create a new reservation.
     * @param userID the user that made the reservation
     * @param date the date of the reservation
     * @param timeSlot the time of the reservation
     */
    public Reservation(Integer id, int userID, String date, String timeSlot) {
        this.id = id;
        this.userId = userID;
        this.date = date;
        this.timeSlot = timeSlot;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
