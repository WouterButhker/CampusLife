package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.entities.image.Image;
import nl.tudelft.oopp.demo.entities.image.UserImage;
import nl.tudelft.oopp.demo.repositories.UserImageRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(path = "/rest/users")
public class UserController {

    @Autowired
    private UserImageRepository userImageRepository;

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

    @PostMapping("/{userId}/image")
    ResponseEntity<UserImage> uploadFile(@PathVariable Integer userId, @RequestParam("file") MultipartFile file) throws IOException {
        if (!usersRepository.existsById(userId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User user = usersRepository.findUserById(userId);
        if (userImageRepository.existsByUser(user)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if(fileName.contains("..")) {
            throw new IllegalArgumentException(
                    "Sorry! Filename contains invalid path sequence " + fileName);
        }
        UserImage userImage = new UserImage(fileName, file.getContentType(), file.getBytes(), user);
        userImageRepository.save(userImage);

        return new ResponseEntity<>(userImage, HttpStatus.OK);
    }

    @GetMapping("/getUrl/{userId}")
    String getUrl(@PathVariable Integer userId) {
        User user = usersRepository.findUserById(userId);
        if (userImageRepository.existsByUser(user)) {
        UserImage userImage = userImageRepository.findByUser(user);
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/rest/users/downloadFile/")
                .path(userImage.getImageId())
                .toUriString();
        }
        return null;
    }

    @GetMapping("/downloadFile/{imageId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String imageId) {
        Image image = userImageRepository.findByImageId(imageId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                .body(new ByteArrayResource(image.getData()));
    }
}
