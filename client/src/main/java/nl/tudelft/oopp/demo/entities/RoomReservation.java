package nl.tudelft.oopp.demo.entities;

public class RoomReservation extends Reservation {

    private Integer user;
    private Room room;

    /**
     * Make a Reservation object.
     * @param user the User(id) that made the reservation
     * @param room the Room(roomCode) that is reserved
     * @param timeSlot the time at which the Room is reserved
     */
    public RoomReservation(int user, Room room, String timeSlot) {
        super(null, user, timeSlot.substring(0, 10), timeSlot);
        this.room = room;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return null;
    }
}
