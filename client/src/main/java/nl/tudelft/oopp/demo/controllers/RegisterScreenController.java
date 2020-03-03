package nl.tudelft.oopp.demo.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;


public class RegisterScreenController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button okButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Pane popup;

    @FXML
    private PasswordField reEnterPasswordField;

    @FXML
    private Button registerButton;

    @FXML
    private TextField usernameField;

    @FXML
    void onOkClicked(ActionEvent event) {
        popup.setVisible(false);
    }

    @FXML
    void onRegisterClicked(ActionEvent event) {
        if(!(passwordField.getText().equals(reEnterPasswordField.getText()))) {
            popup.setVisible(true);
        }
    }


    @FXML
    void initialize() {
        assert okButton != null : "fx:id=\"okButton\" was not injected: check your FXML file 'RegisterScreen.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'RegisterScreen.fxml'.";
        assert popup != null : "fx:id=\"popup\" was not injected: check your FXML file 'RegisterScreen.fxml'.";
        assert reEnterPasswordField != null : "fx:id=\"reEnterPasswordField\" was not injected: check your FXML file 'RegisterScreen.fxml'.";
        assert registerButton != null : "fx:id=\"registerButton\" was not injected: check your FXML file 'RegisterScreen.fxml'.";
        assert usernameField != null : "fx:id=\"usernameField\" was not injected: check your FXML file 'RegisterScreen.fxml'.";


    }

}
