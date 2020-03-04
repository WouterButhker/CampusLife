package nl.tudelft.oopp.demo;

import nl.tudelft.oopp.demo.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.Column;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private Integer id;
    private String username;
    private String password;
    private Integer role;
    private User u;

    @BeforeEach
    void setUpper() {
        id = 42;
        username = "Bob";
        password = "pass";
        role = 2;
        u = new User(id, username, password, role);
    }

    @Test
    void getIdTest() {
        assertEquals(id, u.getId());
    }

    @Test
    void setIdTest() {
        u.setId(123);
        assertEquals(123, u.getId());
    }

    @Test
    void getUsernameTest() {
        assertEquals(username, u.getUsername());
    }

    @Test
    void setUsernameTest() {
        u.setUsername("Sam");
        assertEquals("Sam", u.getUsername());
    }

    @Test
    void getPasswordTest() {
        assertEquals(password, u.getPassword());
    }

    @Test
    void setPasswordTest() {
        u.setPassword("Admin123");
        assertEquals("Admin123", u.getPassword());
    }

    @Test
    void getRoleTest() {
        assertEquals(role, u.getRole());
    }

    @Test
    void setRoleTest() {
        u.setRole(9);
        assertEquals(9, u.getRole());
    }
}