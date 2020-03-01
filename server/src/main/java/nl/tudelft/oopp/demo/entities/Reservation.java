package nl.tudelft.oopp.demo.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;          // user id

    @ManyToOne
    @JoinColumn(name = "room")
    private Room room;          // room code

    @Column(name = "timeSlot")
    private String timeSlot;

    public Reservation() {

    }


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
        if (this == o) return true;
        if (!(o instanceof Reservation)) return false;
        Reservation that = (Reservation) o;
        return id.equals(that.id) &&
                user.equals(that.user) &&
                room.equals(that.room) &&
                timeSlot.equals(that.timeSlot);
    }

    public String toString() {
        return "[" + id + ", " + user + ", " + room + ", " + timeSlot + "]";    }
}
