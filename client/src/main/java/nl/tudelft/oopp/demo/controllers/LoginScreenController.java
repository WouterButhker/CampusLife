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
import nl.tudelft.oopp.demo.core.RoutingScene;
import nl.tudelft.oopp.demo.core.XmlRoute;
import nl.tudelft.oopp.demo.views.MainMenuRoute;
import nl.tudelft.oopp.demo.views.RoomsListRoute;


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
        RoutingScene routingScene = (RoutingScene) passwordField.getScene();
        routingScene.pushRoute(new MainMenuRoute());
    }

    @FXML
    void onRegisterClicked(ActionEvent event) {
        RoutingScene routingScene = (RoutingScene) passwordField.getScene();
        /*
        try {
            URL xmlUrl = getClass().getResource("/AdminScene.fxml");
            routingScene.pushRoute(new XmlRoute(xmlUrl));
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
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
