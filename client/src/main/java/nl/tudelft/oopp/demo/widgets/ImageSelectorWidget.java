package nl.tudelft.oopp.demo.widgets;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;


public class ImageSelectorWidget extends VBox {
    private HBox chooseFile;
    private Button chooseFileButton;
    private Label fileChosen;

    private Button sendFile;

    private File image;

    /**
     * Creates a new ImageSelectorWidget.
     */
    public ImageSelectorWidget() {
        Label selectImage = new Label("Select Image:");
        selectImage.setStyle("-fx-font-weight: bold;");
        this.fileChosen = new Label("  No file chosen");
        this.chooseFileButton = new Button("Choose Image");
        this.sendFile = new Button("Send");

        this.chooseFile = new HBox();
        this.chooseFile.setPrefHeight(32);
        this.chooseFile.setAlignment(Pos.CENTER_LEFT);
        this.chooseFile.getChildren().addAll(chooseFileButton, fileChosen);

        this.getChildren().addAll(selectImage, chooseFile, sendFile);
        this.setAlignment(Pos.CENTER_LEFT);

        chooseFileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                getImage();
            }
        });

        sendFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                upload();
            }
        });
    }

    /**
     * Opens a file navigation that allows the user to select a jpg or png.
     */
    public void getImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters()
                .addAll(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {

            System.out.println("File selected: " + selectedFile.getName());
            this.fileChosen.setText("  " + selectedFile.getName());
        } else {
            System.out.println("File selection cancelled.");
        }

        this.image = selectedFile;
    }

    /**
     * uploads the selected image.
     */
    public void upload() {
        if (this.image != null) {
            System.out.println(this.image.getName());
        } else {
            System.out.println("No file selected");
        }

    }
}
