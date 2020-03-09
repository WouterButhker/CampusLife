package nl.tudelft.oopp.demo.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import nl.tudelft.oopp.demo.widgets.AppBar;


public class AdminSceneReservationsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchorPaneReservations;

    @FXML
    private Button deleteReservationButton;

    @FXML
    private VBox mainBox;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TextField reservationIdField;

    @FXML
    private VBox reservationsList;


    @FXML
    private void onDeleteClicked(ActionEvent event) {
    }

    private void addAppBar() {
        mainBox.getChildren().add(0, new AppBar());
    }

    private void loadReservations() {
        /*
        for(int i =0; i<10; i++) {

        }
         */
    }

    @FXML
    private void initialize() {
        assert anchorPaneReservations != null : "fx:id=\"anchorPaneReservations\" "
                + "was not injected: check your FXML file 'AdminSceneReservations.fxml'.";
        assert deleteReservationButton != null : "fx:id=\"deleteReservationButton\" "
                + "was not injected: check your FXML file 'AdminSceneReservations.fxml'.";
        assert mainBox != null : "fx:id=\"mainBox\" was not injected: "
                + "check your FXML file 'AdminSceneReservations.fxml'.";
        assert mainPane != null : "fx:id=\"mainPane\" was not injected: "
                + "check your FXML file 'AdminSceneReservations.fxml'.";
        assert reservationIdField != null : "fx:id=\"reservationIdField\" "
                + "was not injected: check your FXML file 'AdminSceneReservations.fxml'.";
        assert reservationsList != null : "fx:id=\"reservationsList\" "
                + "was not injected: check your FXML file 'AdminSceneReservations.fxml'.";

        addAppBar();
        loadReservations();
    }

}
