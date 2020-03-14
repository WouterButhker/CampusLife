package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import nl.tudelft.oopp.demo.core.Route;
import nl.tudelft.oopp.demo.core.RoutingScene;
import nl.tudelft.oopp.demo.core.XmlRoute;
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
    private void onRegisterClicked(ActionEvent event) throws IOException {
        if (usernameField.getText().equals("")
                || passwordField.getText().equals("")
                || reEnterPasswordField.getText().equals("")) {
            errorMessage.setText("All fields must be filled in");
        } else if (!(passwordField.getText().equals(reEnterPasswordField.getText()))) {
            errorMessage.setText("Passwords do not match");
            passwordField.setText("");
            reEnterPasswordField.setText("");
        } else {
            String pass = passwordField.getText();
            boolean hasUppercase = !(pass.equals(pass.toLowerCase()));
            boolean hasLowercase = !(pass.equals(pass.toUpperCase()));
            boolean hasNumber = pass.matches(".*\\d.*");

            if (pass.length() < 8) {
                errorMessage.setText("Password must be at least 8 characters long");
                passwordField.setText("");
                reEnterPasswordField.setText("");
                return;
            }
            if (!(hasLowercase && hasUppercase)) {
                errorMessage.setText("Password must have at least one uppercase and one lowercase");
                passwordField.setText("");
                reEnterPasswordField.setText("");
                return;
            }
            if (!hasNumber) {
                errorMessage.setText("Password must have at least one number");
                passwordField.setText("");
                reEnterPasswordField.setText("");
                return;
            }

            Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(pass);
            boolean hasSpecialCharacter = m.find();

            if (!hasSpecialCharacter) {
                errorMessage.setText("Password must have at least one special character");
                passwordField.setText("");
                reEnterPasswordField.setText("");
                return;
            }

            errorMessage.setText("");
            register(usernameField.getText(), passwordField.getText());
            RoutingScene scene = (RoutingScene) passwordField.getScene();
            Route route = new XmlRoute(getClass().getResource("/LoginScreen.fxml"));
            scene.pushRoute(route);
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
