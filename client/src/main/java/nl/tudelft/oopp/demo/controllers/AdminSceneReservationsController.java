package nl.tudelft.oopp.demo.controllers;

import static nl.tudelft.oopp.demo.communication.ReservationCommunication.deleteReservationFromDatabase;
import static nl.tudelft.oopp.demo.communication.ReservationCommunication.getAllReservations;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
    private Button ok;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TextField reservationIdField;

    @FXML
    private TextField userOrRoomField;

    @FXML
    private Text selectUserOrRoom;

    @FXML
    private VBox reservationsList;

    @FXML
    private ChoiceBox choiceBox;


    @FXML
    void onOkClicked(ActionEvent event) {
        selectUserOrRoom.setText("");
        userOrRoomField.setVisible(false);
        ok.setVisible(false);

        loadReservations();
    }

    private void addAppBar() {
        mainBox.getChildren().add(0, new AppBar());
    }

    private List<Reservation> getReservations() {
        String choice = choiceBox.getValue().toString();
        List<Reservation> reservations = getAllReservations();
        if (choice.equals("Show all")) {
            return reservations;
        } else if (choice.equals("Show by user")) {
            List<Reservation> res = new ArrayList<Reservation>();
            for (int i = 0; i < reservations.size(); i++) {
                if (reservations.get(i).getUser().equals(userOrRoomField)) {
                    res.add(reservations.get(i));
                }
            }
            return res;
        } else {
            List<Reservation> res = new ArrayList<Reservation>();
            for (int i = 0; i < reservations.size(); i++) {
                if (reservations.get(i).getRoom().equals(userOrRoomField)) {
                    res.add(reservations.get(i));
                }
            }
            return res;
        }
    }

    private void loadReservations() {
        List<Reservation> reservations = getReservations();

        reservationsList.getChildren().clear();
        anchorPaneReservations.setPrefHeight(70 * reservations.size());

        for (int i = 0; i < reservations.size(); i++) {
            HBox reservation = new HBox();
            reservation.setMaxWidth(1011);
            Label text = new Label("Reservation ID: " + reservations.get(i).getId() + " | "
                    + "User: " + reservations.get(i).getUser() + " | "
                    + "Room: " + reservations.get(i).getRoom() + " | "
                    + "TimeSlot: " + reservations.get(i).getTimeSlot());
            text.setPrefSize(900, 60);
            text.setStyle("-fx-font: 17 arial;");

            int finalI = i;
            Button delete = new Button("delete");
            delete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Integer id = reservations.get(finalI).getId();
                    deleteReservationFromDatabase(id);
                    loadReservations();
                }
            });

            reservation.setPadding(new Insets(5, 5, 5,5));
            String css = "-fx-border-color: black;\n"
                    + "-fx-border-insets: 4\n;"
                    + "-fx-border-style: solid\n;"
                    + "-fx-border-width: 1;"
                    + "-fx-border-radius: 10;";
            reservation.setStyle(css);
            reservation.getChildren().addAll(text, delete);
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
        choiceBox.setItems(FXCollections.observableArrayList(
                "Show all", "Show by user", "Show by room"
        ));
        loadReservations();
    }

}
