package nl.tudelft.oopp.demo.entities;

public class FavoriteRoom {

    private Integer id;
    private Room room;
    private User user;

    /**
     * Makes a new FavoriteRoom object.
     * @param id the id of the favorite pair in the database
     * @param room the room
     * @param user the user
     */
    public FavoriteRoom(Integer id, Room room, User user) {
        this.id = id;
        this.room = room;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
