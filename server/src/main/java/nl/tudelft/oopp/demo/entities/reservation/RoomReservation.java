package nl.tudelft.oopp.demo.entities.reservation;

import java.util.Objects;
import javax.persistence.*;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "room_reservation")
public class RoomReservation extends Reservation {

    @ManyToOne
    @JoinColumn(name = "room_code", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Room room;          // room code

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

        super(user, date, timeSlot);
        this.room = room;
    }


    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoomReservation)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        RoomReservation that = (RoomReservation) o;
        return Objects.equals(room, that.room);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), room);
    }

    @Override
    public String toString() {
        return "Roomreservation{" + super.toString()
                + ", room: " + this.room
                + "}";
    }
}
