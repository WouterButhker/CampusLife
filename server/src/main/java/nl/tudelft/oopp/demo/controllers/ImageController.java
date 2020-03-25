package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.image.Image;
import nl.tudelft.oopp.demo.repositories.image.UserImageRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@RestController
public class ImageController {

    @Autowired
    private UserImageRepository userImageRepository;

    @Autowired
    private UserRepository usersRepository;

    public static String checkFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")) {
            throw new IllegalArgumentException(
                    "Sorry! Filename contains invalid path sequence " + fileName);
        }
        return fileName;
    }

    public static String getUrl(String downloadPath, Image image) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(downloadPath)
                .path(image.getImageId())
                .toUriString();
    }

    public static ResponseEntity<Resource> downloadFile(Image image) {
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + image.getFileName() + "\"")
                .body(new ByteArrayResource(image.getData()));
    }
}
