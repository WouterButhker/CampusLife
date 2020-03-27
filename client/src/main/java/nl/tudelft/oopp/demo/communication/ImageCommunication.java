package nl.tudelft.oopp.demo.communication;

import static nl.tudelft.oopp.demo.communication.AuthenticationCommunication.*;
import static nl.tudelft.oopp.demo.communication.ServerCommunication.SERVER_URL;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.ResponseEntity;

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

    public static String updateRoomImage(String roomCode, File image) {
        String url = "/rooms/image/" + roomCode;
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

    public static List<String> getRoomImageUrl(String roomCode) {
        List<String> defaultResponse = new ArrayList<>();
        defaultResponse.add("images/RoomTempIMG.jpg");
        ResponseEntity<String> response = authenticatedRequest(
                "/rooms/image/getUrl/" + roomCode);
        if (response != null && response.getBody() == null) {
            return defaultResponse;
        } else if (response != null && response.getBody() != null
                && response.getStatusCode().toString().equals("200 OK")) {
            System.out.println("Image Url: " + response.getBody());
            Type listType = new TypeToken<List<String>>() {}.getType();
            return new Gson().fromJson(response.getBody(), listType);
        } else {
            System.out.println("Login failed");
        }
        return defaultResponse;
    }

}
