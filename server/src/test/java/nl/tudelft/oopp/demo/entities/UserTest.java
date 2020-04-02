package nl.tudelft.oopp.demo.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private Integer id;
    private String username;
    private String password;
    private String role;
    private boolean accountIsEnabled;
    private User user1;
    private User user2;
    private User user3;
    private User user4;

    @BeforeEach
    void setUpper() {
        id = 123;
        username = "username";
        password = "password";
        role = "Student";
        accountIsEnabled = true;
        user1 = new User(username, password, role);
        user2 = new User(username, password);
        user3 = new User(id);
        user4 = new User();
    }

    @Test
    void constructorTest1() {
        assertNotNull(user1);
    }

    @Test
    void constructorTest2() {
        assertNotNull(user2);
    }

    @Test
    void constructorTest3() {
        assertNotNull(user3);
    }

    @Test
    void constructorTest4() {
        assertNotNull(user4);
    }

    @Test
    void getIdTest() {
        assertEquals(id, user3.getId());
    }

    @Test
    void getAuthoritiesTest() {
        assertEquals(new SimpleGrantedAuthority(role), user1.getAuthorities().toArray()[0]);
    }

    @Test
    void getPasswordTest() {
        assertEquals(password, user1.getPassword());
    }

    @Test
    void getUsernameTest() {
        assertEquals(username, user1.getUsername());
    }

    @Test
    void getRoleTest() {
        assertEquals(role, user1.getRole());
    }

    @Test
    void isAccountNonExpiredTest() {
        assertEquals(accountIsEnabled, user1.isAccountNonExpired());
    }

    @Test
    void isAccountNonLockedTest() {
        assertEquals(accountIsEnabled, user1.isAccountNonLocked());
    }

    @Test
    void isCredentialsNonExpiredTest() {
        assertEquals(accountIsEnabled, user1.isCredentialsNonExpired());
    }

    @Test
    void isEnabledTest() {
        assertEquals(accountIsEnabled, user1.isEnabled());
    }

    @Test
    void toStringTest() {
        assertEquals("NAME: username", user1.toString());
    }
}