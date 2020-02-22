package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.Quote;
import nl.tudelft.oopp.demo.entities.Users;
import nl.tudelft.oopp.demo.repositories.QuoteRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/rest/users")
public class UsersController {

    @Autowired
    private UserRepository usersRepository;

    @GetMapping(path = "/all")
    public List<Users> getAll() {
       return usersRepository.findAll();
    }

    @GetMapping(path = "/add")
    public @ResponseBody String addNewUser(@RequestParam String username){
        Users user = new Users();
        user.setUsername(username);
        usersRepository.save(user);
        return "Saved";
    }

}
