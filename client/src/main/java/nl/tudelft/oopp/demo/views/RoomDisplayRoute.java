package nl.tudelft.oopp.demo.views;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import nl.tudelft.oopp.demo.communication.ImageCommunication;
import nl.tudelft.oopp.demo.core.Route;
import nl.tudelft.oopp.demo.core.RoutingScene;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.widgets.AppBar;
import nl.tudelft.oopp.demo.widgets.GalleryWidget;
import nl.tudelft.oopp.demo.widgets.RectangularImageButton;

import java.util.ArrayList;
import java.util.List;

public class RoomDisplayRoute extends Route {
    private Room room;

    private VBox rootContainer;
    private HBox contentContainer;
    private VBox leftContainer;
    private GalleryWidget galleryWidget;

    private Text title;
    private Rectangle titlePadding;
    private TextWithIcon capacityText;
    private TextWithIcon whiteboardText;
    private TextWithIcon tvText;
    private TextWithIcon buildingText;

    private Rectangle buttonPadding;
    private Button reserveButton;

    /**
     * Instantiates the room display route for the room passed as parameter.
     * @param room the room to be displayed
     */
    public RoomDisplayRoute(Room room) {
        this.room = room;

        rootContainer = new VBox();

        AppBar appBar = new AppBar();
        rootContainer.getChildren().add(appBar);

        contentContainer = new HBox();
        rootContainer.getChildren().add(contentContainer);

        leftContainer = new VBox();
        leftContainer.setSpacing(16);
        leftContainer.setAlignment(Pos.CENTER);
        leftContainer.setPadding(new Insets(32));
        contentContainer.getChildren().add(leftContainer);

        galleryWidget = new GalleryWidget(getRoomImages());
        contentContainer.getChildren().add(galleryWidget);

        // Add title
        title = new Text(String.format("%s (%s)", room.getName(), room.getRoomCode()));
        title.getStyleClass().add("gallery-text");
        leftContainer.getChildren().add(title);
        titlePadding = new Rectangle();
        titlePadding.setHeight(24);
        leftContainer.getChildren().add(titlePadding);

        // Add info texts
        Image capacityImage = new Image("/images/capacity_icon.png");
        String capacityString  = String.format("Up to %d people", room.getCapacity());
        capacityText = new TextWithIcon(capacityImage, capacityString);
        leftContainer.getChildren().add(capacityText);

        Image whiteboardImage = new Image("/images/whiteboard_icon.png");
        String whiteboardString;
        if (room.isHasWhiteboard()) {
            whiteboardString = "Does have a whiteboard";
        } else {
            whiteboardString = "Does not have a whiteboard";
        }
        whiteboardText = new TextWithIcon(whiteboardImage, whiteboardString);
        leftContainer.getChildren().add(whiteboardText);

        Image tvImage = new Image("/images/tv_icon.png");
        String tvString;
        if (room.isHasTV()) {
            tvString = "Does have a TV";
        } else {
            tvString = "Does not have a TV";
        }
        tvText = new TextWithIcon(tvImage, tvString);
        leftContainer.getChildren().add(tvText);

        Image buildingIcon = new Image("/images/building_icon.png");
        String buildingString  = room.getBuilding().getName();
        buildingText = new TextWithIcon(buildingIcon, buildingString);
        leftContainer.getChildren().add(buildingText);

        // Create reserve button
        buttonPadding = new Rectangle();
        buttonPadding.setHeight(24);
        leftContainer.getChildren().add(buttonPadding);
        reserveButton = new Button("Reserve room");
        reserveButton.getStyleClass().add("reserve-button");
        reserveButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Button button = (Button) event.getSource();
                RoutingScene routingScene = (RoutingScene) button.getScene();
                routingScene.pushRoute(new RoomReservationRoute(room));
            }
        });
        leftContainer.getChildren().add(reserveButton);

        rootContainer.sceneProperty().addListener((obs2, oldScene, newScene) -> {
            if (newScene != null) {
                resizeDisplay(newScene.getWidth(), newScene.getHeight() * 0.9);
                newScene.widthProperty().addListener((obs, oldWidth, newWidth) -> {
                    resizeDisplay(newScene.getWidth(), newScene.getHeight() * 0.9);
                });
                newScene.heightProperty().addListener((obs, oldHeight, newHeight) -> {
                    resizeDisplay(newScene.getWidth(), newScene.getHeight() * 0.9);
                });
            }
        });
    }

    private void resizeDisplay(double newWidth, double newHeight) {
        leftContainer.setPrefWidth(newWidth * 0.5);
        title.setStyle("-fx-font-size: " + newHeight * 0.075);
        title.setWrappingWidth(newWidth * 0.5 - 64);
        double infoTextHeight = newHeight * 0.045;
        capacityText.setPrefWidth(newWidth * 0.5 - 64);
        capacityText.setPrefHeight(infoTextHeight);
        whiteboardText.setPrefWidth(newWidth * 0.5 - 64);
        whiteboardText.setPrefHeight(infoTextHeight);
        tvText.setPrefWidth(newWidth * 0.5 - 64);
        tvText.setPrefHeight(infoTextHeight);
        buildingText.setPrefWidth(newWidth * 0.5 - 64);
        buildingText.setPrefHeight(infoTextHeight);

        reserveButton.setStyle("-fx-font-size: " + newHeight * 0.025);

        galleryWidget.setPrefWidth(newWidth * 0.5);
        galleryWidget.setPrefHeight(newHeight);
    }

    private List<Image> getRoomImages() {
        List<String> imageUrls = ImageCommunication.getRoomImageUrl(room.getRoomCode());

        List<Image> roomImages = new ArrayList<>();
        for (String url : imageUrls) {
            roomImages.add(new Image(url));
        }
        return roomImages;
    }

    @Override
    public Parent getRootElement() {
        return rootContainer;
    }

    private static class TextWithIcon extends HBox {
        private ImageView imageView;
        private Text text;

        public TextWithIcon(Image icon, String text) {
            setAlignment(Pos.CENTER_LEFT);
            setPadding(new Insets(0, 0, 0, 24));
            setSpacing(24);

            imageView = new ImageView(icon);
            getChildren().add(imageView);

            this.text = new Text(text);
            this.text.getStyleClass().add("gallery-text");
            getChildren().add(this.text);

            prefWidthProperty().addListener((obs, oldWidth, newWidth) -> {
                resizeWidget(getPrefWidth(), getPrefHeight());
            });
            prefHeightProperty().addListener((obs, oldHeight, newHeight) -> {
                resizeWidget(getPrefWidth(), getPrefHeight());
            });
        }

        private void resizeWidget(double newWidth, double newHeight) {
            text.setWrappingWidth(newWidth - newHeight * 0.8 - 24);
            text.setStyle("-fx-font-size: " + newHeight);

            imageView.setFitWidth(newHeight);
            imageView.setFitHeight(newHeight);
        }
    }
}
