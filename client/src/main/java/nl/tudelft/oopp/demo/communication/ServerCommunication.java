package nl.tudelft.oopp.demo.communication;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

public class ServerCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();
    private static final String SERVER_URL = "http://localhost:8080";
    private static PasswordEncoder encoder = passwordEncoder();

    /**
     * Retrieves a quote from the server.
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */
    public static String getQuote() {
        HttpRequest request = HttpRequest.newBuilder().GET()
                .uri(URI.create(SERVER_URL + "/quote")).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return "Communication with server failed";
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }
        return response.body();
    }

    /**
     * Retrieves all the buildings codes and names from the database and returns an array.
     * @return array of all the buildings, format: "code name"
     */
    public static String[] getBuildingsCodeAndName() {
        URI myUri = URI.create(SERVER_URL + "/buildings/code+name");
        HttpRequest request = HttpRequest.newBuilder().GET().uri(myUri).build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseString = response.body();
            responseString = responseString.replace("[", "");
            responseString = responseString.replace("]", "");
            responseString = responseString.replace("\"", "");
            return responseString.split(",");
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void auth() {

    }

    /**
     * For adding buildings to the database.
     * @param buildingCode the number of the building
     * @param name the name of the building
     * @param location the street where the building is situated
     * @param openingHours time it is open with format hh:mm-hh:mm
     */
    public static void addBuildingToDatabase(Integer buildingCode,
                                             String name,
                                             String location,
                                             String openingHours) {
        name = name.replace(" ", "%20");
        location = location.replace(" ", "%20");
        openingHours = openingHours.replace(" ", "%20");
        URI myUri = URI.create(SERVER_URL + "/buildings/add?buildingCode=" + buildingCode
                + "&name=" + name + "&location=" + location + "&openingHours=" + openingHours);
        HttpRequest request = HttpRequest.newBuilder().GET().uri(myUri).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean authenticate(String user, String pass) {
        // TODO: ENCRYPT PASSWORDS

        //URI uri = URI.create(SERVER_URL + "/login/login?user=" + user + "&pass=" + pass);
        URI uri = URI.create(SERVER_URL + "/login");
        HttpRequest request = HttpRequest.newBuilder().GET().uri(uri).build();
        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response: " + response.body());
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return Boolean.parseBoolean(response.toString());
    }

    private static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Sends a basic (authenticated) get request to /admin and prints the response.
     *
     * @param user username
     * @param pass password
     *
     */
    public static void getAdmin(String user, String pass) {
        //pass = passwordEncoder().encode(pass);
        //URI uri = URI.create(SERVER_URL + "/login");
        //HttpRequest request = HttpRequest.newBuilder().GET().uri(uri).build();

        HttpEntity request = new HttpEntity(createHeaders(user, pass));
        ResponseEntity<String> response;
        try {
            response = new RestTemplate()
                    .exchange(SERVER_URL + "/admin", HttpMethod.GET, request, String.class);
            System.out.println(response.getBody());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private static HttpHeaders createHeaders(String username, String password) {
        String auth = username + ":" + password;
        System.out.println(auth);
        String encodedauth = Base64.getEncoder().encodeToString(auth.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + encodedauth);
        System.out.println(headers.toString());
        return headers;

    }
}
