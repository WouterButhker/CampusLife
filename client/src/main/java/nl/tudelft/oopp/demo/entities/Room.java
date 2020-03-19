package nl.tudelft.oopp.demo.entities;

import java.util.Objects;

public class Room {
    private String roomCode;
    private String name;
    private Integer capacity;
    private boolean hasWhiteboard;
    private boolean hasTV;
    private Integer rights;
    private Building building;

    /**
     * Creates a new Room object.
     * @param roomCode String with the Room code
     * @param name String with the name of the Room
     * @param capacity the number of available places
     * @param hasWhiteboard true if the Room has a whiteboard
     * @param hasTV true if the Room has a TV
     * @param rights the rights needed to reserve a room
     * @param building the number of the building the Room is from
     */
    public Room(String roomCode,
                String name,
                Integer capacity,
                boolean hasWhiteboard,
                boolean hasTV,
                Integer rights,
                Building building) {
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

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public boolean isHasWhiteboard() {
        return hasWhiteboard;
    }

    public void setHasWhiteboard(boolean hasWhiteboard) {
        this.hasWhiteboard = hasWhiteboard;
    }

    public boolean isHasTV() {
        return hasTV;
    }

    public void setHasTV(boolean hasTV) {
        this.hasTV = hasTV;
    }

    public Integer getRights() {
        return rights;
    }

    public void setRights(Integer rights) {
        this.rights = rights;
    }

    public Building getBuilding() {
        return building;
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
}
