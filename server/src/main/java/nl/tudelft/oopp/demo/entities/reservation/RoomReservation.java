package nl.tudelft.oopp.demo.entities.reservation;

import javax.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "room_reservation")
public class RoomReservation extends Reservation {


    @JoinColumn(name = "room_code")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private String room;          // room code

    public RoomReservation() {

    }

    /**
     * Make a RoomReservation object.
     * @param user the User that makes the reservation
     * @param room the Room that is reserved
     * @param timeSlot the time at which the Room is reserved
     */
    public RoomReservation(int user,
                           String room,
                           String date,
                           String timeSlot)  {

        super(user, date, timeSlot);
        this.room = room;

    }


    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return null;
    }
}
