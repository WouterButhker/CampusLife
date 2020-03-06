package nl.tudelft.oopp.demo.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import nl.tudelft.oopp.demo.communication.AuthenticationCommunication;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.UserDTO;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class RegisterScreenController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button okButtonPassword1;

    @FXML
    private Button okButtonPassword2;

    @FXML
    private Button okButtonUsername;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Pane popupPassword1;

    @FXML
    private Pane popupPassword2;

    @FXML
    private Pane popupUsername;

    @FXML
    private PasswordField reEnterPasswordField;

    @FXML
    private Button registerButton;

    @FXML
    private TextField usernameField;

    @FXML
    void onOkPassword1Clicked(ActionEvent event) {
        popupPassword1.setVisible(false);
        passwordField.setText("");
        reEnterPasswordField.setText("");
    }

    @FXML
    void onOkPassword2Clicked(ActionEvent event) {
        popupPassword2.setVisible(false);
    }

    @FXML
    void onOkUsernameClicked(ActionEvent event) {
        popupUsername.setVisible(false);
    }

    @FXML
    void onRegisterClicked(ActionEvent event) {
        if (usernameField.getText().equals("")) {
            popupUsername.setVisible(true);
        } else if (passwordField.getText().equals("")) {
            popupPassword2.setVisible(true);
        } else if (!(passwordField.getText().equals(reEnterPasswordField.getText()))) {
            popupPassword1.setVisible(true);
        } else {
            register(usernameField.getText(), passwordField.getText());
        }
    }

    private static void register(String username, String password) {

        UserDTO user = new UserDTO(username, new BCryptPasswordEncoder().encode(password));

        ResponseEntity<String> response = AuthenticationCommunication.register(user);

        // TODO: create user friendly messages using response

    }

    @FXML
    void initialize() {
        assert okButtonPassword1 != null : "fx:id=\"okButtonPassword1\" "
                + "was not injected: check your FXML file 'RegisterScreen.fxml'.";
        assert okButtonPassword2 != null : "fx:id=\"okButtonPassword2\" "
                + "was not injected: check your FXML file 'RegisterScreen.fxml'.";
        assert okButtonUsername != null : "fx:id=\"okButtonUsername\" "
                + "was not injected: check your FXML file 'RegisterScreen.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" "
                + "was not injected: check your FXML file 'RegisterScreen.fxml'.";
        assert popupPassword1 != null : "fx:id=\"popupPassword1\" "
                + "was not injected: check your FXML file 'RegisterScreen.fxml'.";
        assert popupPassword2 != null : "fx:id=\"popupPassword2\" "
                + "was not injected: check your FXML file 'RegisterScreen.fxml'.";
        assert popupUsername != null : "fx:id=\"popupUsername\" "
                + "was not injected: check your FXML file 'RegisterScreen.fxml'.";
        assert reEnterPasswordField != null : "fx:id=\"reEnterPasswordField\" "
                + "was not injected: check your FXML file 'RegisterScreen.fxml'.";
        assert registerButton != null : "fx:id=\"registerButton\" "
                + "was not injected: check your FXML file 'RegisterScreen.fxml'.";
        assert usernameField != null : "fx:id=\"usernameField\" "
                + "was not injected: check your FXML file 'RegisterScreen.fxml'.";


    }


}
