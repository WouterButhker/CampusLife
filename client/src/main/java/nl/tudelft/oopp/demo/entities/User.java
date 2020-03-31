package nl.tudelft.oopp.demo.entities;

import java.io.Serializable;

public class User implements Serializable {
    private Integer id;

    private String username;
    private String password;

    private String role;
    private boolean accountIsEnabled = true;

    /**
     * Constructor used for registering a new account.
     * @param user The username
     * @param pass The password
     */
    public User(String user, String pass) {
        this.username = user;
        this.password = pass;
        this.role = "Student";
    }

    public User(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isAccountIsEnabled() {
        return accountIsEnabled;
    }

    public void setAccountIsEnabled(boolean accountIsEnabled) {
        this.accountIsEnabled = accountIsEnabled;
    }

    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * The {@code toString} method for class {@code Object}
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
        return "Id: " + id + " user: " + this.username + " pass: " + password + " role: " + role;
    }
}
