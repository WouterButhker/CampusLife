package nl.tudelft.oopp.demo.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import nl.tudelft.oopp.demo.communication.RestaurantCommunication;
import nl.tudelft.oopp.demo.communication.RoomCommunication;
import nl.tudelft.oopp.demo.core.Route;
import nl.tudelft.oopp.demo.core.RoutingScene;
import nl.tudelft.oopp.demo.core.XmlRoute;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.widgets.AppBar;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminSceneRestaurantsController implements Initializable {
    @FXML
    private VBox mainBox;

    @FXML
    private TextField restaurantNameInput;

    @FXML
    private TextField restaurantOpeningHoursInput;

    @FXML
    private ChoiceBox<String> buildingList;

    @FXML
    private Button submit;

    @FXML
    private ChoiceBox<String> buildingList2;

    @FXML
    private AnchorPane anchorPaneRestaurants;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox restaurantsList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadBuildings();
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
                    loadRestaurants(newValue);
                }
            });
            buildingList2.setValue("All buildings");
        }
    }

    private void loadRestaurants(String buildingCodeString) {
        restaurantsList.getChildren().clear();
        int buildingCode;
        if (buildingCodeString.equals("All buildings")) {
            buildingCode = -1;
        } else {
            buildingCode = Integer.parseInt(buildingCodeString.split(" ")[0]);
        }
        List<Restaurant> restaurants;
        if (buildingCode == -1) {
            restaurants = RestaurantCommunication.getAllRestaurants();
        } else {
            restaurants = RestaurantCommunication.getAllRestaurantsFromBuilding(buildingCode);
        }
        int numRestaurants = restaurants.size();
        int height = numRestaurants * 82;
        anchorPaneRestaurants.setPrefHeight(height);
        if (height <= scrollPane.getPrefHeight()) {
            scrollPane.setPrefWidth(400);
        } else {
            scrollPane.setPrefWidth(417);
        }
        for (int i = 0; i < numRestaurants; i++) {
            HBox restaurant = new HBox();
            restaurant.setMaxWidth(400);
            Image image = new Image("images/RoomTempIMG.jpg");
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(65);
            imageView.setFitHeight(60);
            Label text = new Label("Building Code: " + restaurants.get(i).getBuildingCode()
                   + "\n" + restaurants.get(i).getName());
            text.setPrefSize(225, 60);
            text.setPadding(new Insets(0, 0, 0, 10));
            Button modify = new Button("modify");
            int finalI = i;
            modify.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    createModifyPopup(restaurants.get(finalI));
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
                    RestaurantCommunication.deleteRestaurantFromDatabase(restaurants.get(finalI).getName());
                    loadRestaurants(buildingList2.getValue());
                }
            });
            delete.setPrefSize(45, 40);
            delete.setPadding(new Insets(0, 0,0,0));
            StackPane deletePane = new StackPane(delete);
            deletePane.setPadding(new Insets(10, 0, 10, 0));
            restaurant.setPadding(new Insets(5, 5, 5,5));
            String css = "-fx-border-color: black;\n"
                    + "-fx-border-insets: 4\n;"
                    + "-fx-border-style: solid\n;"
                    + "-fx-border-width: 1;"
                    + "-fx-border-radius: 10;";
            restaurant.setStyle(css);
            restaurant.getChildren().addAll(imageView, text, modifyPane, deletePane);
            restaurantsList.getChildren().add(restaurant);
        }
    }

    @FXML
    private void submitNewRestaurant() {
        boolean buildingFound = false;
        int buildingCode = 0;
        if (buildingList.getValue() != null) {
            buildingCode = Integer.parseInt(buildingList.getValue().split(" ")[0]);
            buildingFound = true;
        } else {
            System.out.println("No building selected");
        }

        String restaurantName = restaurantNameInput.getText();

        String restaurantOpeningHours = restaurantOpeningHoursInput.getText();

        Text submitStatus = new Text();

        if (!restaurantName.equals("") && buildingFound) {
            RestaurantCommunication.addRestaurantToDatabase(restaurantName, buildingCode, restaurantOpeningHours);
            submitStatus.setText("Room has been successfully added to "
                    + buildingList.getValue().split(" ")[1]);
            try {
                refreshRestaurantsPage();
            } catch (Exception e) {
                System.out.println("Refresh failed");
            }
        } else {
            submitStatus.setText("All fields have to be entered");
        }

        if (!buildingFound) {
            submitStatus.setText("Please select a building");
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
        VBox restaurantBox = new VBox(submitStatus, back);
        restaurantBox.setPrefSize(300, 200);
        restaurantBox.setAlignment(Pos.CENTER);
        AnchorPane root = new AnchorPane(restaurantBox);
        root.setPrefSize(300, 200);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    private void refreshRestaurantsPage() throws Exception {
        RoutingScene scene = (RoutingScene) mainBox.getScene();
        Route route = new XmlRoute(getClass().getResource("/AdminSceneFood.fxml"));
        scene.popRoute();
        scene.pushRoute(route);
    }

    private void createModifyPopup(Restaurant restaurant) {
        VBox root = new VBox();
        root.setPrefSize(400, 500);

        Text header = new Text("Modify your room");
        header.setFont(Font.font("System", 24));
        HBox headerBox = new HBox(header);
        headerBox.setPadding(new Insets(20, 125,10,125));

        Label nameText = new Label("Name :");
        HBox nameTextBox = new HBox(nameText);
        nameTextBox.setPadding(new Insets(10, 175,0,175));

        Pane spacer = new Pane();
        spacer.setPrefSize(125, 20);
        TextField name = new TextField(restaurant.getName());
        name.setPrefSize(150, 20);
        HBox nameBox = new HBox(spacer, name);
        nameBox.setPadding(new Insets(10, 0, 0, 0));

        Label openingHoursText = new Label("Opening hours :");
        HBox openingHoursTextBox = new HBox(openingHoursText);
        openingHoursTextBox.setPadding(new Insets(10, 175,0,175));

        Pane spacer2 = new Pane();
        spacer2.setPrefSize(125, 20);
        TextField openingHours = new TextField(restaurant.getOpeningHours());
        openingHours.setPrefSize(150, 20);
        HBox openingHoursBox = new HBox(spacer2, openingHours);
        nameBox.setPadding(new Insets(10, 0, 0, 0));

        Button submit = new Button("Submit");
        submit.setPrefSize(100, 20);
        HBox submitBox = new HBox(submit);
        submitBox.setPadding(new Insets(10, 150,10, 150));
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Label status = modifyRestaurant(name.getText(), restaurant.getBuildingCode(), openingHours.getText());
                if (status == null) {
                    Button button = (Button) event.getSource();
                    Stage stage = (Stage) button.getScene().getWindow();
                    stage.close();
                    loadRestaurants(buildingList2.getValue());
                } else {
                    status.setPadding(new Insets(10, 125, 0, 125));
                    try {
                        root.getChildren().remove(11);
                        root.getChildren().add(status);
                    } catch (IndexOutOfBoundsException e) {
                        root.getChildren().add(status);
                    }
                }
            }
        });

        root.getChildren().addAll(headerBox, nameTextBox, nameBox, submitBox);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(buildingList.getScene().getWindow());
        stage.showAndWait();
    }

    private Label modifyRestaurant(String name, int buildingCode, String openingHours) {
        if (!name.equals("")) {
            RestaurantCommunication.addRestaurantToDatabase(name, buildingCode, openingHours);
        } else {
            return new Label("All fields have to be entered");
        }

        return null;
    }

}
