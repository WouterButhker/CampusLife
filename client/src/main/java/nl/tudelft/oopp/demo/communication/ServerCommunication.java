package nl.tudelft.oopp.demo.communication;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
