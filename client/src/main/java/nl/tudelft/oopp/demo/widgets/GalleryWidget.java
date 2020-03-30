package nl.tudelft.oopp.demo.widgets;

import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class GalleryWidget extends StackPane {
    private Rectangle background;

    private ImageView imageView;

    private HBox buttonContainer;
    private Rectangle buttonSpace;
    private Button leftButton;
    private Button rightButton;

    private int currentImage = 0;
    private List<Image> images;

    /**
     * Creates a gallery widget which displays the images passed.
     * @param images the images to be displayed
     */
    public GalleryWidget(List<Image> images) {
        this.images = images;

        background = new Rectangle();
        getChildren().add(background);

        imageView = new ImageView();
        getChildren().add(imageView);

        buttonContainer = new HBox();
        buttonContainer.setAlignment(Pos.CENTER);
        leftButton = new Button();
        leftButton.getStyleClass().add("left-arrow-white");
        leftButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                prevImage();
            }
        });
        buttonContainer.getChildren().add(leftButton);

        rightButton = new Button();
        rightButton.getStyleClass().add("right-arrow-white");
        rightButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                nextImage();
            }
        });
        buttonContainer.getChildren().add(rightButton);
        getChildren().add(buttonContainer);

        refreshImage();
        updateButtonVisibility();

        prefWidthProperty().addListener((obs, oldWidth, newWidth) -> {
            resizeWidget(getPrefWidth(), getPrefHeight());
        });
        prefHeightProperty().addListener((obs, oldHeight, newHeight) -> {
            resizeWidget(getPrefWidth(), getPrefHeight());
        });
    }

    private void resizeWidget(double newWidth, double newHeight) {
        background.setWidth(newWidth);
        background.setHeight(newHeight);

        double buttonSize = newWidth * 0.1;
        leftButton.setPrefWidth(buttonSize);
        leftButton.setPrefHeight(buttonSize);
        leftButton.setStyle(
                String.format("-fx-background-size: %fpx %fpx", buttonSize, buttonSize));

        rightButton.setPrefWidth(buttonSize);
        rightButton.setPrefHeight(buttonSize);
        rightButton.setStyle(
                String.format("-fx-background-size: %fpx %fpx", buttonSize, buttonSize));

        buttonContainer.setSpacing(newWidth - buttonSize * 2);

        resizeImage(newWidth, newHeight);
    }

    private void resizeImage(double newWidth, double newHeight) {
        Image image = imageView.getImage();

        double aspectRatio = image.getWidth() / image.getHeight();

        double imageWidth = newWidth * 0.8;
        double imageHeight = imageWidth / aspectRatio;
        // Try fitting width
        if (imageHeight > newHeight * 0.8) {
            imageHeight = newHeight * 0.8;
            imageWidth = imageHeight * aspectRatio;
        }

        imageView.setFitWidth(imageWidth);
        imageView.setFitHeight(imageHeight);
    }

    private void refreshImage() {
        // Must check if image is valid when there are no images
        if (0 <= currentImage && currentImage < images.size()) {
            imageView.setImage(images.get(currentImage));
            resizeImage(getPrefWidth(), getPrefHeight());
        }
    }

    private void updateButtonVisibility() {
        leftButton.setVisible(currentImage - 1 >= 0);
        rightButton.setVisible(currentImage + 1 < images.size());
    }

    private void nextImage() {
        currentImage += 1;
        if (currentImage >= images.size()) {
            currentImage = images.size() - 1;
        }

        updateButtonVisibility();
        refreshImage();
    }

    private void prevImage() {
        currentImage -= 1;
        if (currentImage < 0) {
            currentImage = 0;
        }

        updateButtonVisibility();
        refreshImage();
    }
}
