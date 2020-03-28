package nl.tudelft.oopp.demo.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import nl.tudelft.oopp.demo.communication.RoomReservationCommunication;
import nl.tudelft.oopp.demo.entities.RoomReservation;
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
        if (choiceBox.getValue().toString().equals("Show by user")) {
            try {
                Integer.parseInt(userOrRoomField.getText());
            } catch (Exception e) {
                selectUserOrRoom.setText("Please input a valid userID number");
                return;
            }
        }

        loadReservations(choiceBox.getValue().toString());
    }

    private void addAppBar() {
        mainBox.getChildren().add(0, new AppBar());
    }

    private void loadReservations(String choice) {
        List<RoomReservation> reservations = new ArrayList<>();
        if (choice.equals("Show by user")) {
            reservations = RoomReservationCommunication
                    .getAllReservationsForUser(Integer.parseInt(userOrRoomField.getText()));
        } else if (choice.equals("Show by room")) {
            reservations = RoomReservationCommunication
                    .getAllReservationsForRoom(userOrRoomField.getText());
        } else {
            reservations = RoomReservationCommunication.getAllReservations();
        }

        reservationsList.getChildren().clear();
        anchorPaneReservations.setPrefHeight(70 * reservations.size());

        for (int i = 0; i < reservations.size(); i++) {
            HBox reservation = new HBox();
            reservation.setMaxWidth(1011);
            Label text = new Label("Reservation ID: " + reservations.get(i).getId() + " | "
                    + "User: " + reservations.get(i).getId() + " | "
                    + "Room: " + reservations.get(i).getRoom() + " | "
                    + "TimeSlot: " + reservations.get(i).getTimeSlot());
            text.setPrefSize(900, 60);
            text.setStyle("-fx-font: 17 arial;");

            int finalI = i;
            Button delete = new Button("delete");
            List<RoomReservation> finalReservations = reservations;
            delete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Integer id = finalReservations.get(finalI).getId();
                    RoomReservationCommunication.deleteReservationFromDatabase(id);
                    loadReservations(choiceBox.getValue().toString());
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
        assert reservationIdField != null : "fx:id=\"reservationIdField\" "
                + "was not injected: check your FXML file 'AdminSceneReservations.fxml'.";
        assert reservationsList != null : "fx:id=\"reservationsList\" "
                + "was not injected: check your FXML file 'AdminSceneReservations.fxml'.";

        addAppBar();
        choiceBox.setItems(FXCollections.observableArrayList(
                "Show all", "Show by user", "Show by room"
        ));
        choiceBox.getSelectionModel().selectFirst();
        loadReservations("Show all");
        choiceBox.getSelectionModel().selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<?
                            extends Number> observableValue, Number num, Number num2) {
                        String choice = (String)choiceBox.getItems().get((Integer) num2);
                        if (choice.equals("Show by user")) {
                            selectUserOrRoom.setText("Please enter the desired userID");
                        } else if (choice.equals("Show by room")) {
                            selectUserOrRoom.setText("Please enter the desired room name");
                        }
                        if (!choice.equals("Show all")) {
                            userOrRoomField.setVisible(true);
                            userOrRoomField.setText("");
                            ok.setVisible(true);
                        } else {
                            selectUserOrRoom.setText("");
                            userOrRoomField.setVisible(false);
                            ok.setVisible(false);
                            loadReservations(choice);
                        }
                    }
                });
    }

}
