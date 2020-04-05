package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.util.List;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.entities.image.UserImage;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import nl.tudelft.oopp.demo.repositories.image.UserImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/rest/users")
public class UserController {

    @Autowired
    private UserRepository usersRepository;

    @Autowired
    private UserImageRepository userImageRepository;

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

    @PutMapping(path = "changeRole")
    public User changeRole(@RequestBody User user) {
        return usersRepository.save(user);
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<Integer> deleteUser(@PathVariable Integer id) {
        ResponseEntity<Integer> res = new ResponseEntity<>(id, HttpStatus.NOT_FOUND);
        if (usersRepository.findById(id).isPresent()) {
            usersRepository.deleteById(id);
            res = new ResponseEntity<>(id, HttpStatus.OK);
        }
        return res;
    }


    @PutMapping(path = "/changePassword")
    public ResponseEntity<User> changePassword(@RequestBody User user) {
        ResponseEntity<User> res = new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
        if (usersRepository.existsById(user.getId())) {
            usersRepository.updatePassword(user.getUsername(), user.getPassword());
            res = new ResponseEntity<>(user, HttpStatus.OK);
        }
        return res;
    }

    @Modifying
    @PutMapping(value = "/image/{userId}")
    ResponseEntity<UserImage> uploadFile(@PathVariable Integer userId,
                                         @RequestParam("file") MultipartFile file)
            throws IOException {
        if (!usersRepository.existsById(userId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User user = usersRepository.findUserById(userId);
        if (userImageRepository.existsByUser(user)) {
            userImageRepository.deleteByUser(user);
        }
        String fileName = ImageController.checkFile(file);
        UserImage userImage = new UserImage(fileName, file.getContentType(), file.getBytes(), user);
        userImageRepository.save(userImage);
        return new ResponseEntity<>(userImage, HttpStatus.OK);
    }

    @GetMapping("/image/getUrl/{userId}")
    String getUrl(@PathVariable Integer userId) {
        User user = usersRepository.findUserById(userId);
        if (userImageRepository.existsByUser(user)) {
            UserImage userImage = userImageRepository.findByUser(user);
            return ImageController.getUrl("/rest/users/image/downloadFile/", userImage);
        }
        return null;
    }

    @GetMapping("/image/downloadFile/{imageId}")
    ResponseEntity<Resource> downloadFile(@PathVariable String imageId) {
        return ImageController.downloadFile(userImageRepository.findByImageId(imageId));
    }
}
