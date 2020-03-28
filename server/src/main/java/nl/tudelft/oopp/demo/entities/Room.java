package nl.tudelft.oopp.demo.entities;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "room")
public class Room {

    @Id
    @Column(name = "room_code")
    private String roomCode;

    @Column(name = "name")
    private String name;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "whiteboard")
    private boolean hasWhiteboard;

    @Column(name = "tv")
    private boolean hasTV;

    @Column(name = "rights")
    private Integer rights;

    //@Id
    @ManyToOne
    @JoinColumn(name = "building")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Building building;

    public Room() {

    }

    /**
     * Makes a new room with the given parameters.
     * @param roomCode the abreviation of the room
     * @param name the actual name of the room
     * @param capacity how many spaces when empty
     * @param hasWhiteboard if the room has a whiteboard
     * @param hasTV if the room has a TV
     * @param rights (minimum) required to reserve this room
     * @param building in which the room is
     */
    public Room(String roomCode,
                String name,
                Integer capacity,
                boolean hasWhiteboard,
                boolean hasTV,
                Integer rights,
                Building building)  {
        this.roomCode = roomCode;
        this.name = name;
        this.capacity = capacity;
        this.hasWhiteboard = hasWhiteboard;
        this.hasTV = hasTV;
        this.rights = rights;
        this.building = building;
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

    public Integer getRights() {
        return rights;
    }

    public Building getBuilding() {
        return building;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public void setHasWhiteboard(boolean hasWhiteboard) {
        this.hasWhiteboard = hasWhiteboard;
    }

    public void setHasTV(boolean hasTV) {
        this.hasTV = hasTV;
    }

    public void setRights(Integer rights) {
        this.rights = rights;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Room)) {
            return false;
        }
        Room room = (Room) o;
        return hasWhiteboard == room.hasWhiteboard
                && hasTV == room.hasTV
                && roomCode.equals(room.roomCode)
                && name.equals(room.name)
                && capacity.equals(room.capacity)
                && rights.equals(room.rights)
                && building.equals(room.building);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomCode, name, capacity, hasWhiteboard, hasTV, rights, building);
    }

    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p>
     * The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "room{roomcode: " + this.roomCode
                + ", name: " + this.name
                + ", capacity: " + this.capacity
                + ", rights: " + this.rights
                + ", hasTV: " + this.hasTV
                + ", hasWhiteboard: " + this.hasWhiteboard
                + ", building: " + this.building
                + "}";
    }
}
