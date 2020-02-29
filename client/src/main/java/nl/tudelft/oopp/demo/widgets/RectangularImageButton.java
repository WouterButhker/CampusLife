package nl.tudelft.oopp.demo.widgets;

import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class RectangularImageButton extends StackPane {
    private Image image;
    private String text;

    private Rectangle hoverShade;
    private Rectangle labelBackgroundOffset;
    private Rectangle labelBackground;
    private Text label;
    private ImageView imageView;
    private StackPane labelPane;

    /**
     * Creates RectangularImageButton with an image as background and a label of text.
     * @param image the background image
     * @param text the label
     */
    public RectangularImageButton(Image image, String text) {
        draw(image, text, true);
    }

    /**
     * Creates RectangularImageButton with an image as background and a label of text.
     * @param image the background image
     * @param text the label
     * @param preserveRatio if the image should preserve the ratio
     */
    public RectangularImageButton(Image image, String text, boolean preserveRatio) {
        draw(image, text, preserveRatio);
    }

    private void draw(Image image, String text, boolean preserveRatio) {
        this.image = image;
        this.text = text;

        hoverShade = new Rectangle();
        hoverShade.setFill(Color.color(1.0, 1.0, 1.0, 0.2));
        hoverShade.setVisible(false);

        imageView = new ImageView(image);
        imageView.setPreserveRatio(preserveRatio);

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                showHover();
            }
        });
        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hideHover();
            }
        });

        // Create label background
        labelBackground = new Rectangle();
        labelBackground.setFill(Color.LIGHTSKYBLUE);
        // And offset, this is used so that the label is not in the middle of the image
        labelBackgroundOffset = new Rectangle();
        imageView.fitWidthProperty().addListener((obs, oldWidth, newWidth) -> {
            hoverShade.setWidth(newWidth.doubleValue());
            hoverShade.setHeight(newWidth.doubleValue());
            labelBackground.setWidth(newWidth.doubleValue());
            labelBackground.setHeight(newWidth.doubleValue() * 0.3);
            labelBackgroundOffset.setHeight(newWidth.doubleValue() * 0.71);
            setMaxWidth(newWidth.doubleValue());
            labelPane.setMaxWidth(newWidth.doubleValue());
        });
        // Create label
        label = new Text(text);
        StackPane.setAlignment(label, Pos.CENTER_LEFT);
        // Add to labelPane
        labelPane = new StackPane();
        labelPane.maxHeightProperty().bind(labelBackground.widthProperty());
        labelPane.getChildren().addAll(labelBackground, label);
        StackPane.setAlignment(labelPane, Pos.BOTTOM_LEFT);

        // Create labelVBox
        VBox labelVBox = new VBox();
        labelVBox.getChildren().addAll(labelBackgroundOffset, labelPane);

        getChildren().add(imageView);
        getChildren().add(labelVBox);
        getChildren().add(hoverShade);
    }

    private void showHover() {
        hoverShade.setVisible(true);
    }

    private void hideHover() {
        hoverShade.setVisible(false);
    }

    /**
     * This function sets the width of the RectangularImageButton.
     * @param width the width in pixels
     */
    public void setFitWidth(double width) {
        imageView.setFitWidth(width);
    }

    /**
     * This function sets the height of the RectangularImageButton.
     * @param height the height in pixels
     */
    public void setFitHeight(double height) {
        imageView.setFitHeight(height);
    }

    /**
     * Getter for the fitWidthProperty of this object.
     * @return the fitWidthProperty of this object
     */
    public DoubleProperty fitWidthProperty() {
        return imageView.fitWidthProperty();
    }

    /**
     * Getter for the fitHeightProperty of this object.
     * @return the fitHeightProperty of this object
     */
    public DoubleProperty fitHeightProperty() {
        return imageView.fitHeightProperty();
    }
}
