package nl.tudelft.oopp.demo.widgets;

import java.util.Optional;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.*;



public class PopupWidget {

    /**
     * Creates a popup that displays the parameter message.
     * @param message The message that is to be displayed in the popup
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

    /**
     * Creates an error popup that displays
     * an error image, the parameter message, and has a specified title.
     * @param message The message that is to be displayed in the popup
     * @param title The title of the popup
     */
    public static void displayError(String message, String title) {
        Stage popUpWindow = new Stage();
        popUpWindow.setTitle(title);
        popUpWindow.initModality(Modality.APPLICATION_MODAL);
        Label label1 = new Label(message);
        label1.setStyle("-fx-font-size: 14");

        Button button1 = new Button(" OK ");
        button1.setStyle("-fx-font-size: 14");

        HBox errorMessage = new HBox();
        ImageView error = new ImageView();
        Image errorImage = new Image("/images/cross.png");
        error.setImage(errorImage);

        errorMessage.setAlignment(Pos.CENTER_LEFT);
        errorMessage.getChildren().addAll(new Label("    "), error, new Label("  "), label1);




        button1.setOnAction(e -> popUpWindow.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(errorMessage, new Label(""), button1);
        layout.setAlignment(Pos.CENTER);
        Scene scene1 = new Scene(layout, 370, 190);
        popUpWindow.setScene(scene1);
        popUpWindow.showAndWait();
    }

    /**
     * Creates a success popup that displays
     * a success icon, the parameter message, and has a specified title.
     * @param message The message that is to be displayed in the popup
     * @param title The title of the popup
     */
    public static void displaySuccess(String message, String title) {
        Stage popUpWindow = new Stage();
        popUpWindow.setTitle(title);
        popUpWindow.initModality(Modality.APPLICATION_MODAL);
        Label label1 = new Label(message);
        label1.setStyle("-fx-font-size: 14");

        Button button1 = new Button(" OK ");
        button1.setStyle("-fx-font-size: 14");

        HBox errorMessage = new HBox();
        ImageView error = new ImageView();
        Image errorImage = new Image("/images/check.png");
        error.setImage(errorImage);

        errorMessage.setAlignment(Pos.CENTER_LEFT);
        errorMessage.getChildren().addAll(new Label("    "), error, new Label("  "), label1);

        button1.setOnAction(e -> popUpWindow.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(errorMessage, new Label(""), button1);
        layout.setAlignment(Pos.CENTER);
        Scene scene1 = new Scene(layout, 370, 190);
        popUpWindow.setScene(scene1);
        popUpWindow.showAndWait();
    }



    /**
     * Creates a yes/no popup that displays the parameter message, and has a specified title.
     * @param message The message that is to be displayed in the popup
     * @param title The title of the popup
     * @return
     */
    public static boolean displayBool(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.setContentText("Please select your choice");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }

}
