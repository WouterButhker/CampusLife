package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import nl.tudelft.oopp.demo.core.Route;
import nl.tudelft.oopp.demo.core.RoutingScene;
import nl.tudelft.oopp.demo.core.XmlRoute;
import nl.tudelft.oopp.demo.widgets.AppBar;

public class AdminSceneController implements Initializable {

    @FXML
    private VBox mainBox;

    @FXML
    private Button modifyBuildingsEnter;

    @FXML
    private Button modifyRoomsEnter;

    @FXML
    private Button modifyRestaurantEnter;

    @FXML
    private Button modifyFoodEnter;

    @FXML
    private Button modifyRightsEnter;

    @FXML
    private Button modifyReservationsEnter;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addAppBar();
        addStyle();
    }

    private void addStyle() {
        mainBox.getStylesheets().add("css/admin-scene.css");
        modifyBuildingsEnter.getStyleClass().add("adminButton");
        modifyRoomsEnter.getStyleClass().add("adminButton");
        modifyRestaurantEnter.getStyleClass().add("adminButton");
        modifyFoodEnter.getStyleClass().add("adminButton");
        modifyRightsEnter.getStyleClass().add("adminButton");
        modifyReservationsEnter.getStyleClass().add("adminButton");
        //mainBox.setStyle("-fx-background-color: -primary-color-light");
    }

    private void addAppBar() {
        mainBox.getChildren().add(0, new AppBar());
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
    private void modifyRestaurantEnter() throws IOException {
        RoutingScene scene = (RoutingScene) modifyRestaurantEnter.getScene();
        Route route = new XmlRoute(getClass().getResource("/AdminSceneRestaurants.fxml"));
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
