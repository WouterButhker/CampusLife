package nl.tudelft.oopp.demo.views;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import nl.tudelft.oopp.demo.communication.AuthenticationCommunication;
import nl.tudelft.oopp.demo.communication.FavoriteRoomCommunication;
import nl.tudelft.oopp.demo.communication.ImageCommunication;
import nl.tudelft.oopp.demo.communication.RoomCommunication;
import nl.tudelft.oopp.demo.core.PopupRoute;
import nl.tudelft.oopp.demo.core.Route;
import nl.tudelft.oopp.demo.core.RoutingScene;
import nl.tudelft.oopp.demo.entities.FavoriteRoom;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.widgets.AppBar;
import nl.tudelft.oopp.demo.widgets.ButtonsGridView;
import nl.tudelft.oopp.demo.widgets.LoadingPopup;

//import javafx.scene.control.*;

public class RoomsListRoute extends PopupRoute {

    private ScrollPane scrollPane;
    private VBox rootContainer;
    private HBox horizontalBox;

    private List<Image> images;
    private List<String> texts;
    private List<Room> roomList;
    private VBox filters;
    private ButtonsGridView roomsGrid;

    /**
     * A route that displays the list of rooms from a Building and also filters.
     * @param buildingCode the number of the building being displayed
     */
    public RoomsListRoute(Integer buildingCode) {
        super(new VBox());
        createRootElement(RoomCommunication.getAllRoomsFromBuilding(buildingCode), buildingCode);
    }

    /**
     * Instantiates a RoomsListRoute that displays all rooms.
     */
    public RoomsListRoute() {
        super(new VBox());
        createRootElement(RoomCommunication.getAllRooms(), -1);
    }

    private void createRootElement(List<Room> roomList, Integer buildingCode) {
        this.roomList = roomList;
        rootContainer = (VBox) getMainElement();
        horizontalBox = new HBox();
        AppBar appBar = new AppBar();
        rootContainer.getChildren().add(appBar);

        //filter box container
        filters = new VBox();
        filters.setPadding(new Insets(5));
        filters.setTranslateX(5);

        //making filter boxes
        CheckBox hasWhiteboard = new CheckBox("White board");
        hasWhiteboard.setStyle("-fx-background-color: -secondary-color; "
                                + "-fx-background-radius: 16;");
        hasWhiteboard.setPadding(new Insets(8, 16, 8, 16));
        hasWhiteboard.setPrefWidth(160);

        CheckBox hasTV = new CheckBox("TV");
        hasTV.setStyle("-fx-background-color: -secondary-color; -fx-background-radius: 16;");
        hasTV.setPadding(new Insets(8, 16, 8, 16));
        hasTV.setPrefWidth(160);

        CheckBox isFavorite = new CheckBox("Favorites");
        isFavorite.setStyle("-fx-background-color: -secondary-color; -fx-background-radius: 16;");
        isFavorite.setPadding(new Insets(8, 16, 8, 16));
        isFavorite.setPrefWidth(160);

        TextField minCap = new TextField();
        minCap.setPromptText("Min");
        minCap.setPrefWidth(50);
        TextField maxCap = new TextField();
        maxCap.setPromptText("Max");
        maxCap.setPrefWidth(50);
        HBox capacities = new HBox(10, minCap, maxCap);
        capacities.setStyle("-fx-background-color: -secondary-color; -fx-background-radius: 16;");
        capacities.setPadding(new Insets(4, 16, 4, 16));
        capacities.setPrefWidth(160);

        Button apply = new Button("Apply filters");
        apply.setStyle("-fx-background-radius: 16;");
        apply.setPadding(new Insets(8, 16, 8, 16));
        apply.setPrefWidth(130);

        Text errorMessage = new Text();

        filters.getChildren().addAll(hasWhiteboard, hasTV, isFavorite,
                capacities, apply, errorMessage);
        filters.setSpacing(5);
        horizontalBox.getChildren().add(filters);

        //container for the rooms
        VBox rooms = new VBox();

        roomsGrid = new ButtonsGridView(new ArrayList<>(), new ArrayList<>(), 4);
        rooms.getChildren().add(roomsGrid);
        roomsGrid.setListener(new ButtonsGridView.Listener() {
            @Override
            public void onButtonClicked(int buttonIndex) {
                RoutingScene routingScene = getRoutingScene();
                routingScene.pushRoute(new RoomDisplayRoute(
                        RoomsListRoute.this.roomList.get(buttonIndex)));
            }
        });

        loadRooms(
                false,
                false,
                Integer.MIN_VALUE,
                Integer.MAX_VALUE,
                buildingCode,
                isFavorite.isSelected()
        );

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

                loadRooms(
                        hasTvBool,
                        hasWhiteboardBool,
                        minCapInt,
                        maxCapInt,
                        buildingCode,
                        isFavorite.isSelected()
                );

                setRooms();
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

        // Resize layout on width change
        rootContainer.sceneProperty().addListener((obs2, oldScene, newScene) -> {
            if (newScene != null) {
                resizeDisplay(newScene.getWidth());
                newScene.widthProperty().addListener((obs, oldWidth, newWidth) -> {
                    resizeDisplay(newWidth);
                });
            }
        });
    }

    private void loadRooms(boolean hasTv, boolean hasWhiteboard,
                           int minCapInt, int maxCapInt, int buildingCode,
                           boolean isFavorite) {
        showPopup(new LoadingPopup(), false);
        new Thread(() -> {
            refreshRooms(hasTv, hasWhiteboard, minCapInt, maxCapInt, buildingCode, isFavorite);

            Platform.runLater(() -> {
                setRooms();
                removePopup();
            });
        }).start();
    }

    private void refreshRooms(boolean hasTv, boolean hasWhiteboard,
                              int minCapInt, int maxCapInt, int buildingCode,
                              boolean isFavorite) {
        RoomsListRoute.this.roomList = RoomCommunication.getAllRooms();
        for (int i = 0; i < RoomsListRoute.this.roomList.size(); i++) {
            Room room = RoomsListRoute.this.roomList.get(i);
            if ((hasTv && !room.isHasTV())
                    || (hasWhiteboard && !room.isHasWhiteboard())
                    || room.getCapacity() < minCapInt
                    || room.getCapacity() > maxCapInt
                    || (buildingCode != -1
                    && !room.getBuilding().getCode().equals(buildingCode))) {
                RoomsListRoute.this.roomList.remove(i);
                i--;
            }
        }

        if (isFavorite) {
            List<FavoriteRoom> favoriteRooms = FavoriteRoomCommunication.getAll();
            for (int i = 0; i < RoomsListRoute.this.roomList.size(); i++) {
                Room room = RoomsListRoute.this.roomList.get(i);
                boolean isFav = false;
                for (FavoriteRoom favoriteRoom : favoriteRooms) {
                    if (favoriteRoom.getRoom().getRoomCode().equals(room.getRoomCode())) {
                        isFav = true;
                        break;
                    }
                }
                if (!isFav) {
                    RoomsListRoute.this.roomList.remove(i);
                    i--;
                }
            }
        }

        images = new ArrayList<>();
        texts = new ArrayList<>();
        for (Room room : roomList) {
            images.add(new Image(ImageCommunication.getRoomImageUrl(room.getRoomCode()).get(0)));
            texts.add(room.getName());
        }
    }

    private void setRooms() {
        roomsGrid.setButtons(images, texts);
    }

    private void resizeDisplay(Number newWidth) {
        roomsGrid.setPrefWidth(newWidth.doubleValue() * 0.85);
    }
}
