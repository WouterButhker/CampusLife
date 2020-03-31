package nl.tudelft.oopp.demo.entities.reservation;

import java.util.Objects;
import nl.tudelft.oopp.demo.entities.User;

public abstract class Reservation {

    private int id;
    private User user;
    private String date;
    private String timeSlot;

    /**
     * parent constructor to create a new reservation.
     * @param user the user that made the reservation
     * @param date the date of the reservation
     * @param timeSlot the time of the reservation
     */
    public Reservation(User user, String date, String timeSlot) {
        this.user = user;
        this.date = date;
        this.timeSlot = timeSlot;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reservation)) {
            return false;
        }
        Reservation that = (Reservation) o;
        return id == that.id
                && Objects.equals(user, that.user)
                && Objects.equals(date, that.date)
                && Objects.equals(timeSlot, that.timeSlot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, date, timeSlot);
    }

    /**
     * Returns a programmer friendly representation of the object.
     * @return a programmer friendly string representation of the object
     */
    @Override
    public String toString() {
        return "user: " + this.user
                + ", date: " + this.date
                + ", timeslot: " + this.timeSlot;
    }

    /**
     * returns the date and timeslot in a string.
     * @return the date and timeslot
     */
    public String getDateAndTimeslot() {
        return "Date: " + this.date + " | Timeslot: "
                + this.timeSlot;
    }

    /**
     * Returns a user friendly representation of the object.
     * @return a user friendly string representation of the object
     */
    public abstract String toDisplayString();

    /**
     * Returns a admin friendly representation of the object.
     * @return a admin friendly string representation of the object
     */
    public String toDisplayStringAdmin() {
        if (user.getUsername() == null) {
            return toDisplayString() + " | user: " + user.getId();
        }
        return toDisplayString() + " | user: " + user.getId()
                + " (" + user.getUsername() + ")";
    }
}
