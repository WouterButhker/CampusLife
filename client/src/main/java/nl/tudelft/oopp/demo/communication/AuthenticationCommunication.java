package nl.tudelft.oopp.demo.communication;

import static nl.tudelft.oopp.demo.communication.ServerCommunication.SERVER_URL;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import nl.tudelft.oopp.demo.entities.UserDtO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.client.RestTemplate;


public class AuthenticationCommunication {
    private static HttpHeaders authenticationHeader;
    private static RestTemplate template = new RestTemplate();

    /**
     * uses credentials to create a authenticated header that is saved for future requests.
     * makes a request to /login to retrieve the user role.
     * @param username the username for the authentication
     * @param password the password for the authentication
     */
    public static void login(String username, String password) {
        //TODO: check if credentials are correct
        // make request to /login to retrieve user role
        authenticationHeader = createHeaders(username, password);

    }

    /**
     * Makes a post request to the server to create a new user in the database.
     * @param user the user to be added to the database
     * @return the server response
     */
    public static ResponseEntity<String> register(UserDtO user) {

        String url = SERVER_URL + "/register";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserDtO> request = new HttpEntity<>(user, headers);

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        template.setMessageConverters(messageConverters);

        ResponseEntity<String> out = null;
        try {
            out = template.exchange(url, HttpMethod.POST, request, String.class);
            System.out.println(out);
        } catch (Exception e) {
            System.out.println("ERROR!!! " + e.toString());
        }
        return out;
    }

    /**
     * makes a GET request with the saved user credentials to the specified link.
     * @param link the link to send a request to
     * @return the server response
     * @throws AuthenticationException gets thrown when the user credentials are not correct
     *                                  or user doesn't have the right permissions
     */
    public static ResponseEntity<String> authenticatedRequest(String link)
            throws AuthenticationException {
        // TODO: throw exception when authentication fails
        ResponseEntity<String> response;
        HttpEntity request = new HttpEntity(authenticationHeader);

        response = new RestTemplate()
                .exchange(SERVER_URL + link, HttpMethod.GET, request, String.class);
        System.out.println("Made request to: " + link);
        return response;
    }

    /**
     * makes a DELETE request with the saved user credentials to the specified link.
     * @param link the link to send a request to
     * @return the server response
     * @throws AuthenticationException gets thrown when the user credentials are not correct
     *                                  or user doesn't have the right permissions
     */
    public static ResponseEntity<String> authenticatedDeleteRequest(String link)
            throws AuthenticationException {
        // TODO: throw exception when authentication fails
        ResponseEntity<String> response;
        HttpEntity request = new HttpEntity(authenticationHeader);

        response = new RestTemplate()
                .exchange(SERVER_URL + link, HttpMethod.DELETE, request, String.class);
        System.out.println("Made DELETE request to: " + link);
        return response;
    }

    /**
     * makes a POST request with the saved user credentials to the specified link.
     * @param link the link to send a request to
     * @return the server response
     * @throws AuthenticationException gets thrown when the user credentials are not correct
     *                                  or user doesn't have the right permissions
     */
    public static ResponseEntity<String> authenticatedPostRequest(String link, Object body)
            throws AuthenticationException {
        // TODO: throw exception when authentication fails
        ResponseEntity<String> response;
        HttpEntity request = new HttpEntity(body, authenticationHeader);

        response = new RestTemplate()
                .exchange(SERVER_URL + link, HttpMethod.POST, request, String.class);
        System.out.println("Made request to: " + link);
        return response;
    }

    private static HttpHeaders createHeaders(String username, String password) {
        String auth = username + ":" + password;

        String encodedauth = Base64.getEncoder().encodeToString(auth.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + encodedauth);
        return headers;

    }
}
