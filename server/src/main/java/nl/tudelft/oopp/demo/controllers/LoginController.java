package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Login method.
     * @param user the username
     * @param pass the password
     * @return response for the login attempt
     */
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
