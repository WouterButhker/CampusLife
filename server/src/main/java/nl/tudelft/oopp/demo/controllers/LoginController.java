package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {



    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/login")
    public String login(@RequestParam String user, @RequestParam String pass) {
        if (user.equals("admin")) {
            System.out.println("User admin logged in");
            return "true";
        }
        System.out.println("User tried to login");
        return "false";
    }

    private void test() {

    }

    @GetMapping("/")
    public String home() {
        return "<h1>Welcome</h1>";
    }

    @GetMapping("/admin")
    public String admin() {
        return "<h1>Welcome ADMIN</h1>";
    }

    @GetMapping("/user")
    public String user() {
        return "<h1>HELLO AUTHORIZED USER</h1>";
    }

    private void loginn() {

    }

}
