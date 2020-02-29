package nl.tudelft.oopp.demo.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import nl.tudelft.oopp.demo.communication.ServerCommunication;

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
        String username = usernameField.getText();
        String password = passwordField.getText();
        login();
//        if (!ServerCommunication.authenticate(username, password)) {
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Error");
//            alert.setHeaderText(null);
//            alert.setContentText("FAILED");
//            alert.showAndWait();
//        };

        ServerCommunication.getAdmin(username, password);
    }

    @FXML
    void onRegisterClicked(ActionEvent event) {
    }

    @FXML
    void initialize() {
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'LoginScreen.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'LoginScreen.fxml'.";
        assert registerLink != null : "fx:id=\"registerLink\" was not injected: check your FXML file 'LoginScreen.fxml'.";
        assert usernameField != null : "fx:id=\"usernameField\" was not injected: check your FXML file 'LoginScreen.fxml'.";


    }

    @FXML
    private void login() {
        //Authentication authToken = new UsernamePasswordAuthenticationToken(usernameField.getText(), passwordField.getText());


    }

}
