package nl.tudelft.oopp.demo.entities.reservation;

import javax.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @JoinColumn(name = "users")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private int user;

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
    public Reservation(int user, String date, String timeSlot) {
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

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
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

    public abstract String toString();
}
