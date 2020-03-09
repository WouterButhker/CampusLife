package nl.tudelft.oopp.demo.controllers;

import java.util.Optional;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    /**
     * provides the user role when a request is send.
     * @param username the user to return the role of
     * @return the user role or an error message
     */
    @GetMapping("/login")
    public String login(@RequestBody String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            //TODO: better error handling
            return "Error user not found";
        } else {
            return user.get().getRole();
        }
    }

    /**
     * creates a new user in the database when a request is made.
     * @param user the user to be added to the database
     */
    @PostMapping(path = "/register", consumes = "application/json")
    public void register(@RequestBody User user) {
        Optional<User> u = userRepository.findByUsername(user.getUsername());
        if (u.isEmpty()) {
            userRepository.save(new User(user));
        } else {
            //TODO: send error user already exists
        }
    }

}
