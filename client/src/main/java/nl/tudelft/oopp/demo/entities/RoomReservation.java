package nl.tudelft.oopp.demo.entities;

import java.util.Objects;

public class RoomReservation {

    private Integer id;
    private Integer user;
    private String room;
    /// DATE
    private String timeSlot;

    /**
     * Make a Reservation object.
     * @param id the number of the reservation
     * @param user the User(id) that made the reservation
     * @param room the Room(roomCode) that is reserved
     * @param timeSlot the time at which the Room is reserved
     */
    public RoomReservation(Integer id, Integer user, String room, String timeSlot) {
        this.id = id;
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

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
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
        if (!(o instanceof RoomReservation)) {
            return false;
        }
        RoomReservation that = (RoomReservation) o;
        return id.equals(that.id)
                && user.equals(that.user)
                && room.equals(that.room)
                && timeSlot.equals(that.timeSlot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, room, timeSlot);
    }
}
