package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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

        modifyBuildingsEnter.setStyle("-fx-font-size: 17");
        modifyRoomsEnter.setStyle("-fx-font-size: 17");
        modifyRestaurantEnter.setStyle("-fx-font-size: 17");
        modifyFoodEnter.setStyle("-fx-font-size: 17");
        modifyRightsEnter.setStyle("-fx-font-size: 17");
        modifyReservationsEnter.setStyle("-fx-font-size: 17");
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
