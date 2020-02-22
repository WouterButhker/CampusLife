package nl.tudelft.oopp.demo.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class RegisterScreenDisplay extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/RegisterScreen.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
