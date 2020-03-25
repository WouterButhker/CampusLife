package nl.tudelft.oopp.demo.communication;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.FileInputStream;
import java.util.Base64;

import static nl.tudelft.oopp.demo.communication.AuthenticationCommunication.*;
import static nl.tudelft.oopp.demo.communication.ServerCommunication.SERVER_URL;

public class ImageCommunication {

    /**
     * Changes the profile picture of the user.
     * @param image the file that becomes the profile picture
     * @return
     */
    public static String updateUserImage(File image) {
        String url = "/rest/users/image/" + myUserId;
        try {
            HttpPut uploadFile = new HttpPut(SERVER_URL + url);
            String encodedauth = Base64.getEncoder().encodeToString(auth.getBytes());
            uploadFile.setHeader("Authorization", "Basic " + encodedauth);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody("file", new FileInputStream(image),
                    ContentType.MULTIPART_FORM_DATA, image.getName());
            org.apache.http.HttpEntity multipart = builder.build();
            uploadFile.setEntity(multipart);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(uploadFile);
            org.apache.http.HttpEntity responseEntity = response.getEntity();
            System.out.println();
            httpClient.close();
            response.close();
            saveUserImageUrl(myUserId);
            if (response.getStatusLine().getStatusCode() == 200) {
                return "200 OK";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "400 BAD_REQUEST";
    }

    public static String updateBuildingImage(Integer buildingId, File image) {
        String url = "/buildings/image/" + buildingId;
        try {
            HttpPut uploadFile = new HttpPut(SERVER_URL + url);
            String encodedauth = Base64.getEncoder().encodeToString(auth.getBytes());
            uploadFile.setHeader("Authorization", "Basic " + encodedauth);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody("file", new FileInputStream(image),
                    ContentType.MULTIPART_FORM_DATA, image.getName());
            org.apache.http.HttpEntity multipart = builder.build();
            uploadFile.setEntity(multipart);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(uploadFile);
            org.apache.http.HttpEntity responseEntity = response.getEntity();
            System.out.println();
            httpClient.close();
            response.close();
            if (response.getStatusLine().getStatusCode() == 200) {
                return "200 OK";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "400 BAD_REQUEST";
    }

    public static String getBuildingImageUrl(Integer buildingCode) {
        ResponseEntity<String> response = authenticatedRequest(
                "/buildings/image/getUrl/" + buildingCode);
        if (response != null && response.getBody() == null) {
            return "images/TuDelftTempIMG.jpg";
        } else if (response != null && response.getBody() != null
                && response.getStatusCode().toString().equals("200 OK")) {
            System.out.println("Image Url: " + response.getBody());
            return response.getBody();
        } else {
            System.out.println("Login failed");
        }
        return "images/TuDelftTempIMG.jpg";
    }
}
