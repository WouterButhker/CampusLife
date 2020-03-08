package nl.tudelft.oopp.demo.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.tudelft.oopp.demo.entities.Reservation;
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
        ArrayList<Reservation> reservations = new ArrayList<Reservation>();
        for(Integer i =0; i<10; i++) {
            String room = "room" + i;
            Reservation reservation = new Reservation(i, i, room, "12:30-14:30");
            reservations.add(reservation);
        }

        anchorPaneReservations.setPrefHeight(70 * reservations.size());

        for(int i = 0; i<reservations.size(); i++) {
            HBox reservation = new HBox();
            reservation.setMaxWidth(642);
            Label text = new Label("Reservation ID: " + reservations.get(i).getId() + " | "
                    + "User: " + reservations.get(i).getUser() + " | "
                    + "Room: " + reservations.get(i).getRoom() + " | "
                    + "TimeSlot: " + reservations.get(i).getTimeSlot());
            text.setPrefSize(642, 60);
            text.setStyle("-fx-font: 17 arial;");

            reservation.setPadding(new Insets(5, 5, 5,5));
            String css = "-fx-border-color: black;\n"
                    + "-fx-border-insets: 4\n;"
                    + "-fx-border-style: solid\n;"
                    + "-fx-border-width: 1;"
                    + "-fx-border-radius: 10;";
            reservation.setStyle(css);
            reservation.getChildren().add(text);
            reservationsList.getChildren().add(reservation);
        }

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
