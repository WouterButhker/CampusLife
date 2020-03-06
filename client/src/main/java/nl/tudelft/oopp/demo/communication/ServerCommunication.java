package nl.tudelft.oopp.demo.communication;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;

public class ServerCommunication {

    public static final String SERVER_URL = "http://localhost:8080";


    public static ResponseEntity<String> authenticatedRequest(String link) throws AuthenticationException {
        return AuthenticationCommunication.authenticatedRequest(link);
    }

}
