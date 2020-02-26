package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.communication.ServerCommunication;

public class AdminSceneController implements Initializable {

    @FXML
    private ChoiceBox<String> bikeBuildings;

    @FXML
    private Text numberBikes;

    @FXML
    private Button modifyBuildingsEnter;

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDataBikes();
    }


    private void loadDataBikes() {
        if (bikeBuildings != null) {
            bikeBuildings.getItems().addAll(BuildingCommunication.getBuildingsCodeAndName());
            SingleSelectionModel<String> selectionModel = bikeBuildings.getSelectionModel();
            selectionModel.selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable,
                                    String oldValue,
                                    String newValue) {
                    updateNumberBikes();
                }
            });
        }
    }


    private int updateNumberBikes() {
        String building = bikeBuildings.getValue();
        // Relay to the backend what the building is and
        // retrieve a number of bikes and store it in int bikes
        int bikes = 5;
        numberBikes.setText(Integer.toString(bikes));
        // return bikes;
        return bikes;
    }

    /**
     * Add a bike to the database.
     */
    @FXML
    private void addBike() {
        if (bikeBuildings.getValue() != null) {
            int bikes = updateNumberBikes();
            bikes++;
            //send the new bikes to the backend
            updateNumberBikes();
        }
    }
    /**
     * Remove a bike from the database.
     */
    @FXML
    private void removeBike() {
        if (bikeBuildings.getValue() != null) {
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



}
