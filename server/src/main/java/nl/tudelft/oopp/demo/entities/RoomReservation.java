package nl.tudelft.oopp.demo.entities;

import javax.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;



@Entity
@Table(name = "reservation")
public class RoomReservation {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "users")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;          // user id

    @ManyToOne
    @JoinColumn(name = "roomCode")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Room room;          // room code

    @Column(name = "date")
    private String date;

    @Column(name = "timeSlot")
    private String timeSlot;

    public RoomReservation() {

    }

    /**
     * Make a RoomReservation object.
     * @param user the User that makes the reservation
     * @param room the Room that is reserved
     * @param timeSlot the time at which the Room is reserved
     */
    public RoomReservation(User user,
                           Room room,
                           String date,
                           String timeSlot)  {
        System.out.println(this.id);
        this.user = user;
        this.room = room;
        this.timeSlot = timeSlot;
        this.date = date;
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
        if (!(o instanceof RoomReservation)) {
            return false;
        }
        RoomReservation that = (RoomReservation) o;
        return id.equals(that.id)
                && user.equals(that.user)
                && room.equals(that.room)
                && timeSlot.equals(that.timeSlot);
    }

    public String toString() {
        return "[" + id + ", " + user + ", " + room + ", " + date + ", " + timeSlot + "]";
    }
}
