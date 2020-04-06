package nl.tudelft.oopp.demo.widgets;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.*;
import nl.tudelft.oopp.demo.communication.UserCommunication;

import static nl.tudelft.oopp.demo.communication.ImageCommunication.updateUserImage;


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

    public static void displayPasswordChange(String username) {
        Stage popUpWindow = new Stage();
        popUpWindow.initModality(Modality.APPLICATION_MODAL);
        popUpWindow.setTitle(" Change Password");
        Label label1 = new Label(" New Password must:");
        Label label2 = new Label(" -Be at least 8 characters long");
        Label label3 = new Label(" -Contain an uppercase and lowercase character");
        Label label4 = new Label(" -Contain at least one number and one special character");
        VBox layout = new VBox(10);
        label1.setStyle("-fx-font-family: -primary-font-name;"
                + "-fx-font-weight: bold;"
                + "-fx-font-size: 18;");
        String style = "-fx-font-family: -primary-font-name;"
                + "-fx-font-size: 16;";
        label2.setStyle(style);
        label3.setStyle(style);
        label4.setStyle(style);
        layout.getChildren().addAll(label1, label2, label3, label4);

        PasswordField newPassword = new PasswordField();
        PasswordField retypeNewPassword = new PasswordField();

        newPassword.setStyle(style);
        retypeNewPassword.setStyle(style);

        HBox pass1 = new HBox();
        Label passLabel = new Label(" New Password      ");
        passLabel.setStyle(style);
        pass1.getChildren().addAll(passLabel, newPassword);
        HBox pass2 = new HBox();
        Label retypePassLabel = new Label(" Retype Password  ");
        retypePassLabel.setStyle(style);
        pass2.getChildren().addAll(retypePassLabel, retypeNewPassword);


        Button button1 = new Button(" Change Password ");

        String styleHovered = "-fx-background-color:#4d70ff;"
                + "-fx-text-fill: white; -fx-font-size: 16;";
        String stylebutton = "-fx-background-color: #7ca7fc;"
                + "-fx-text-fill: white; -fx-font-size: 16;";
        button1.setStyle(stylebutton);
        button1.setOnMouseEntered(event -> button1.setStyle(styleHovered));
        button1.setOnMouseExited(event -> button1.setStyle(stylebutton));
        button1.setPrefWidth(200);

        HBox button = new HBox();
        Pane spacer = new Pane();
        spacer.setPrefWidth(133);
        button.getChildren().addAll(spacer, button1);

        Label errorMessage = new Label("");
        errorMessage.setStyle(style);

        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!(newPassword.getText().equals(retypeNewPassword.getText()))) {
                    errorMessage.setText(" Passwords do not match");
                    newPassword.setText("");
                    retypeNewPassword.setText("");
                } else {
                    String pass = newPassword.getText();
                    boolean hasUppercase = !(pass.equals(pass.toLowerCase()));
                    boolean hasLowercase = !(pass.equals(pass.toUpperCase()));
                    boolean hasNumber = pass.matches(".*\\d.*");

                    if (pass.length() < 8) {
                        errorMessage.setText(" Password must be at least 8 characters long");
                        newPassword.setText("");
                        retypeNewPassword.setText("");
                        return;
                    }
                    if (!(hasLowercase && hasUppercase)) {
                        errorMessage.setText(" Password must have at least one uppercase and one lowercase");
                        newPassword.setText("");
                        retypeNewPassword.setText("");
                        return;
                    }
                    if (!hasNumber) {
                        errorMessage.setText(" Password must have at least one number");
                        newPassword.setText("");
                        retypeNewPassword.setText("");
                        return;
                    }

                    Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                    Matcher m = p.matcher(pass);
                    boolean hasSpecialCharacter = m.find();

                    if (!hasSpecialCharacter) {
                        errorMessage.setText(" Password must have at least one special character");
                        newPassword.setText("");
                        retypeNewPassword.setText("");
                        return;
                    }
                    if(PopupWidget.displayBool("Are you sure you want to change your password?"
                            , "Are you sure?")) {
                        UserCommunication.changePassword(username, newPassword.getText());
                        popUpWindow.close();
                    }
                }
            }
        });


        layout.getChildren().addAll(pass1, pass2, errorMessage, button);
        layout.setAlignment(Pos.CENTER_LEFT);
        Scene scene1 = new Scene(layout, 466, 370);
        popUpWindow.setScene(scene1);
        popUpWindow.showAndWait();
    }

}
