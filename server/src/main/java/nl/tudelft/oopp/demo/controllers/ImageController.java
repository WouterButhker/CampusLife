package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.Image;
import nl.tudelft.oopp.demo.repositories.ImageRepository;
import nl.tudelft.oopp.demo.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    private String makeUrl(Image image) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/images/downloadFile/")
                .path(image.getId())
                .toUriString();
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        Image image = imageService.storeFile(file);

        return new String(image.getFileName() + " | " + makeUrl(image)
                + " | " + file.getContentType() + " | " + file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    public List<String> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{imageId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String imageId) {
        // Load file from database
        Image image = imageService.getFile(imageId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                .body(new ByteArrayResource(image.getData()));
    }

    @GetMapping("/allIds")
    public List<String> getAllIds() {
        List<Image> images = imageService.getAll();
        List<String> response = new ArrayList<>();
        for (Image image: images) {
            response.add(image.getId());
        }
        return  response;
    }

    @GetMapping("/getUrl/{imageId}")
    public String getUrl(@PathVariable String imageId) {
        return makeUrl(imageService.getImageById(imageId));
    }

}
