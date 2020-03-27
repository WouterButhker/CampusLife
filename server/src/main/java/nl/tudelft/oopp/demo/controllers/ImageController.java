package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import nl.tudelft.oopp.demo.entities.image.Image;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import nl.tudelft.oopp.demo.repositories.image.UserImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class ImageController {

    @Autowired
    private UserImageRepository userImageRepository;

    @Autowired
    private UserRepository usersRepository;

    /**
     * Makes sure the file name is correct.
     * @param file the file that is sent
     * @return the name of the new file
     * @throws IOException exception that the file is not ok
     */
    public static String checkFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")) {
            throw new IllegalArgumentException(
                    "Sorry! Filename contains invalid path sequence " + fileName);
        }
        return fileName;
    }

    /**
     * Returns the url to the file that we want to download.
     * @param downloadPath the path from the controller to the download link
     * @param image the Image entity you want to download
     * @return a Url to the downloadable file
     */
    public static String getUrl(String downloadPath, Image image) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(downloadPath)
                .path(image.getImageId())
                .toUriString();
    }

    /**
     * Downloads the file.
     * @param image the Image entity you want to download
     * @return the downloaded file
     */
    public static ResponseEntity<Resource> downloadFile(Image image) {
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + image.getFileName() + "\"")
                .body(new ByteArrayResource(image.getData()));
    }
}
