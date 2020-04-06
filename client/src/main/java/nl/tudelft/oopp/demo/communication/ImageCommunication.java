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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;


public class ImageCommunication {

    private static String updateImage(File image, String url) {
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

    /**
     * Changes the profile picture of the user.
     * @param image the file that becomes the profile picture
     * @return "200 OK" / "400 BAD_REQUEST"
     */
    public static String updateUserImage(File image) {
        String url = "/rest/users/image/" + myUserId;
        String response = updateImage(image, url);
        if (response.equals("200 OK")) {
            saveUserImageUrl(myUserId);
        }
        return response;
    }

    /**
     * Updates the image of a Building.
     * @param buildingId the number of the Building
     * @param image the new image
     * @return "200 OK" / "400 BAD_REQUEST"
     */
    public static String updateBuildingImage(Integer buildingId, File image) {
        String url = "/buildings/image/" + buildingId;
        return updateImage(image, url);
    }

    /**
     * Updates the image of a Room.
     * @param roomCode the code of the Room
     * @param image the new image
     * @return "200 OK" / "400 BAD_REQUEST"
     */
    public static String updateRoomImage(String roomCode, File image) {
        roomCode = roomCode.replace(" ", "%20");
        String url = "/rooms/image/" + roomCode;
        return updateImage(image, url);
    }

    public static String updateRestaurantImage(Integer restaurantCode, File image) {
        String url = "/restaurants/image/" + restaurantCode;
        return updateImage(image, url);
    }

    private static String getImageUrl(String url, String defaultImage) {
        ResponseEntity<String> response = authenticatedRequest(url);
        if (response != null && response.getBody() == null) {
            return defaultImage;
        } else if (response != null && response.getBody() != null
                && response.getStatusCode().toString().equals("200 OK")) {
            return response.getBody();
        } else {
            System.out.println("Login failed");
        }
        return defaultImage;
    }

    /**
     * Gets the Url of the image from the database or a default image.
     * @param buildingCode the number of the building
     * @return url of an image
     */
    public static String getBuildingImageUrl(Integer buildingCode) {
        return getImageUrl("/buildings/image/getUrl/" + buildingCode,
                "images/TuDelftTempIMG.jpg");
    }

    private static List<String> getImagesUrl(String url, String defaultImage) {
        List<String> defaultResponse = new ArrayList<>();
        defaultResponse.add(defaultImage);
        ResponseEntity<String> response = authenticatedRequest(url);
        if (response != null && response.getBody() == null) {
            return defaultResponse;
        } else if (response != null && response.getBody() != null
                && response.getStatusCode().toString().equals("200 OK")) {
            Type listType = new TypeToken<List<String>>() {}.getType();
            return new Gson().fromJson(response.getBody(), listType);
        } else {
            System.out.println("Login failed");
        }
        return defaultResponse;
    }

    public static List<String> getRestaurantImageUrl(Integer restaurantCode) {
        return getImagesUrl("/restaurants/image/getUrl/" + restaurantCode,
                "images/restaurant_image.jpg");
    }

    /**
     * Get a list of images for a Room (with the main image on index 0).
     * @param roomCode the code of the room
     * @return list of strings which are urls to the images
     */
    public static List<String> getRoomImageUrl(String roomCode) {
        return getImagesUrl("/rooms/image/getUrl/" + roomCode, "images/RoomTempIMG.jpg");
    }

    private static String deleteImageByUrl(String imageUrl, String defaultPath, String deleteUrl) {
        String imageId = imageUrl.substring(defaultPath.length());
        return ServerCommunication.authenticatedDeleteRequest(deleteUrl + imageId).getBody();
    }

    /**
     * Delete the selected image from the room.
     * @param imageUrl the url of the image you want to delete
     * @return "200 OK" / "400 BAD_REQUEST"
     */
    public static String deleteRoomImage(String imageUrl) {
        if (!imageUrl.equals("images/RoomTempIMG.jpg")) {
            return deleteImageByUrl(imageUrl,
                    SERVER_URL + "/rooms/image/downloadFile/",
                    "/rooms/image/");
        }
        return "400 BAD_REQUEST";
    }

    /**
     * Delete the selected image from the restaurant.
     * @param imageUrl the url of the image you want to delete
     * @return "200 OK" / "400 BAD_REQUEST"
     */
    public static String deleteRestaurantImage(String imageUrl) {
        if (!imageUrl.equals("images/restaurant_image.jpg")) {
            return deleteImageByUrl(imageUrl,
                    SERVER_URL + "/restaurants/image/downloadFile/",
                    "/restaurants/image/");
        }
        return "400 BAD_REQUEST";
    }

}
