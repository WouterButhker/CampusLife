package nl.tudelft.oopp.demo.entities.reservation;

import javax.persistence.*;

import nl.tudelft.oopp.demo.entities.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Objects;


@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "users")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "date")
    private String date;

    @Column(name = "time_slot")
    private String timeSlot;

    public Reservation() {

    }

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
        if (this == o) return true;
        if (!(o instanceof Reservation)) return false;
        Reservation that = (Reservation) o;
        return id == that.id &&
                Objects.equals(user, that.user) &&
                Objects.equals(date, that.date) &&
                Objects.equals(timeSlot, that.timeSlot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, date, timeSlot);
    }

    @Override
    public String toString() {
        return "user: " + this.user
                + ", date: " + this.date
                + ", timeslot: " + this.timeSlot;
    }
}
