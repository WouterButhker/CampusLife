package nl.tudelft.oopp.demo.entities;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;          // user id

    @ManyToOne
    @JoinColumn(name = "room")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Room room;          // room code

    @Column(name = "timeSlot")
    private String timeSlot;

    public Reservation() {

    }

    /**
     * Make a Reservation object.
     * @param user the User that makes the reservation
     * @param room the Room that is reserved
     * @param timeSlot the time at which the Room is reserved
     */
    public Reservation(User user,
                       Room room,
                       String timeSlot)  {
        System.out.println(this.id);
        this.user = user;
        this.room = room;
        this.timeSlot = timeSlot;
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

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
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
        return id.equals(that.id)
                && user.equals(that.user)
                && room.equals(that.room)
                && timeSlot.equals(that.timeSlot);
    }

    public String toString() {
        return "[" + id + ", " + user + ", " + room + ", " + timeSlot + "]";
    }
}
