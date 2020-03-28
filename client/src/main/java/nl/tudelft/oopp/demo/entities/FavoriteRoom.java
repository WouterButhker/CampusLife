package nl.tudelft.oopp.demo.entities;

public class FavoriteRoom {

    private Integer id;
    private Integer room;
    private Integer user;

    /**
     * Makes a new FavoriteRoom object.
     * @param id the id of the favorite pair in the database
     * @param room the id of the room
     * @param user the id of the user
     */
    public FavoriteRoom(Integer id, Integer room, Integer user) {
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

    public Integer getRoom() {
        return room;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }
}
