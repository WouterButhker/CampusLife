package nl.tudelft.oopp.demo.service;

import nl.tudelft.oopp.demo.entities.Image;
import nl.tudelft.oopp.demo.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public Image storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new IllegalArgumentException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Image image = new Image(fileName, file.getContentType(), file.getBytes());

            return imageRepository.save(image);
        } catch (IOException ex) {
            throw new IllegalArgumentException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }


    public Image getFile(String fileId) {
        return imageRepository.findById(fileId)
                .orElseThrow(() -> new IllegalArgumentException("File not found with id " + fileId));
    }

    public List<Image> getAll() {
        return imageRepository.findAll();
    }

    public Image getImageById(String imageId) {
        return imageRepository.getImageById(imageId);
    }
}
