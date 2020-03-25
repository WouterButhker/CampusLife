package nl.tudelft.oopp.demo.views;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import nl.tudelft.oopp.demo.core.Route;
import nl.tudelft.oopp.demo.core.RoutingScene;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.widgets.AppBar;
import nl.tudelft.oopp.demo.widgets.RectangularImageButton;

public class RoomDisplayRoute extends Route {
    private Room room;

    private VBox rootContainer;
    private VBox topLeftQuadrant;
    private VBox topRightQuadrant;
    private VBox botLeftQuadrant;

    /**
     * Instantiates the room display route for the room passed as parameter.
     * @param room the room to be displayed
     */
    public RoomDisplayRoute(Room room) {
        this.room = room;

        rootContainer = new VBox();

        AppBar appBar = new AppBar();
        rootContainer.getChildren().add(appBar);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(16, 16, 16, 16));
        rootContainer.getChildren().add(gridPane);
        topLeftQuadrant = new VBox();
        topLeftQuadrant.setAlignment(Pos.TOP_LEFT);
        topLeftQuadrant.setFillWidth(false);
        gridPane.getChildren().add(topLeftQuadrant);
        GridPane.setConstraints(topLeftQuadrant, 0, 0, 1, 1);
        topRightQuadrant = new VBox();
        topRightQuadrant.setAlignment(Pos.TOP_RIGHT);
        topRightQuadrant.setFillWidth(false);
        gridPane.getChildren().add(topRightQuadrant);
        GridPane.setConstraints(topRightQuadrant, 1, 0, 1, 1);
        botLeftQuadrant = new VBox();
        botLeftQuadrant.setAlignment(Pos.TOP_CENTER);
        botLeftQuadrant.setFillWidth(true);
        gridPane.getChildren().add(botLeftQuadrant);
        GridPane.setConstraints(botLeftQuadrant, 0, 1, 1, 1);

        topLeftQuadrant.getChildren().add(createDescriptionTextBox());
        topRightQuadrant.getChildren().add(createLocatedAtBox());
        botLeftQuadrant.getChildren().add(createReserveButton());

        rootContainer.sceneProperty().addListener((obs2, oldScene, newScene) -> {
            if (newScene != null) {
                resizeDisplay(newScene.getWidth());
                newScene.widthProperty().addListener((obs, oldWidth, newWidth) -> {
                    resizeDisplay(newWidth.doubleValue());
                });
            }
        });
    }

    private void resizeDisplay(double newWidth) {
        topLeftQuadrant.setPrefWidth(newWidth * 0.5 - 16);
        topRightQuadrant.setPrefWidth(newWidth * 0.5 - 16);
        botLeftQuadrant.setPrefWidth(newWidth * 0.5 - 16);
    }

    @Override
    public Parent getRootElement() {
        return rootContainer;
    }

    private Node createDescriptionTextBox() {
        Text roomTitle = new Text(String.format("%s (%s)", room.getName(), room.getRoomCode()));
        roomTitle.getStyleClass().add("room-display-title");

        String whiteboardString;
        if (room.isHasWhiteboard()) {
            whiteboardString = "- Does have a whiteboard";
        } else {
            whiteboardString = "- Does not have a whiteboard";
        }
        Text whiteBoardText = new Text(whiteboardString);
        whiteBoardText.getStyleClass().add("room-display-text");

        String tvString;
        if (room.isHasTV()) {
            tvString = "- Does have a TV";
        } else {
            tvString = "- Does not have a TV";
        }
        Text tvText = new Text(tvString);
        tvText.getStyleClass().add("room-display-text");
        Text capacityText = new Text(String.format("- Up to %d people", room.getCapacity()));
        capacityText.getStyleClass().add("room-display-text");

        VBox rootContainer = new VBox();
        rootContainer.setPadding(new Insets(16, 16, 16, 16));

        VBox infoContainer = new VBox();
        infoContainer.setPadding(new Insets(16, 16, 0, 16));
        infoContainer.getChildren().add(whiteBoardText);
        infoContainer.getChildren().add(tvText);
        infoContainer.getChildren().add(capacityText);
        rootContainer.getChildren().add(roomTitle);
        rootContainer.getChildren().add(infoContainer);
        rootContainer.setStyle("-fx-background-color: -secondary-color-dark");

        return rootContainer;
    }

    private Node createLocatedAtBox() {
        VBox rootContainer = new VBox();
        Text locatedAtText = new Text("Located at:");
        locatedAtText.getStyleClass().add("room-display-text");

        Image image = new Image("/images/main-screen-default-building.jpg");
        RectangularImageButton button = new RectangularImageButton(image, "Drebbelweg");
        button.setFitWidth(200);

        rootContainer.setPadding(new Insets(8, 8, 8, 8));
        rootContainer.setSpacing(8);
        rootContainer.getChildren().addAll(locatedAtText, button);
        rootContainer.setStyle("-fx-background-color: -secondary-color-dark");
        return rootContainer;
    }

    private Node createReserveButton() {
        Button button = new Button("Reserve room");
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Button button = (Button) event.getSource();
                RoutingScene routingScene = (RoutingScene) button.getScene();
                routingScene.pushRoute(new RoomReservationRoute(room));
            }
        });
        return button;
    }
}
