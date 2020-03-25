package nl.tudelft.oopp.demo.entities.reservation;

import javax.persistence.Column;
import javax.persistence.Entity;
import nl.tudelft.oopp.demo.entities.User;

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
    public String toString() {
        return null;
    }
}
