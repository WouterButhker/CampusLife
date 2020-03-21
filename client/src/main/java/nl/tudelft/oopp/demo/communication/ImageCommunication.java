package nl.tudelft.oopp.demo.communication;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImageCommunication {

    /**
     * Get a List of all the image ids in the database.
     * @return List of Strings which represent Image ids
     */
    /*
    public static List<String> getAllImageIds() {
        String url = "/images/allIds";
        try {
            String[] response = ServerCommunication.authenticatedRequest(url).getBody()
                    .replace("[", "")
                    .replace("]", "")
                    .replace("\"", "")
                    .split(",");
            return Arrays.asList(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

     */

    /**
     * Get the URL of an image from a given image id.
     * @param id the id of the image you want
     * @return an URL to the Image you are looking for
     */
    /*
    public static String getImageUrlFromId(String id) {
        String url = "/images/getUrl/" + id;
        try {
            return ServerCommunication.authenticatedRequest(url).getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    */
}
