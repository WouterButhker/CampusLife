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
    @JoinColumn(name = "pickUpBuilding")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Building pickUpBuilding;

    @ManyToOne
    @JoinColumn(name = "dropOffBuilding")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Building dropOffBuilding;

    @Column(name = "date")
    private String date;

    @Column(name = "timeSlot")
    private String timeSlot;

    public BikeReservation() {

    }

    /**
     * Makes a new BikeReservation object.
     * @param user The user that reserved
     * @param pickUpBuilding the building from where the bike is picked up
     * @param dropOffBuilding the building where the bike is dropped off
     * @param date The date of the reservation
     * @param timeSlot The timeslot of the reservation
     */
    public BikeReservation(User user,
                       Building pickUpBuilding,
                       Building dropOffBuilding,
                       String date,
                       String timeSlot)  {
        this.user = user;
        this.pickUpBuilding = pickUpBuilding;
        this.dropOffBuilding = dropOffBuilding;
        this.timeSlot = timeSlot;
        this.date = date;
    }

    @Override
    public String toString() {
        return "BikeReservation{" + "id=" + id + ", user=" + user + ", pickUpBuilding="
                + pickUpBuilding + ", dropOffBuilding=" + dropOffBuilding
                + ", date='" + date + '\'' + ", timeSlot='" + timeSlot + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BikeReservation that = (BikeReservation) o;
        return id.equals(that.id)
                && user.equals(that.user)
                && pickUpBuilding.equals(that.pickUpBuilding)
                && dropOffBuilding.equals(that.dropOffBuilding)
                && date.equals(that.date)
                && timeSlot.equals(that.timeSlot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, pickUpBuilding, dropOffBuilding, date, timeSlot);
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

    public Building getPickUpBuilding() {
        return pickUpBuilding;
    }

    public void setPickUpBuilding(Building pickUpBuilding) {
        this.pickUpBuilding = pickUpBuilding;
    }

    public Building getDropOffBuilding() {
        return dropOffBuilding;
    }

    public void setDropOffBuilding(Building dropOffBuilding) {
        this.dropOffBuilding = dropOffBuilding;
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

