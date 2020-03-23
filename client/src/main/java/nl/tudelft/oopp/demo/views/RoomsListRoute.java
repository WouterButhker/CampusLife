package nl.tudelft.oopp.demo.views;

import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import jdk.jfr.Event;
import nl.tudelft.oopp.demo.communication.AuthenticationCommunication;
import nl.tudelft.oopp.demo.communication.RoomCommunication;
import nl.tudelft.oopp.demo.core.Route;
import nl.tudelft.oopp.demo.core.RoutingScene;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.widgets.AppBar;
import nl.tudelft.oopp.demo.widgets.RectangularImageButton;
import nl.tudelft.oopp.demo.widgets.RoomsGridView;

//import javafx.scene.control.*;

public class RoomsListRoute extends Route {

    private ScrollPane scrollPane;
    private VBox rootContainer;
    private HBox horizontalBox;
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
        createRootElement(RoomCommunication.getAllRoomsFromBuilding(buildingCode), buildingCode);
    }

    /**
     * Instantiates a RoomsListRoute that displays all rooms.
     */
    public RoomsListRoute() {
        createRootElement(RoomCommunication.getAllRooms(), -1);
    }

    private void createRootElement(List<Room> roomList, Integer buildingCode) {
        rootContainer = new VBox();
        horizontalBox = new HBox();
        AppBar appBar = new AppBar();
        rootContainer.getChildren().add(appBar);

        //filter box container
        filters = new VBox();
        filters.setPadding(new Insets(5));
        filters.setTranslateX(5);

        //making filter boxes
        CheckBox hasWhiteboard = new CheckBox("white board");
        hasWhiteboard.setStyle("-fx-background-color: -secondary-color; "
                                + "-fx-background-radius: 16;");
        hasWhiteboard.setPadding(new Insets(8, 16, 8, 16));
        hasWhiteboard.setPrefWidth(130);

        CheckBox hasTV = new CheckBox("TV");
        hasTV.setStyle("-fx-background-color: -secondary-color; -fx-background-radius: 16;");
        hasTV.setPadding(new Insets(8, 16, 8, 16));
        hasTV.setPrefWidth(130);

        TextField minCap = new TextField();
        minCap.setPromptText("Min");
        minCap.setPrefWidth(50);
        TextField maxCap = new TextField();
        maxCap.setPromptText("Max");
        maxCap.setPrefWidth(50);
        HBox capacities = new HBox(10, minCap, maxCap);
        capacities.setStyle("-fx-background-color: -secondary-color; -fx-background-radius: 16;");
        capacities.setPadding(new Insets(4, 16, 4, 16));
        capacities.setPrefWidth(130);

        Button apply = new Button("Apply filters");
        apply.setStyle("-fx-background-radius: 16;");
        apply.setPadding(new Insets(8, 16, 8, 16));
        apply.setPrefWidth(130);

        Text errorMessage = new Text();

        filters.getChildren().addAll(hasWhiteboard, hasTV, capacities, apply, errorMessage);
        filters.setSpacing(5);
        horizontalBox.getChildren().add(filters);

        //container for the rooms
        VBox rooms = new VBox();

        RoomsGridView roomsGrid = new RoomsGridView(roomList);
        rooms.getChildren().add(roomsGrid);
        roomsGrid.setListener(new RoomsGridView.Listener() {
            @Override
            public void onRoomClicked(Room room) {
                RoutingScene routingScene = getRoutingScene();
                routingScene.pushRoute(new RoomDisplayRoute(room));
            }
        });

        apply.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!errorMessage.getText().equals("")) {
                    errorMessage.setText("");
                }

                Integer minCapInt = 0;
                if (!minCap.getText().equals("")) {
                    try {
                        minCapInt = Integer.parseInt(minCap.getText());
                    } catch (NumberFormatException e) {
                        errorMessage.setText("This is not a number!");
                    }
                }

                Integer maxCapInt = Integer.MAX_VALUE;
                if (!maxCap.getText().equals("")) {
                    try {
                        maxCapInt = Integer.parseInt(maxCap.getText());
                    } catch (NumberFormatException e) {
                        errorMessage.setText("This is not a number!");
                    }
                }

                Integer myRights = 0;
                if (AuthenticationCommunication.myUserRole.equalsIgnoreCase("Admin")) {
                    myRights = 2;
                } else if (AuthenticationCommunication.myUserRole.equalsIgnoreCase("Employee")) {
                    myRights = 1;
                }
                Boolean hasTvBool = hasTV.isSelected();
                Boolean hasWhiteboardBool = hasWhiteboard.isSelected();
                List<Room> filteredList = RoomCommunication.getFilteredRooms(buildingCode,
                            myRights, hasTvBool, hasWhiteboardBool, minCapInt, maxCapInt);
                rooms.getChildren().clear();
                roomsGrid.setRooms(filteredList);
                rooms.getChildren().add(roomsGrid);
                if (errorMessage.getText().equals("")) {
                    errorMessage.setText("Filters applied!");
                }
            }
        });


        scrollPane = new ScrollPane(rooms);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color:transparent;");
        horizontalBox.getChildren().add(scrollPane);

        rootContainer.getChildren().add(horizontalBox);
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
