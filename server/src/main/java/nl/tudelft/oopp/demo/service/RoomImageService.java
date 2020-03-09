package nl.tudelft.oopp.demo.service;

import nl.tudelft.oopp.demo.entities.RoomImage;
import nl.tudelft.oopp.demo.repositories.RoomImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

@Service
public class RoomImageService {

    @Autowired
    private RoomImageRepository roomImageRepository;

    public RoomImage storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new IllegalArgumentException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            RoomImage roomImage = new RoomImage(fileName, file.getContentType(), file.getBytes());

            return roomImageRepository.save(roomImage);
        } catch (IOException ex) {
            throw new IllegalArgumentException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }


    public Optional<RoomImage> getFile(String fileId) {
        if (roomImageRepository.findById(fileId) != null)
            return roomImageRepository.findById(fileId);
        throw new IllegalArgumentException("File not found with id " + fileId);
    }

}
