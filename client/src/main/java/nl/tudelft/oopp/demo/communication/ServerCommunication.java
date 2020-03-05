package nl.tudelft.oopp.demo.communication;

import java.util.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ServerCommunication {

    public static final String SERVER_URL = "http://localhost:8080";
    private static HttpHeaders authenticationHeader;

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

    public static void login(String username, String password) {
        if (authenticationHeader == null) {
            authenticationHeader = createHeaders(username, password);
            System.out.println("Headers created: " + authenticationHeader.toString());
        }
    }

    public static ResponseEntity<String> authenticatedRequest(String link) throws Exception {
        // TODO: throw exception when authentication fails
        ResponseEntity<String> response;
        HttpEntity request = new HttpEntity(authenticationHeader);

        response = new RestTemplate()
               .exchange(SERVER_URL + link, HttpMethod.GET, request, String.class);
        System.out.println("Made request to: " + link);
        return response;
    }

    public static HttpHeaders createHeaders(String username, String password) {
        String auth = username + ":" + password;

        String encodedauth = Base64.getEncoder().encodeToString(auth.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + encodedauth);
        return headers;

    }
}
