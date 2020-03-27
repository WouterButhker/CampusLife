package nl.tudelft.oopp.demo.controllers;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.communication.ImageCommunication;
import nl.tudelft.oopp.demo.communication.RoomCommunication;
import nl.tudelft.oopp.demo.core.Route;
import nl.tudelft.oopp.demo.core.RoutingScene;
import nl.tudelft.oopp.demo.core.XmlRoute;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.widgets.AppBar;
import nl.tudelft.oopp.demo.widgets.ImageSelectorWidget;

public class AdminSceneRoomsController implements Initializable {

    @FXML
    private VBox mainBox;

    @FXML
    private TextField roomCodeInput;

    @FXML
    private TextField roomNameInput;

    @FXML
    private TextField capacityInput;

    @FXML
    private ChoiceBox<String> buildingList;

    @FXML
    private ChoiceBox<String> rightsList;

    @FXML
    private CheckBox whiteboardBox;

    @FXML
    private CheckBox tvBox;

    @FXML
    private Button submit;

    @FXML
    private ChoiceBox<String> buildingList2;

    @FXML
    private AnchorPane anchorPaneRooms;

    @FXML
    private VBox scrollPaneVBox;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox roomsList;

    @FXML
    private VBox settingsBox;

    private ImageSelectorWidget imageSelectorWidget;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadBuildings();
        loadRights();
        loadImageSelectorWidget();
        addAppBar();
    }

    private void addAppBar() {
        mainBox.getChildren().add(0, new AppBar());
    }

    private void loadBuildings() {
        if (buildingList != null) {
            buildingList.getItems().addAll(BuildingCommunication.getBuildingsCodeAndName());
        }
        if (buildingList2 != null) {
            buildingList2.getItems().add("All buildings");
            buildingList2.getItems().addAll(BuildingCommunication.getBuildingsCodeAndName());
            SingleSelectionModel<String> selectionModel = buildingList2.getSelectionModel();
            selectionModel.selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable,
                                    String oldValue, String newValue) {
                    loadRooms(newValue);
                }
            });
            buildingList2.setValue("All buildings");
        }
    }

    private void loadRights() {
        if (rightsList != null) {
            String[] rights = new String[3];
            rights[0] = "Student";
            rights[1] = "Employee";
            rights[2] = "Admin";
            rightsList.getItems().addAll(rights);
        }
    }

    private void loadImageSelectorWidget() {
        imageSelectorWidget = new ImageSelectorWidget();
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().add(imageSelectorWidget);
        box.setPadding(new Insets(10));
        settingsBox.getChildren().add(2, box);
    }

    @FXML
    private void submitNewRoom() {
        int capacity = 0;
        boolean capacityCorrect = false;
        try {
            capacity = Integer.parseInt(capacityInput.getText().trim());
            capacityCorrect = true;
        } catch (NumberFormatException e) {
            System.out.println("Not a proper number");
        }

        boolean buildingFound = false;
        int buildingCode = 0;
        if (buildingList.getValue() != null) {
            buildingCode = Integer.parseInt(buildingList.getValue().split(" ")[0]);
            buildingFound = true;
        } else {
            System.out.println("No building selected");
        }

        String rightsString = rightsList.getValue();
        int rights = 0;
        boolean rightsFound = false;
        //This if statement allows to add more levels of rights in the long term
        if (rightsString != null) {
            rightsFound = true;
            if (rightsString.equals("Student")) {
                rights = 0;
            }
            if (rightsString.equals("Employee")) {
                rights = 1;
            }
            if (rightsString.equals("Admin")) {
                rights = 2;
            }
        } else {
            System.out.println("No rights selected");
        }

        boolean whiteboard = whiteboardBox.selectedProperty().get();
        boolean tv = tvBox.selectedProperty().get();

        String roomCode = roomCodeInput.getText();
        String roomName = roomNameInput.getText();

        Text submitStatus = new Text();
        if (!roomCode.equals("") && !roomName.equals("") && capacityCorrect
                && buildingFound && rightsFound && imageSelectorWidget.imageSelected()) {
            Room room = new Room(roomCode, roomName, capacity, whiteboard,
                    tv, rights, BuildingCommunication.getBuildingByCode(buildingCode));
            RoomCommunication.saveRoom(room);
            ImageCommunication.updateRoomImage(roomCode, imageSelectorWidget.getImage());
            submitStatus.setText("Room has been successfully added to "
                    + buildingList.getValue().split(" ")[1]);
            try {
                refreshRoomsPage();
            } catch (Exception e) {
                System.out.println("Refresh failed");
            }
        } else {
            submitStatus.setText("All fields have to be entered");
        }

        if (!capacityCorrect) {
            submitStatus.setText("The capacity has to be a proper number");
        }

        if (!buildingFound) {
            submitStatus.setText("Please select a building");
        }

        if (!rightsFound) {
            submitStatus.setText("Rights have to be set");
        }

        if (!imageSelectorWidget.imageSelected()) {
            submitStatus.setText("Image has to be selected");
        }

        Button back = new Button("Okay! take me back");
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Button button = (Button) event.getSource();
                Stage stage = (Stage) button.getScene().getWindow();
                stage.close();
            }
        });
        VBox roomBox = new VBox(submitStatus, back);
        roomBox.setPrefSize(300, 200);
        roomBox.setAlignment(Pos.CENTER);
        AnchorPane root = new AnchorPane(roomBox);
        root.setPrefSize(300, 200);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        //stage.initOwner(buildingList.getScene().getWindow());
        stage.showAndWait();
    }

    @FXML
    private void refreshRoomsPage() throws Exception {
        RoutingScene scene = (RoutingScene) mainBox.getScene();
        Route route = new XmlRoute(getClass().getResource("/AdminSceneRooms.fxml"));
        scene.popRoute();
        scene.pushRoute(route);
    }

    private void loadRooms(String buildingCodeString) {
        roomsList.getChildren().clear();
        int buildingCode;
        if (buildingCodeString.equals("All buildings")) {
            buildingCode = -1;
        } else {
            buildingCode = Integer.parseInt(buildingCodeString.split(" ")[0]);
        }
        List<Room> rooms;
        if (buildingCode == -1) {
            rooms = RoomCommunication.getAllRooms();
        } else {
            rooms = RoomCommunication.getAllRoomsFromBuilding(buildingCode);
        }
        int numRooms = rooms.size();
        int height = numRooms * 82;
        anchorPaneRooms.setPrefHeight(height);
        if (height <= scrollPane.getPrefHeight()) {
            scrollPaneVBox.setPrefWidth(400);
            scrollPane.setPrefWidth(400);
        } else {
            scrollPaneVBox.setPrefWidth(417);
            scrollPane.setPrefWidth(417);
        }
        for (int i = 0; i < numRooms; i++) {
            HBox room = new HBox();
            room.setMaxWidth(400);
            Image image = new Image(ImageCommunication
                    .getRoomImageUrl(rooms.get(i).getRoomCode()).get(0));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(65);
            imageView.setFitHeight(60);
            int rights = rooms.get(i).getRights();
            String rightsString = "Student";
            if (rights == 1) {
                rightsString = "Employee";
            }
            if (rights == 2) {
                rightsString = "Admin";
            }
            Label text = new Label("Building Code: " + rooms.get(i).getBuilding().getCode()
                    + " | Room Code: "  + rooms.get(i).getRoomCode() + "\n" + rooms.get(i).getName()
                    + " " + rightsString + " capacity: " + rooms.get(i).getCapacity()
                    + "\nWhiteboard: " + rooms.get(i).isHasWhiteboard() + " TV: "
                    + rooms.get(i).isHasTV());
            text.setPrefSize(225, 60);
            text.setPadding(new Insets(0, 0, 0, 10));
            Button modify = new Button("modify");
            int finalI = i;
            modify.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    createModifyPopup(rooms.get(finalI));
                }
            });
            modify.setPrefSize(45, 40);
            modify.setPadding(new Insets(0, 0, 0,0));
            StackPane modifyPane = new StackPane(modify);
            modifyPane.setPadding(new Insets(10, 0, 10, 0));
            Button delete = new Button("delete");
            delete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    RoomCommunication.deleteRoom(rooms.get(finalI).getRoomCode());
                    loadRooms(buildingList2.getValue());
                }
            });
            delete.setPrefSize(45, 40);
            delete.setPadding(new Insets(0, 0,0,0));
            StackPane deletePane = new StackPane(delete);
            deletePane.setPadding(new Insets(10, 0, 10, 0));
            room.setPadding(new Insets(5, 5, 5,5));
            String css = "-fx-border-color: black;\n"
                    + "-fx-border-insets: 4\n;"
                    + "-fx-border-style: solid\n;"
                    + "-fx-border-width: 1;"
                    + "-fx-border-radius: 10;";
            room.setStyle(css);
            room.getChildren().addAll(imageView, text, modifyPane, deletePane);
            roomsList.getChildren().add(room);
        }
    }

    private void createModifyPopup(Room room) {
        VBox root = new VBox();
        root.setPrefSize(400, 500);

        Text header = new Text("Modify your room");
        header.setFont(Font.font("System", 24));
        HBox headerBox = new HBox(header);
        headerBox.setPadding(new Insets(20, 125,10,125));

        Label roomCode = new Label("Room Code :");
        HBox roomCodeBox = new HBox(roomCode);
        roomCodeBox.setPadding(new Insets(20, 175,0,175));

        Pane spacer1 = new Pane();
        spacer1.setPrefSize(125, 20);
        TextField roomCodeField = new TextField(room.getRoomCode());
        roomCodeField.setPrefSize(150,20);
        roomCodeField.setEditable(false);
        HBox roomCodeFieldBox = new HBox(spacer1, roomCodeField);
        roomCodeFieldBox.setPadding(new Insets(10, 0, 0, 0));

        Label nameText = new Label("Name :");
        HBox nameTextBox = new HBox(nameText);
        nameTextBox.setPadding(new Insets(10, 175,0,175));

        Pane spacer2 = new Pane();
        spacer2.setPrefSize(125, 20);
        TextField name = new TextField(room.getName());
        name.setPrefSize(150, 20);
        HBox nameBox = new HBox(spacer2, name);
        nameBox.setPadding(new Insets(10, 0, 0, 0));

        Label capacityText = new Label("Capacity :");
        HBox capacityTextBox = new HBox(capacityText);
        capacityTextBox.setPadding(new Insets(10, 175,0,175));

        Pane spacer3 = new Pane();
        spacer3.setPrefSize(125, 20);
        TextField capacity = new TextField(Integer.toString(room.getCapacity()));
        capacity.setPrefSize(150, 20);
        HBox capacityBox = new HBox(spacer3, capacity);
        capacityBox.setPadding(new Insets(10, 0, 10, 0));

        Pane spacer6 = new Pane();
        spacer6.setPrefSize(150, 20);
        ChoiceBox<String> rights = new ChoiceBox<String>();
        rights.setPrefSize(100, 20);
        rights.getItems().addAll("Student", "Employee", "Admin");
        int rightsInt = room.getRights();
        switch (rightsInt) {
            case 0 : rights.setValue("Student");
                     break;
            case 1 : rights.setValue("Employee");
                     break;
            case 2 : rights.setValue("Admin");
                     break;
            default: rights.setValue("Student");
        }
        HBox rightsBox = new HBox(spacer6, rights);
        rightsBox.setPadding(new Insets(10, 0, 10, 0));

        Pane spacer4 = new Pane();
        spacer4.setPrefSize(150, 30);
        CheckBox whiteboard = new CheckBox("Whiteboard");
        whiteboard.setSelected(room.isHasWhiteboard());
        HBox whiteboardBox = new HBox(spacer4, whiteboard);
        whiteboardBox.setPadding(new Insets(10, 0, 0,0));

        Pane spacer5 = new Pane();
        spacer5.setPrefSize(150, 30);
        CheckBox tv = new CheckBox("TV");
        tv.setSelected(room.isHasTV());
        HBox tvBox = new HBox(spacer5, tv);
        tvBox.setPadding(new Insets(10, 0, 0,0));

        HBox imageSelectorWidgetBox = new HBox();
        imageSelectorWidgetBox.setAlignment(Pos.CENTER);
        ImageSelectorWidget imageSelectorWidget = new ImageSelectorWidget();
        imageSelectorWidgetBox.getChildren().add(imageSelectorWidget);
        //imageSelectorWidget.setImage(ImageCommunication.getRoomImageUrl(room.getRoomCode()));

        Button submit = new Button("submit");
        submit.setPrefSize(100, 20);
        HBox submitBox = new HBox(submit);
        submitBox.setPadding(new Insets(10, 150,10, 150));
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Node status = modifyRoom(room.getRoomCode(), name.getText(), capacity.getText(),
                        whiteboard.selectedProperty().get(), tv.selectedProperty().get(),
                        rights.getValue(), room.getBuilding().getCode(),
                        imageSelectorWidget.getImage());
                if (status == null) {
                    Button button = (Button) event.getSource();
                    Stage stage = (Stage) button.getScene().getWindow();
                    stage.close();
                    loadRooms(buildingList2.getValue());
                } else {
                    try {
                        root.getChildren().remove(12);
                        root.getChildren().add(status);
                    } catch (IndexOutOfBoundsException e) {
                        root.getChildren().add(status);
                    }
                }
            }
        });

        root.getChildren().addAll(headerBox, roomCodeBox, roomCodeFieldBox, nameTextBox,
                nameBox, capacityTextBox, capacityBox, rightsBox,
                whiteboardBox, tvBox, imageSelectorWidgetBox, submitBox);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(buildingList.getScene().getWindow());
        stage.showAndWait();
    }

    private Node modifyRoom(String roomCode, String roomName, String capacity,
                             boolean whiteboard, boolean tv, String rights, int buildingCode,
                            File image) {
        int rightsNum = 0;
        Label message = null;
        if (rights.equals("Student")) {
            rightsNum = 0;
        }
        if (rights.equals("Employee")) {
            rightsNum = 1;
        }
        if (rights.equals("Admin")) {
            rightsNum = 2;
        }

        int capacityInt = -1;
        try {
            capacityInt = Integer.parseInt(capacity.trim());
        } catch (NumberFormatException e) {
            message = new Label("The capacity is not a number");
        }

        if (!roomCode.equals("") && !roomName.equals("")) {
            Room room = new Room(roomCode, roomName, capacityInt, whiteboard,
                    tv, rightsNum, BuildingCommunication.getBuildingByCode(buildingCode));
            RoomCommunication.updateRoom(room);
            ImageCommunication.updateRoomImage(roomCode, image);
        } else {
            message = new Label("All fields have to be entered");
        }
        HBox res = null;
        if (message != null) {
            message.setStyle("-fx-text-fill: red");
            res = new HBox();
            res.setAlignment(Pos.CENTER);
            res.getChildren().add(message);
        }
        return res;
    }

    @FXML
    private void upload() {
        //
    }
}
