package nl.tudelft.oopp.demo.entities;

import java.util.Objects;
import javax.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "bikeReservation")
public class BikeReservation {

    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "users")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;          // user id

    @ManyToOne
    @JoinColumn(name = "building")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Building building;          // building code

    @Column(name = "date")
    private String date;

    @Column(name = "timeSlot")
    private String timeSlot;

    public BikeReservation() {

    }

    /**
     * Makes a new BikeReservation object.
     * @param user The user that reserved
     * @param building The building from which the user reserved a bike
     * @param date The date of the reservation
     * @param timeSlot The timeslot of the reservation
     */
    public BikeReservation(User user,
                       Building building,
                       String date,
                       String timeSlot)  {
        this.user = user;
        this.building = building;
        this.timeSlot = timeSlot;
        this.date = date;
    }

    @Override
    public String toString() {
        return "BikeReservation{" + "id=" + id + ", user=" + user + ", building=" + building
                + ", date='" + date + '\'' + ", timeSlot='" + timeSlot + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BikeReservation that = (BikeReservation) o;
        return id.equals(that.id)
                && user.equals(that.user)
                && building.equals(that.building)
                && date.equals(that.date)
                && timeSlot.equals(that.timeSlot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, building, date, timeSlot);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}

