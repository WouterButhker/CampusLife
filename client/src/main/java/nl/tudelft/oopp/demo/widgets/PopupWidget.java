package nl.tudelft.oopp.demo.widgets;

import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;


public class PopupWidget {

    /**
     * Creates a popup that displays the parameter message.
     * @param message The message that is to be displayed in the popup
     */
    public static void display(String message) {
        Stage popupwindow = new Stage();
        popupwindow.setTitle("Error");
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        Label label1 = new Label(message);
        Button button1 = new Button(" OK ");

        label1.setStyle("-fx-font-size: 14");
        button1.setStyle("-fx-font-size: 14");

        button1.setOnAction(e -> popupwindow.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label1, new Label(""), button1);
        layout.setAlignment(Pos.CENTER);
        Scene scene1 = new Scene(layout, 370, 190);
        popupwindow.setScene(scene1);
        popupwindow.showAndWait();
    }

    /**
     * Creates a popup that displays the parameter message, and has a specified title.
     * @param message The message that is to be displayed in the popup
     * @param title The title of the popup
     */
    public static void display(String message, String title) {
        Stage popupwindow = new Stage();
        popupwindow.setTitle(title);
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        Label label1 = new Label(message);
        Button button1 = new Button(" OK ");

        label1.setStyle("-fx-font-size: 14");
        button1.setStyle("-fx-font-size: 14");

        button1.setOnAction(e -> popupwindow.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label1, new Label(""), button1);
        layout.setAlignment(Pos.CENTER);
        Scene scene1 = new Scene(layout, 370, 190);
        popupwindow.setScene(scene1);
        popupwindow.showAndWait();
    }

}
