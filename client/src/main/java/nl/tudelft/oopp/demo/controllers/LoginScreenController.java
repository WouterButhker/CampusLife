package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import nl.tudelft.oopp.demo.communication.AuthenticationCommunication;
import nl.tudelft.oopp.demo.core.RoutingScene;
import nl.tudelft.oopp.demo.core.XmlRoute;
import nl.tudelft.oopp.demo.views.MainMenuRoute;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;


public class LoginScreenController {



    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Hyperlink registerLink;

    @FXML
    private TextField usernameField;


    @FXML
    void onLoginClicked(ActionEvent event) {
        try {
            AuthenticationCommunication.login(usernameField.getText(), passwordField.getText());
            RoutingScene routingScene = (RoutingScene) passwordField.getScene();
            routingScene.pushRoute(new MainMenuRoute());
        } catch (HttpClientErrorException e) {
            System.out.println("login failed: " + e.getStatusCode());
        } catch (ResourceAccessException e) {
            System.out.println("Cant access server");
        }

    }

    @FXML
    void onRegisterClicked(ActionEvent event) {
        RoutingScene routingScene = (RoutingScene) passwordField.getScene();

        try {
            URL xmlUrl = getClass().getResource("/RegisterScreen.fxml");
            routingScene.pushRoute(new XmlRoute(xmlUrl));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void initialize() {
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: "
                + "check your FXML file 'LoginScreen.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: "
                + "check your FXML file 'LoginScreen.fxml'.";
        assert registerLink != null : "fx:id=\"registerLink\" was not injected: "
                + "check your FXML file 'LoginScreen.fxml'.";
        assert usernameField != null : "fx:id=\"usernameField\" was not injected: "
                + "check your FXML file 'LoginScreen.fxml'.";
    }

}
