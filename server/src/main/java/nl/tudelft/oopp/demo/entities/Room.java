package nl.tudelft.oopp.demo.entities;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "room")
public class Room {
    @Id
    @Column(name = "code")
    private String roomCode;

    @Column(name = "name")
    private String name;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "whiteboard")
    private boolean hasWhiteboard;

    @Column(name = "tv")
    private boolean hasTV;

    // TODO: add class userRole or something
    //@Column(name = "rights")
    //private UserRole userRole;

    // TODO: figure out foreign keys
    // TODO: add building class
    //@Column(name = "building")
    //private Building building;


    // TODO: add user and building

    /**
     *  Create new Room instance.
     * @param roomCode unique room identifier
     * @param name name of the room
     * @param capacity maximum number of people allowed in the room
     * @param hasWhiteboard true if there is a whiteboard in the room
     * @param hasTV true if there is a TV in the room
     */
    public Room(String roomCode, String name, int capacity, boolean hasWhiteboard, boolean hasTV) {
        this.roomCode = roomCode;
        this.name = name;
        this.capacity = capacity;
        this.hasWhiteboard = hasWhiteboard;
        this.hasTV = hasTV;
    }

    public Room() {

    }

    public String getRoomCode() {
        return roomCode;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isHasWhiteboard() {
        return hasWhiteboard;
    }

    public boolean isHasTV() {
        return hasTV;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Room room = (Room) o;
        return roomCode.equals(room.roomCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomCode);
    }
}
