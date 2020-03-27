package nl.tudelft.oopp.demo.widgets;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.io.FileNotFoundException;

public class LoadingAnimationWidget {
    public static void showLoading() throws FileNotFoundException {
        Stage popupwindow = new Stage();
        popupwindow.setTitle("loading...");
        popupwindow.initModality(Modality.APPLICATION_MODAL);

        Label label = new Label("loading...");

        ImageView imageView = new ImageView();
        Image image = new Image("/images/ajax-loader.gif");
        imageView.setImage(image);

        VBox layout = new VBox(10);
        layout.setStyle("-fx-background-color:White");
        layout.getChildren().addAll(label, imageView);
        layout.setAlignment(Pos.CENTER);
        Scene scene1 = new Scene(layout, 300, 250);
        popupwindow.setScene(scene1);
        popupwindow.show();
    }
}
