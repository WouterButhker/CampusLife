package nl.tudelft.oopp.demo.controllers;

import java.util.List;
import java.util.Optional;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/rest/users")
public class UserController {

    @Autowired
    private UserRepository usersRepository;

    @GetMapping(path = "/all")
    public List<User> getAll() {
        return usersRepository.findAll();
    }

    @GetMapping(path = "/getId")
    public Integer getId(@RequestParam String username) {
        return usersRepository.findIdByUsername(username);
    }

    @GetMapping(path = "/getRole")
    public String getRole(@RequestParam String username) {
        return usersRepository.findRoleByUsername(username);
    }

    /*
     * Adds a new user to the database.
     * @param username of the current user
     * @return

    @GetMapping(path = "/add")
    public @ResponseBody
    String addNewUser(@RequestParam String username) {
        User user = new User();
        user.setUsername(username);
        usersRepository.save(user);
        return "Saved";
    }
     */

}
