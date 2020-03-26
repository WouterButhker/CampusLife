package nl.tudelft.oopp.demo.views;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.core.Route;
import nl.tudelft.oopp.demo.core.RoutingScene;
import nl.tudelft.oopp.demo.core.XmlRoute;

public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Route loginRoute = new XmlRoute(getClass().getResource("/LoginScreen.fxml"));
        RoutingScene routingScene = new RoutingScene(loginRoute);

        routingScene.getStylesheets().add("css/text-styles.css");
        routingScene.getStylesheets().add("css/calendar.css");
        routingScene.getStylesheets().add("css/food-menu.css");
        primaryStage.setScene(routingScene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
