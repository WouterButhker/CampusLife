package nl.tudelft.oopp.demo;

import static org.junit.jupiter.api.Assertions.*;

import nl.tudelft.oopp.demo.entities.FavoriteRoom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FavoriteRoomTest {

    private Integer id;
    private Integer room;
    private Integer user;
    private FavoriteRoom fr;

    @BeforeEach
    void setUpper() {
        id = 42;
        room = 123;
        user = 3;
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
        fr.setRoom(1212);
        assertEquals(1212, fr.getRoom());
    }

    @Test
    void getUserTest() {
        assertEquals(user, fr.getUser());
    }

    @Test
    void setUserTest() {
        fr.setUser(1212);
        assertEquals(1212, fr.getUser());
    }
}