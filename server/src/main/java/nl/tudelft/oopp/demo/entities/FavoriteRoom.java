package nl.tudelft.oopp.demo.entities;

import javax.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "favoriteRoom")
public class FavoriteRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "room")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "user")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public FavoriteRoom() {

    }

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
