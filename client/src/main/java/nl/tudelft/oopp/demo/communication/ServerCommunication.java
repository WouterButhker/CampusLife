package nl.tudelft.oopp.demo.communication;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.client.HttpClientErrorException;

public class ServerCommunication {

    public static final String SERVER_URL = "http://localhost:8080";

    public static ResponseEntity<String> authenticatedRequest(String link)
            throws HttpClientErrorException {
        return AuthenticationCommunication.authenticatedRequest(link);
    }

    public static ResponseEntity<String> authenticatedPostRequest(String link, Object body)
            throws AuthenticationException {
        return AuthenticationCommunication.authenticatedPostRequest(link, body);
    }

    public static ResponseEntity<String> authenticatedPutRequest(String link, Object body)
            throws AuthenticationException {
        return AuthenticationCommunication.authenticatedPutRequest(link, body);
    }

    public static ResponseEntity<String> authenticatedDeleteRequest(String link)
            throws AuthenticationException {
        return AuthenticationCommunication.authenticatedDeleteRequest(link);
    }



}
