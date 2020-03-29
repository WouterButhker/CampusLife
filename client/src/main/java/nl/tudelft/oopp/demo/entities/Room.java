package nl.tudelft.oopp.demo.entities;

import java.util.Objects;

public class Room {
    private String code;
    private String name;
    private Integer capacity;
    private boolean hasWhiteboard;
    private boolean hasTV;
    private Integer rights;
    private Integer buildingCode;

    /**
     * Creates a new Room object.
     * @param code String with the Room code
     * @param name String with the name of the Room
     * @param capacity the number of available places
     * @param hasWhiteboard true if the Room has a whiteboard
     * @param hasTV true if the Room has a TV
     * @param rights the rights needed to reserve a room
     * @param buildingCode the number of the building the Room is from
     */
    public Room(String code,
                String name,
                Integer capacity,
                boolean hasWhiteboard,
                boolean hasTV,
                Integer rights,
                Integer buildingCode) {
        this.code = code;
        this.name = name;
        this.capacity = capacity;
        this.hasWhiteboard = hasWhiteboard;
        this.hasTV = hasTV;
        this.rights = rights;
        this.buildingCode = buildingCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Integer getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(Integer buildingCode) {
        this.buildingCode = buildingCode;
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
                && code.equals(room.code)
                && name.equals(room.name)
                && capacity.equals(room.capacity)
                && rights.equals(room.rights)
                && buildingCode.equals(room.buildingCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, capacity, hasWhiteboard, hasTV, rights, buildingCode);
    }
}
