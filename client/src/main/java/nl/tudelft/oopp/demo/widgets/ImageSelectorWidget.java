package nl.tudelft.oopp.demo.widgets;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.springframework.web.multipart.MultipartFile;


public class ImageSelectorWidget extends VBox {
    private HBox chooseFile;
    private Button chooseFileButton;
    private Label fileChosen;

    private File image;

    /**
     * Creates a new ImageSelectorWidget.
     */
    public ImageSelectorWidget() {
        Label selectImage = new Label("Select Image:");
        selectImage.setStyle("-fx-font-weight: bold;");
        this.image = null;
        this.fileChosen = new Label("  No file chosen");
        this.chooseFileButton = new Button("Choose Image");


        this.chooseFile = new HBox();
        this.chooseFile.setPrefHeight(32);
        this.chooseFile.setAlignment(Pos.CENTER_LEFT);
        this.chooseFile.getChildren().addAll(chooseFileButton, fileChosen);

        this.getChildren().addAll(selectImage, chooseFile);
        this.setAlignment(Pos.CENTER_LEFT);

        chooseFileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                pickImage();
            }
        });
    }

    /**
     * Opens a file navigation that allows the user to select a jpg or png.
     */
    public void pickImage() {
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

    public File getImage() {
        return this.image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public boolean imageSelected() {
        return image != null;
    }

    public void addChild(Node node) {
        this.getChildren().add(node);
    }

    public void removeChild(Node node) {
        this.getChildren().remove(node);
    }
}
