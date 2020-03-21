package nl.tudelft.oopp.demo.entities;

import java.util.Collection;
import java.util.Collections;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // same as SQL autoincrement
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "username", unique = true, nullable = false, length = 25)
    private String username;

    @Column(name = "password", nullable = false, length = 60)
    private String password;

    // currently supports only a single role
    @Column(name = "role", length = 20)
    private String role;

    @Column(name = "enabled")
    private boolean accountIsEnabled = true;

    /**
     * Creates a new user with an autogenerated id.
     *
     * @param username the users username
     * @param password the users password (bcrypt)
     * @param role the users access level
     */
    public User(String username,
                String password,
                String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Creates a new user with an autogenerated id and student role.
     * @param username the users username
     * @param password the users password (bcrypt)
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = "Student";
    }

    public User(int id) {
        this.id = id;
    }

    public User() {

    }

    public Integer getId() {
        return id;
    }

    /**
     * Returns the authorities granted to the user. Cannot return <code>null</code>.
     *
     * @return the authorities, sorted by natural key (never <code>null</code>)
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.role));
    }

    /**
     * Returns the password used to authenticate the user.
     *
     * @return the password
     */
    @Override
    public String getPassword() {
        return this.password;
    }

    /**
     * returns the users username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    public String getRole() {
        return this.role;
    }

    /**
     * Indicates whether the user's account has expired. An expired account cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user's account is valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @Override
    public boolean isAccountNonExpired() {
        return accountIsEnabled;
    }

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return accountIsEnabled;
    }

    /**
     * Indicates whether the user's credentials (password) has expired. Expired
     * credentials prevent authentication.
     *
     * @return <code>true</code> if the user's credentials are valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return accountIsEnabled;
    }

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
     */
    @Override
    public boolean isEnabled() {
        return accountIsEnabled;
    }


    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     *
     * <p>The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "NAME: " + username;
    }


}
