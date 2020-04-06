package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.*;

import nl.tudelft.oopp.demo.entities.FavoriteRoom;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FavoriteRoomTest {

    private Integer id;
    private Room room;
    private User user;
    private FavoriteRoom fr;

    @BeforeEach
    void setUpper() {
        id = 42;
        room = new Room();
        user = new User();
        fr = new FavoriteRoom(id, room, user);
    }

    @Test
    void constructorTest() {
        assertNotNull(fr);
    }

    @Test
    void getIdTest() {
        assertEquals(id, fr.getId());
    }

    @Test
    void setIdTest() {
        fr.setId(1212);
        assertEquals(1212, fr.getId());
    }

    @Test
    void getRoomTest() {
        assertEquals(room, fr.getRoom());
    }

    @Test
    void setRoomTest() {
        fr.setRoom(new Room("PC1", null, 1, false, false, 1, null));
        assertEquals("PC1", fr.getRoom().getRoomCode());
    }

    @Test
    void getUserTest() {
        assertEquals(user, fr.getUser());
    }

    @Test
    void setUserTest() {
        fr.setUser(new User(1212));
        assertEquals(1212, fr.getUser().getId());
    }

    @Test
    void toStringTest() {
        assertEquals("FavoriteRoom{id=42, room=room{roomcode: null, name: null, capacity:"
                + " null, rights: null, hasTV: false, hasWhiteboard: false, building: null},"
                + " user=user{id: null, name:null, role: null}}", fr.toString());
    }
}