package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.communication.RoomCommunication;

public class AdminSceneRoomsController implements Initializable {

    @FXML
    private Button modifyRoomsExit;

    @FXML
    private TextField roomCodeInput;

    @FXML
    private TextField roomNameInput;

    @FXML
    private TextField capacityInput;

    @FXML
    private ChoiceBox<String> buildingList;

    @FXML
    private ChoiceBox<String> rightsList;

    @FXML
    private CheckBox whiteboardBox;

    @FXML
    private CheckBox tvBox;

    @FXML
    private Button submit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadBuildings();
        loadRights();
    }

    private void loadBuildings() {
        if (buildingList != null) {
           buildingList.getItems().addAll(BuildingCommunication.getBuildingsCodeAndName());
        }
    }

    private void loadRights() {
        if (rightsList != null) {
           String[] rights = new String[3];
           rights[0] = "Student";
           rights[1] = "Employee";
           rights[2] = "Admin";
           rightsList.getItems().addAll(rights);
        }
    }

    @FXML
    private void modifyRoomsExit() throws IOException {
        Stage stage = (Stage) modifyRoomsExit.getScene().getWindow();
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/AdminScene.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void submitNewRoom() {
        String roomCode = roomCodeInput.getText();
        String roomName = roomNameInput.getText();
        int capacity = 0;
        boolean capacityCorrect = false;
        try {
            capacity = Integer.parseInt(capacityInput.getText().trim());
            capacityCorrect = true;
        } catch (NumberFormatException e) {
            System.out.println("Not a proper number");
        }

        boolean buildingFound = false;
        int buildingCode = 0;
        try {
            buildingCode = Integer.parseInt(buildingList.getValue().split("")[0]);
            buildingFound = true;
        } catch (NumberFormatException e) {
            System.out.println("No building selected");
        }

        String rightsString = rightsList.getValue();
        int rights = 0;
        boolean rightsFound = false;
        //This if statement allows to add more levels of rights in the long term
        if (!rightsString.equals("")) {
            rightsFound = true;
            if (rightsString.equals("Student")) {
                rights = 0;
            }
            if (rightsString.equals("Employee")) {
                rights = 1;
            }
            if (rightsString.equals("Admin")) {
                rights = 2;
            }
        } else {
            System.out.println("No rights selected");
        }

        boolean whiteboard = whiteboardBox.selectedProperty().get();
        boolean tv = tvBox.selectedProperty().get();

        Text submitStatus = new Text();
        if (!roomCode.equals("") && !roomName.equals("") && capacityCorrect && buildingFound && rightsFound) {
            RoomCommunication.addRoomToDatabase(roomCode, roomName, capacity, whiteboard, tv, rights, buildingCode);
            submitStatus.setText("Room has been successfully added to " + buildingList.getValue().split("")[1]);
            try {
                refreshRoomsPage();
            } catch (IOException e) {
                System.out.println("Refresh failed");
            }
        } else {
            submitStatus.setText("All fields have to be entered");
        }

        if (!capacityCorrect) {
            submitStatus.setText("The capacity has to be a proper number");
        }

        if (!buildingFound) {
            submitStatus.setText("Please select a building");
        }

        if (!rightsFound) {
            submitStatus.setText("Rights have to be set");
        }

        Button back = new Button("Okay! take me back");
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Button button = (Button) event.getSource();
                Stage stage = (Stage) button.getScene().getWindow();
                stage.close();
            }
        });
        VBox roomBox = new VBox(submitStatus, back);
        roomBox.setPrefSize(300, 200);
        roomBox.setAlignment(Pos.CENTER);
        AnchorPane root = new AnchorPane(roomBox);
        root.setPrefSize(300, 200);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(modifyRoomsExit.getScene().getWindow());
        stage.showAndWait();
    }

    @FXML
    private void refreshRoomsPage() throws IOException {
        Stage stage = (Stage) modifyRoomsExit.getScene().getWindow();
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/AdminSceneRooms.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
