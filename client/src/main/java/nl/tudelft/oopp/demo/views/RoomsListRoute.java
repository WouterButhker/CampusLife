package nl.tudelft.oopp.demo.views;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
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
    private AnchorPane ap;
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
        ap = new AnchorPane();
        AppBar appBar = new AppBar();
        rootContainer.getChildren().add(appBar);

        scrollPane = new ScrollPane(rootContainer);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        //filter box container
        filters = new VBox();
        filterTitle = new Text("Filters:");
        filterTitle.getStyleClass().add("university-main-title");
        filters.getChildren().add(filterTitle);
        filters.setTranslateX(5);

        //filter checkboxes
        CheckBox available = new CheckBox();
        available.setText("Available");
        CheckBox smallRoom = new CheckBox();
        smallRoom.setText("Small rooms");
        CheckBox mediumRoom = new CheckBox();
        mediumRoom.setText("Medium rooms");
        CheckBox largeRoom = new CheckBox();
        largeRoom.setText("Large rooms");
        filters.getChildren().addAll(available, smallRoom, mediumRoom, largeRoom);
        filters.setSpacing(6);
        filters.setPrefHeight(150);
        filters.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
                new CornerRadii(0), new BorderWidths(1))));
        ap.getChildren().add(filters);

        //container for the rooms
        VBox rooms = new VBox();
        rooms.setTranslateX(140);
        rooms.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
                new CornerRadii(0), new BorderWidths(1))));
        rooms.setPrefWidth(430);

        RoomsGridView buildingsGrid = new RoomsGridView(roomList);
        rooms.getChildren().add(buildingsGrid);
        buildingsGrid.setListener(new RoomsGridView.Listener() {
            @Override
            public void onRoomClicked(Room room) {
                RoutingScene routingScene = getRoutingScene();
                routingScene.pushRoute(new RoomDisplayRoute(room));
            }
        });
        ap.getChildren().add(rooms);

        rootContainer.getChildren().add(ap);
    }

    @Override
    public Parent getRootElement() {
        return scrollPane;
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
