package nl.tudelft.oopp.demo.communication;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImageCommunication {

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

    public static String getImageUrlFromId(String id) {
        String url = "/images/getUrl/" + id;
        try {
            return ServerCommunication.authenticatedRequest(url).getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
