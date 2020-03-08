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

    /**
     * Login call for a user.
     * @param username the username of that user
     * @param password the encoded password
     */
    public static void login(String username, String password) {
        if (authenticationHeader == null) {
            authenticationHeader = createHeaders(username, password);
            System.out.println("Headers created: " + authenticationHeader.toString());
        }
    }

    /**
     * Authenticates a request for the current logged in user.
     * @param link the URL the request was made to
     * @return response from the server
     * @throws Exception ///TODO
     */
    public static ResponseEntity<String> authenticatedRequest(String link) throws Exception {
        // TODO: throw exception when authentication fails
        ResponseEntity<String> response;
        HttpEntity request = new HttpEntity(authenticationHeader);

        response = new RestTemplate()
               .exchange(SERVER_URL + link, HttpMethod.GET, request, String.class);
        System.out.println("Made request to: " + link);
        return response;
    }

    /**
     * Creates headers for the authenticated user to make requests.
     * @param username the username of the User
     * @param password the password of the User
     * @return the Headers
     */
    public static HttpHeaders createHeaders(String username, String password) {
        String auth = username + ":" + password;

        String encodedauth = Base64.getEncoder().encodeToString(auth.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + encodedauth);
        return headers;

    }
}
