package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.core.Route;
import nl.tudelft.oopp.demo.core.RoutingScene;
import nl.tudelft.oopp.demo.core.XmlRoute;
import nl.tudelft.oopp.demo.widgets.AppBar;


public class AdminSceneController implements Initializable {

    @FXML
    private VBox mainBox;

    @FXML
    private ChoiceBox<String> bikeBuildings;

    @FXML
    private Text numberBikes;

    @FXML
    private Button modifyBuildingsEnter;

    @FXML
    private Button modifyRoomsEnter;

    @FXML
    private Button modifyFoodEnter;

    @FXML
    private Button modifyRightsEnter;

    @FXML
    private Button modifyReservationsEnter;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDataBikes();
        addAppBar();
    }

    private void addAppBar() {
        mainBox.getChildren().add(0, new AppBar());
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
        RoutingScene scene = (RoutingScene) modifyBuildingsEnter.getScene();
        Route route = new XmlRoute(getClass().getResource("/AdminSceneBuildings.fxml"));
        scene.pushRoute(route);
    }

    @FXML
    private void modifyRoomsEnter() throws IOException {
        RoutingScene scene = (RoutingScene) modifyRoomsEnter.getScene();
        Route route = new XmlRoute(getClass().getResource("/AdminSceneRooms.fxml"));
        scene.pushRoute(route);
    }

    @FXML
    private void modifyFoodEnter() throws IOException {
        RoutingScene scene = (RoutingScene) modifyFoodEnter.getScene();
        Route route = new XmlRoute(getClass().getResource("/AdminSceneFood.fxml"));
        scene.pushRoute(route);
    }

    @FXML
    private void modifyRightsEnter() throws IOException {
        RoutingScene scene = (RoutingScene) modifyRightsEnter.getScene();
        Route route = new XmlRoute(getClass().getResource("/AdminSceneRights.fxml"));
        scene.pushRoute(route);
    }

    @FXML
    private void modifyReservationsEnter() throws IOException {
        RoutingScene scene = (RoutingScene) modifyReservationsEnter.getScene();
        Route route = new XmlRoute(getClass().getResource("/AdminSceneReservations.fxml"));
        scene.pushRoute(route);
    }


}
