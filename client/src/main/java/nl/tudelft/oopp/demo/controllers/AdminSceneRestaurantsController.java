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
import nl.tudelft.oopp.demo.communication.RestaurantCommunication;
import nl.tudelft.oopp.demo.core.Route;
import nl.tudelft.oopp.demo.core.RoutingScene;
import nl.tudelft.oopp.demo.core.XmlRoute;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.widgets.AppBar;
import nl.tudelft.oopp.demo.widgets.ImageSelectorWidget;

public class AdminSceneRestaurantsController implements Initializable {
    @FXML
    private VBox mainBox;

    @FXML
    private TextField restaurantNameInput;

    @FXML
    private TextField restaurantDescriptionInput;

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

    @FXML
    private ImageSelectorWidget imageSelectorWidget;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadBuildings();
        // loadImageSelectorWidget();
        addAppBar();
    }

    private void addAppBar() {
        mainBox.getChildren().add(0, new AppBar());
    }

    private void loadBuildings() throws NullPointerException {
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

    /*
    private void loadImageSelectorWidget() {
        imageSelectorWidget = new ImageSelectorWidget();
        imageSelectorBox.getChildren().add(imageSelectorWidget);
    }*/

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

        int restaurantID = 0;

        String restaurantName = restaurantNameInput.getText();

        String restaurantDescription = restaurantDescriptionInput.getText();

        Text submitStatus = new Text();

        if (!restaurantName.equals("") && buildingFound && imageSelectorWidget.imageSelected()) {
            Restaurant result = RestaurantCommunication.createRestaurant(new Restaurant(restaurantID,
                                                                    restaurantName,
                                                                    buildingCode,
                                                                    restaurantDescription));
            ImageCommunication.updateRestaurantImage(result.getId(), imageSelectorWidget.getImage());
            submitStatus.setText("Restaurant has been successfully added to "
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
            restaurants = RestaurantCommunication.getRestaurants();
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
            Image image = new Image(ImageCommunication
                    .getRestaurantImageUrl(restaurants.get(i).getId()).get(0));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(65);
            imageView.setFitHeight(60);
            Label text = new Label("Building Code: " + restaurants.get(i).getBuildingCode()
                    + "\n" + "Restaurant ID: " + restaurants.get(i).getId()
                    + "\n" + restaurants.get(i).getName()
                    + " - " + restaurants.get(i).getDescription());
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
            modify.setPadding(new Insets(0, 0, 0, 0));
            StackPane modifyPane = new StackPane(modify);
            modifyPane.setPadding(new Insets(10, 0, 10, 0));
            Button delete = new Button("delete");
            delete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    RestaurantCommunication.deleteRestaurantFromDatabase(
                            restaurants.get(finalI).getId());
                    loadRestaurants(buildingList2.getValue());
                }
            });
            delete.setPrefSize(45, 40);
            delete.setPadding(new Insets(0, 0, 0, 0));
            StackPane deletePane = new StackPane(delete);
            deletePane.setPadding(new Insets(10, 0, 10, 0));
            restaurant.setPadding(new Insets(5, 5, 5, 5));
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
    private void refreshRestaurantsPage() throws Exception {
        RoutingScene scene = (RoutingScene) mainBox.getScene();
        Route route = new XmlRoute(getClass().getResource("/AdminSceneRestaurants.fxml"));
        scene.popRoute();
        scene.pushRoute(route);
    }

    private void createModifyPopup(Restaurant restaurant) {
        VBox root = new VBox();
        root.setPrefSize(400, 500);

        Text header = new Text("Modify your restaurant");
        header.setFont(Font.font("System", 24));
        HBox headerBox = new HBox(header);
        headerBox.setPadding(new Insets(20, 125, 10, 125));

        // Name
        Label nameText = new Label("Name :");
        HBox nameTextBox = new HBox(nameText);
        nameTextBox.setPadding(new Insets(10, 175, 0, 175));

        Pane spacer1 = new Pane();
        spacer1.setPrefSize(125, 20);
        TextField name = new TextField(restaurant.getName());
        name.setPrefSize(150, 20);
        HBox nameBox = new HBox(spacer1, name);
        nameBox.setPadding(new Insets(10, 0, 0, 0));

        // Description
        Label descriptionText = new Label("Description :");
        HBox descriptionTextBox = new HBox(descriptionText);
        descriptionTextBox.setPadding(new Insets(10, 175, 0, 175));

        Pane spacer2 = new Pane();
        spacer2.setPrefSize(125, 20);
        TextField description = new TextField(restaurant.getDescription());
        description.setPrefSize(150, 20);
        final HBox descriptionBox = new HBox(spacer2, description);
        nameBox.setPadding(new Insets(10, 0, 0, 0));

        // Images
        HBox images = new HBox();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(images);
        HBox imagesBox = new HBox(scrollPane);
        imagesBox.setPadding(new Insets(10, 0, 10,0));
        imagesBox.setAlignment(Pos.CENTER);
        imagesBox.setPrefHeight(110);
        images.setStyle("-fx-background-color: -primary-color");
        images.setMinWidth(275);
        loadImages(restaurant, images);
        scrollPane.setFitToHeight(true);
        scrollPane.setMinViewportHeight(112);
        scrollPane.setPrefViewportHeight(112);
        scrollPane.setMinViewportWidth(275);
        scrollPane.setPrefViewportWidth(275);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setOnScroll(event -> {
            if (event.getDeltaX() == 0 && event.getDeltaY() != 0) {
                scrollPane.setHvalue(scrollPane.getHvalue()
                        - event.getDeltaY() / imagesBox.getWidth());
            }
        });

        HBox imageSelectorWidgetBox = new HBox();
        imageSelectorWidgetBox.setAlignment(Pos.CENTER);
        ImageSelectorWidget imageSelectorWidget = new ImageSelectorWidget();
        Button submitImage = new Button("Add image");
        HBox.setMargin(submitImage, new Insets(0, 0, 0,10));
        submitImage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Node status = addImage(restaurant, imageSelectorWidget.getImage());
                if (status != null) {
                    try {
                        root.getChildren().remove(13);
                        root.getChildren().add(status);
                    } catch (IndexOutOfBoundsException e) {
                        root.getChildren().add(status);
                    }
                } else {
                    loadImages(restaurant, images);
                    imageSelectorWidget.reset();
                }
            }
        });
        imageSelectorWidgetBox.getChildren().addAll(imageSelectorWidget, submitImage);

        // Submit button
        Button submit = new Button("Submit");
        submit.setPrefSize(100, 20);
        HBox submitBox = new HBox(submit);
        submitBox.setPadding(new Insets(10, 150, 10, 150));
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Label status = updateRestaurant(restaurant.getId(),
                        name.getText(),
                        restaurant.getBuildingCode(),
                        description.getText());
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

        root.getChildren().addAll(headerBox,
                nameTextBox, nameBox,
                descriptionTextBox, descriptionBox,
                submitBox);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(buildingList.getScene().getWindow());
        stage.showAndWait();
    }

    private Label updateRestaurant(int id, String name, int buildingCode, String description) {
        if (!name.equals("") && !description.equals("")) {
            RestaurantCommunication.updateRestaurant(new Restaurant(id,
                                                                    name,
                                                                    buildingCode,
                                                                    description));
        } else {
            return new Label("All fields have to be entered");
        }

        return null;
    }

    private Node addImage(Restaurant restaurant, File image) {
        if (image != null) {
            ImageCommunication.updateRestaurantImage(restaurant.getId(), image);
            return null;
        }
        Label message = new Label("An image has to be selected");


        message.setStyle("-fx-text-fill: red");
        HBox res = new HBox(message);
        res.setAlignment(Pos.CENTER);
        return res;
    }

    private void loadImages(Restaurant restaurant, HBox images) {
        images.getChildren().clear();
        List<String> imageUrls = ImageCommunication.getRestaurantImageUrl(restaurant.getId());
        for (int i = 0; i < imageUrls.size(); i++) {
            Image image = new Image(imageUrls.get(i));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            Button delete = new Button("X");
            delete.setPrefSize(20, 20);
            HBox deleteContainer = new HBox(delete);
            deleteContainer.setPrefHeight(20);
            deleteContainer.setAlignment(Pos.TOP_RIGHT);
            deleteContainer.setPadding(new Insets(5, 5, 0, 0));
            int finalI = i;
            delete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ImageCommunication.deleteRestaurantImage(imageUrls.get(finalI));
                    loadImages(restaurant, images);
                }
            });
            StackPane container = new StackPane(imageView, deleteContainer);
            container.setPrefSize(100, 100);
            container.setPadding(new Insets(5));
            images.getChildren().add(container);
        }
    }

}
