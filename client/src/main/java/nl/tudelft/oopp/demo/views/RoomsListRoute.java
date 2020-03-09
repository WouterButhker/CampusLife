package nl.tudelft.oopp.demo.views;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import nl.tudelft.oopp.demo.communication.RoomCommunication;
import nl.tudelft.oopp.demo.core.Route;
import nl.tudelft.oopp.demo.core.RoutingScene;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.widgets.AppBar;
import nl.tudelft.oopp.demo.widgets.BuildingsGridView;
import nl.tudelft.oopp.demo.widgets.RectangularImageButton;
import nl.tudelft.oopp.demo.widgets.RoomsGridView;

public class RoomsListRoute extends Route {

    private ScrollPane scrollPane;
    private VBox rootContainer;
    private HBox hBox;
    private Text universityTitle;

    private HBox buildingsTitleContainer;
    private Text buildingsTitle;

    private HBox buttonsRow;
    private List<RectangularImageButton> mainButtons;

    private VBox filters;
    private Text filterTitle;

    /**
     * A route that displays the list of rooms from a Building and also filters.
     * @param buildingCode the number of the building being displayed
     */
    public RoomsListRoute(Integer buildingCode) {
        createRootElement(RoomCommunication.getAllRoomsFromBuilding(buildingCode));
    }

    /**
     * Instantiates a RoomsListRoute that displays all rooms.
     */
    public RoomsListRoute() {
        createRootElement(RoomCommunication.getAllRooms());
    }

    private void createRootElement(List<Room> roomList) {
        rootContainer = new VBox();
        hBox = new HBox();
        AppBar appBar = new AppBar();
        rootContainer.getChildren().add(appBar);

        //filter box container
        filters = new VBox();
        filters.setPadding(new Insets(5));
        filterTitle = new Text("Filters:");
        filterTitle.getStyleClass().add("university-main-title");
        filters.getChildren().add(filterTitle);
        filters.setTranslateX(5);

        //making filter boxes
        CheckBox hasWhiteboard = new CheckBox("white board");
        hasWhiteboard.setStyle("-fx-background-color: -secondary-color; -fx-background-radius: 16;");
        hasWhiteboard.setPadding(new Insets(8, 16, 8, 16));
        hasWhiteboard.setPrefWidth(130);

        CheckBox hasTV = new CheckBox("TV");
        hasTV.setStyle("-fx-background-color: -secondary-color; -fx-background-radius: 16;");
        hasTV.setPadding(new Insets(8, 16, 8, 16));
        hasTV.setPrefWidth(130);

        TextField minCap = new TextField();
        minCap.setPromptText("Minimal capacity");
        minCap.setStyle("-fx-background-color: -secondary-color; -fx-background-radius: 16;");
        minCap.setPadding(new Insets(8, 16, 8, 16));
        minCap.setPrefWidth(130);

        TextField maxCap = new TextField();
        maxCap.setPromptText("Maximal capacity");
        maxCap.setStyle("-fx-background-color: -secondary-color; -fx-background-radius: 16;");
        maxCap.setPadding(new Insets(8, 16, 8, 16));
        maxCap.setPrefWidth(130);

        Button apply = new Button("Apply filters");
        apply.setStyle("-fx-background-radius: 16;");
        apply.setPadding(new Insets(8, 16, 8, 16));
        apply.setPrefWidth(130);

        filters.getChildren().addAll(hasWhiteboard, hasTV, minCap, maxCap, apply);
        filters.setSpacing(5);
        filters.setMaxHeight(140);
        hBox.getChildren().add(filters);


        //container for the rooms
        VBox rooms = new VBox();

        RoomsGridView buildingsGrid = new RoomsGridView(roomList);
        rooms.getChildren().add(buildingsGrid);
        buildingsGrid.setListener(new RoomsGridView.Listener() {
            @Override
            public void onRoomClicked(Room room) {
                RoutingScene routingScene = getRoutingScene();
                routingScene.pushRoute(new RoomDisplayRoute(room));
            }
        });

        scrollPane = new ScrollPane(rooms);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color:transparent;");
        hBox.getChildren().add(scrollPane);

        rootContainer.getChildren().add(hBox);
    }

    @Override
    public Parent getRootElement() {
        return rootContainer;
    }



    private void resizeDisplay(Number newWidth) {
        /*
        This route should display the title, main buttons, buildings title
        and then the buildings below the building buttons should be partially
        covered so as to not make them the main thing
        */

        rootContainer.setPadding(new Insets(20, 0, 0, 0));

        buildingsTitleContainer.setMinWidth(newWidth.doubleValue());

        final double buttonWidth = 0.245;
        for (RectangularImageButton imageButton : mainButtons) {
            imageButton.setFitWidth(buttonWidth * newWidth.doubleValue());
        }
        double horizontalSpacing = ((1 - buttonWidth * 3) / 4) * newWidth.doubleValue();
        buttonsRow.setPadding(new Insets(0, 0, 0, horizontalSpacing));
        buttonsRow.setSpacing(horizontalSpacing);
    }
}
