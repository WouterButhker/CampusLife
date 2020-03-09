package nl.tudelft.oopp.demo.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import nl.tudelft.oopp.demo.communication.AuthenticationCommunication;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import nl.tudelft.oopp.demo.entities.UserDtO;
import nl.tudelft.oopp.demo.widgets.AppBar;
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
    private Text errorMessage;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField reEnterPasswordField;

    @FXML
    private Button registerButton;

    @FXML
    private TextField usernameField;

    @FXML
    private VBox mainBox;



    @FXML
    private void onRegisterClicked(ActionEvent event) {
        if (usernameField.getText().equals("")
                || passwordField.getText().equals("")
                || reEnterPasswordField.getText().equals("")) {
            errorMessage.setText("All fields must be filled in");
        } else if (!(passwordField.getText().equals(reEnterPasswordField.getText()))) {
            errorMessage.setText("Passwords do not match");
            passwordField.setText("");
            reEnterPasswordField.setText("");
        } else {
            errorMessage.setText("");
            register(usernameField.getText(), passwordField.getText());
        }
    }

    private static void register(String username, String password) {

        UserDtO user = new UserDtO(username, new BCryptPasswordEncoder().encode(password));

        ResponseEntity<String> response = AuthenticationCommunication.register(user);

        // TODO: create user friendly messages using response

    }

    private void addAppBar() {
        mainBox.getChildren().add(0, new AppBar());
    }

    @FXML
    void initialize() {
        assert passwordField != null : "fx:id=\"passwordField\" "
                + "was not injected: check your FXML file 'RegisterScreen.fxml'.";
        assert reEnterPasswordField != null : "fx:id=\"reEnterPasswordField\" "
                + "was not injected: check your FXML file 'RegisterScreen.fxml'.";
        assert registerButton != null : "fx:id=\"registerButton\" "
                + "was not injected: check your FXML file 'RegisterScreen.fxml'.";
        assert usernameField != null : "fx:id=\"usernameField\" "
                + "was not injected: check your FXML file 'RegisterScreen.fxml'.";

        addAppBar();
    }


}
