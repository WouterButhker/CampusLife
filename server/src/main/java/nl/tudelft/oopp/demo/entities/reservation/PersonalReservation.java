package nl.tudelft.oopp.demo.entities.reservation;

import nl.tudelft.oopp.demo.entities.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class PersonalReservation extends Reservation {

    @Column(name = "activity")
    private String activity;

    public PersonalReservation() {

    }

    public PersonalReservation(User user, String date, String timeSlot, String activity) {
        super(user, date, timeSlot);
        this.activity = activity;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonalReservation)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        PersonalReservation that = (PersonalReservation) o;
        return Objects.equals(activity, that.activity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), activity);
    }

    @Override
    public String toString() {
        return "personal reservation{" + super.toString()
                + ", activity: " + this.activity
                + "}";
    }
}
