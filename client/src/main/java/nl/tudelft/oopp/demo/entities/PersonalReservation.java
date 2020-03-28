package nl.tudelft.oopp.demo.entities;

import java.util.Objects;

public class PersonalReservation extends Reservation {
    private Integer id;
    private String date;
    private String timeSlot;
    private Integer user;
    private String activity;
    /**
     * Make a PersonalReservation object.
     *
     * @param id       the number of the reservation
     * @param user     the User(id) that made the reservation
     * @param timeSlot the time at which the reservation is made
     * @param activity the activity of the reservation
     */
    public PersonalReservation(Integer id, Integer user, String activity, String date, String timeSlot) {
        super(id, date, timeSlot);

        this.user = user;
        this.activity = activity;
    }

    @Override
    public String toString() {
        return "PersonalReservation{" + "id=" + id + ", user=" + user
                + ", activity=" + activity
                + ", date='" + date + '\'' + ", timeSlot='"
                + timeSlot + '\'' + '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }


    public String getActivity() { return activity; }

    public void setActivity(String activity) {this.activity = activity; }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonalReservation)) {
            return false;
        }
        PersonalReservation that = (PersonalReservation) o;
        return id==that.id
                && user.equals(that.user)
                && activity.equals(that.activity)
                && timeSlot.equals(that.timeSlot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, timeSlot, activity);
    }
}
