package nl.tudelft.oopp.demo.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class RegisterScreenController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField reEnterPasswordField;

    @FXML
    private Button registerButton;

    @FXML
    private TextField usernameField;


    @FXML
    void onBackClicked(ActionEvent event) {
    }

    @FXML
    void onRegisterClicked(ActionEvent event) {
    }

    @FXML
    void initialize() {
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'RegisterScreen.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'RegisterScreen.fxml'.";
        assert reEnterPasswordField != null : "fx:id=\"reEnterPasswordField\" was not injected: check your FXML file 'RegisterScreen.fxml'.";
        assert registerButton != null : "fx:id=\"registerButton\" was not injected: check your FXML file 'RegisterScreen.fxml'.";
        assert usernameField != null : "fx:id=\"usernameField\" was not injected: check your FXML file 'RegisterScreen.fxml'.";


    }

}
