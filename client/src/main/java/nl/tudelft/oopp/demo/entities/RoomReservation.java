package nl.tudelft.oopp.demo.entities;

import java.util.Objects;

public class RoomReservation extends Reservation {

    private String room;

    /**
     * Make a Reservation object.
     * @param user the User(id) that made the reservation
     * @param room the Room(roomCode) that is reserved
     * @param timeSlot the time at which the Room is reserved
     */
    public RoomReservation(int user, String room, String timeSlot) {
        super(user, timeSlot.substring(0, 10), timeSlot);
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
