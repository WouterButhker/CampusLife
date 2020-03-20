package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    @PutMapping("/image")
    ResponseEntity<User> uploadFile(@RequestBody User user, @RequestParam("file") MultipartFile file) {
        if (!usersRepository.existsById(user.getId())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        usersRepository.deleteById(user.getId());
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        //try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new IllegalArgumentException(
                        "Sorry! Filename contains invalid path sequence " + fileName);
            }
//            user.setFileName(fileName);
//            user.setFileType(file.getContentType());
//            user.setData(file.getBytes());
            usersRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        //}
        /*
        catch (IOException ex) {
            throw new IllegalArgumentException(
                    "Could not store file " + fileName + ". Please try again!", ex);
        }*/
    }

    @GetMapping("/getUrl/{userId}")
    String getUrl(@PathVariable Integer userId) {
        User user = usersRepository.findUserById(userId);
        /*
        if (usersRepository.existsById(userId)) {

            return ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/images/downloadFile/")
                    .path(user.getImageId())
                    .toUriString();
        }
        */
        return null;
    }

}
