package nl.tudelft.oopp.demo.entities;

import static org.junit.jupiter.api.Assertions.*;

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
        room = new Room("PC1", null, 1, false, false, 1, null);
        user = new User(3);
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
        assertTrue(fr.getId().equals(1212));
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
        assertTrue(fr.getUser().getId().equals(1212));
    }
}