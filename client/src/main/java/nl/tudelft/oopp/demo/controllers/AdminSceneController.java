package nl.tudelft.oopp.demo.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminSceneController implements Initializable {

    @FXML
    private ChoiceBox<String> bikeBuildings;

    @FXML
    private Text numberBikes;

    @FXML
    private Button modifyBuildingsEnter;

    @FXML
    private Button modifyBuildingsExit;

    @FXML
    private Button modifyRoomsEnter;

    @FXML
    private Button modifyRoomsExit;

    @FXML
    private Button modifyFoodEnter;

    @FXML
    private Button modifyFoodExit;

    @FXML
    private Button modifyRightsEnter;

    @FXML
    private Button modifyRightsExit;

    @FXML
    private TextField locationInput;

    @FXML
    private TextField nameInput;

    @FXML
    private TextField buildingCodeInput;

    @FXML
    private TextField openingHoursInput;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDataBikes();
    }


    private void loadDataBikes() {
        if (bikeBuildings != null) {
            bikeBuildings.getItems().addAll(ServerCommunication.getBuildingsCodeAndName());
            bikeBuildings.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    updateNumberBikes();
                }
            });
        }

    }


    private int updateNumberBikes() {
        String building = bikeBuildings.getValue();
        //Relay to the backend what the building is and retrieve a number of bikes and store it in int bikes
        int bikes = 5;
        numberBikes.setText(Integer.toString(bikes));
       // return bikes;
        return bikes;
    }

    public void addBike() {
        if(bikeBuildings.getValue() != null) {
            int bikes = updateNumberBikes();
            bikes++;
            //send the new bikes to the backend
            updateNumberBikes();
        }
    }

    public void removeBike() {
        if(bikeBuildings.getValue() != null) {
            int bikes = updateNumberBikes();
            bikes--;
            //send the new bikes to the backend
            updateNumberBikes();
        }
    }

    @FXML
    private void modifyBuildingsEnter() throws IOException {
        Stage stage = (Stage) modifyBuildingsEnter.getScene().getWindow();
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/AdminSceneBuildings.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void modifyBuildingsExit() throws IOException {
        Stage stage = (Stage) modifyBuildingsExit.getScene().getWindow();
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/AdminScene.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void modifyRoomsEnter() throws IOException {
        Stage stage = (Stage) modifyRoomsEnter.getScene().getWindow();
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/AdminSceneRooms.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
    private void modifyFoodEnter() throws IOException {
        Stage stage = (Stage) modifyFoodEnter.getScene().getWindow();
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/AdminSceneFood.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void modifyFoodExit() throws IOException {
        Stage stage = (Stage) modifyFoodExit.getScene().getWindow();
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/AdminScene.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void modifyRightsEnter() throws IOException {
        Stage stage = (Stage) modifyRightsEnter.getScene().getWindow();
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/AdminSceneRights.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void modifyRightsExit() throws IOException {
        Stage stage = (Stage) modifyRightsExit.getScene().getWindow();
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/AdminScene.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void submitNewBuilding() {
        String location = locationInput.getText();
        String name = nameInput.getText();
        boolean codeFound = false;
        int buildingCode = 0;
        try {
            buildingCode = Integer.parseInt(buildingCodeInput.getText());
            codeFound = true;
        } catch (NumberFormatException e) {

        }
        //String openingHours = openingHoursInput.getText();
        String openingHours = "08:00-22:00";
        if (location != null && name != null && codeFound && openingHours != null) {
            System.out.println(buildingCode + name + location + openingHours);
           // ServerCommunication.addBuildingToDatabase(buildingCode, name, location, openingHours);
        } else {
            System.out.println("input is wrong/not all fields are entered");
        }
    }
}
