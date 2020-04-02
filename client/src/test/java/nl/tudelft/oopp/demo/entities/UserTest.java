package nl.tudelft.oopp.demo.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private Integer id;
    private String username;
    private String password;
    private String role;
    private boolean accountIsEnabled;
    private User user;

    @BeforeEach
    void setUpper() {
        id = 123;
        username = "username";
        password = "password";
        role = "Student";
        accountIsEnabled = true;
        user = new User(username, password);
    }

    @Test
    void constructorTest() {
        assertNotNull(user);
    }

    @Test
    void getUsernameTest() {
        assertEquals(username, user.getUsername());
    }

    @Test
    void setUsernameTest() {
        user.setUsername("hello");
        assertEquals("hello", user.getUsername());
    }

    @Test
    void getPasswordTest() {
        assertEquals(password, user.getPassword());
    }

    @Test
    void setPasswordTest() {
        user.setPassword("world");
        assertEquals("world", user.getPassword());
    }

    @Test
    void getIdTest() {
        assertEquals(null, user.getId());
    }

    @Test
    void setIdTest() {
        user.setId(123);
        assertEquals(id, user.getId());
    }

    @Test
    void getRoleTest() {
        assertEquals(role, user.getRole());
    }

    @Test
    void setRoleTest() {
        user.setRole("Admin");
        assertEquals("Admin", user.getRole());
    }

    @Test
    void isAccountIsEnabledTest() {
        assertEquals(true, user.isAccountIsEnabled());
    }

    @Test
    void setAccountIsEnabledTest() {
        user.setAccountIsEnabled(false);
        assertEquals(false, user.isAccountIsEnabled());
    }

    @Test
    void toStringTest() {
        assertEquals("Id: null user: username pass: password role: Student", user.toString());
    }
}