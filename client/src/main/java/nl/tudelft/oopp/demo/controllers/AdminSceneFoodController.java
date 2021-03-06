package nl.tudelft.oopp.demo.controllers;

import java.net.URL;
import java.text.DecimalFormat;
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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.FoodCommunication;
import nl.tudelft.oopp.demo.communication.RestaurantCommunication;
import nl.tudelft.oopp.demo.core.Route;
import nl.tudelft.oopp.demo.core.RoutingScene;
import nl.tudelft.oopp.demo.core.XmlRoute;
import nl.tudelft.oopp.demo.entities.Food;
import nl.tudelft.oopp.demo.widgets.AppBar;
import nl.tudelft.oopp.demo.widgets.PopupWidget;

public class AdminSceneFoodController implements Initializable {
    @FXML
    private VBox mainBox;

    @FXML
    private TextField foodNameInput;

    @FXML
    private ChoiceBox<String> restaurantsList1;

    @FXML
    private TextField foodPriceInput;
    
    @FXML
    private ChoiceBox<String> restaurantsList2;

    @FXML
    private AnchorPane anchorPaneFood;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox foodsList;

    @FXML
    private Button submit;

    @FXML
    private Button refresh;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadRestaurants();
        addAppBar();
        addStyle();
    }

    private void addStyle() {
        mainBox.getStylesheets().add("css/admin-scene.css");
        //mainBox.setStyle("-fx-background-color: -primary-color-light");
    }

    private void addAppBar() {
        mainBox.getChildren().add(0, new AppBar());
    }

    private void loadRestaurants() throws NullPointerException {
        if (restaurantsList1 != null) {
            restaurantsList1.getItems().addAll(RestaurantCommunication.getRestaurantsIdAndName());
        }
        if (restaurantsList2 != null) {
            restaurantsList2.getItems().add("All restaurants");
            restaurantsList2.getItems().addAll(RestaurantCommunication.getRestaurantsIdAndName());
            SingleSelectionModel<String> selectionModel = restaurantsList2.getSelectionModel();
            selectionModel.selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable,
                                    String oldValue, String newValue) {
                    loadFoods(newValue);
                }
            });
            restaurantsList2.setValue("All restaurants");
        }
    }

    private void loadFoods(String restaurantIdString) {
        DecimalFormat df2 = new DecimalFormat("#.##");
        foodsList.getChildren().clear();
        int restaurantId;
        if (restaurantIdString.equals("All restaurants")) {
            restaurantId = -1;
        } else {
            restaurantId = Integer.parseInt(restaurantIdString.split(" ")[0]);
        }
        List<Food> foods;
        if (restaurantId != -1) {
            foods = RestaurantCommunication.getAllFood(restaurantId);
        } else {
            foods = FoodCommunication.getFoods();
        }
        assert foods != null;
        int numRestaurants = foods.size();
        int height = numRestaurants * 82;
        anchorPaneFood.setPrefHeight(height);
        if (height <= scrollPane.getPrefHeight()) {
            scrollPane.setPrefWidth(400);
        } else {
            scrollPane.setPrefWidth(417);
        }
        for (int i = 0; i < numRestaurants; i++) {
            HBox restaurant = new HBox();
            restaurant.setMaxWidth(400);
            Image image = new Image("images/main-screen-food.jpg");
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(65);
            imageView.setFitHeight(60);
            Label text = new Label("Restaurant id: " + foods.get(i).getRestaurant()
                    + "\n" + "Food ID: " + foods.get(i).getId()
                    + "\n" + foods.get(i).getName()
                    + " - " + String.format("€ %.2f", foods.get(i).getPrice()));
            text.setPrefSize(225, 60);
            text.setPadding(new Insets(0, 0, 0, 10));
            Button modify = new Button("modify");
            modify.getStyleClass().add("adminButtonSmall");
            int finalI = i;
            modify.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    createModifyPopup(foods.get(finalI));
                }
            });
            modify.setPrefSize(45, 40);
            modify.setPadding(new Insets(0, 0, 0, 0));
            StackPane modifyPane = new StackPane(modify);
            modifyPane.setPadding(new Insets(10, 0, 10, 0));
            Button delete = new Button("delete");
            delete.getStyleClass().add("adminButtonSmall");
            delete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    boolean confirmation = PopupWidget.displayBool("Are you sure about deleting "
                            + "this?\nThe change will be irreversible.", "Confirmation");
                    if (confirmation) {
                        FoodCommunication.deleteFood(
                                foods.get(finalI).getId());
                        loadFoods(restaurantsList2.getValue());
                    }
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
                    + "-fx-border-radius: 10";
            restaurant.setStyle(css);
            restaurant.getChildren().addAll(imageView, text, modifyPane, deletePane);
            foodsList.getChildren().add(restaurant);
        }
    }

    private void createModifyPopup(Food food) {
        VBox root = new VBox();
        //root.setStyle("-fx-background-color: -primary-color");
        root.setPrefSize(400, 500);

        Text header = new Text("Modify the food item");
        header.setFont(Font.font("System", 24));
        HBox headerBox = new HBox(header);
        headerBox.setPadding(new Insets(20, 125, 10, 125));

        Label nameText = new Label("Name :");
        HBox nameTextBox = new HBox(nameText);
        nameTextBox.setPadding(new Insets(10, 175, 0, 175));

        Pane spacer1 = new Pane();
        spacer1.setPrefSize(125, 20);
        TextField name = new TextField(food.getName());
        name.setPrefSize(150, 20);
        HBox nameBox = new HBox(spacer1, name);
        nameBox.setPadding(new Insets(10, 0, 0, 0));

        Label priceText = new Label("Price :");
        HBox priceTextBox = new HBox(priceText);
        priceTextBox.setPadding(new Insets(10, 175, 0, 175));

        Pane spacer2 = new Pane();
        spacer2.setPrefSize(125, 20);
        TextField price = new TextField(food.getPrice().toString());
        price.setPrefSize(150, 20);
        final HBox priceBox = new HBox(spacer2, price);
        nameBox.setPadding(new Insets(10, 0, 0, 0));

        Button submit = new Button("Submit");
        submit.getStyleClass().add("adminButtonSmall");
        submit.setPrefSize(100, 20);
        HBox submitBox = new HBox(submit);
        submitBox.setPadding(new Insets(10, 150, 10, 150));
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Label status = updateFood(food.getId(),
                        name.getText(),
                        food.getRestaurant(),
                        Double.parseDouble(price.getText()));
                if (status == null) {
                    Button button = (Button) event.getSource();
                    Stage stage = (Stage) button.getScene().getWindow();
                    stage.close();
                    loadFoods(restaurantsList2.getValue());
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
                priceTextBox, priceBox,
                submitBox);
        Stage stage = new Stage();
        stage.setTitle("Modifying " + food.getName());
        stage.getIcons().add(new Image("images/modifyingImage.png"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("css/palette.css");
        scene.getStylesheets().add("css/admin-scene.css");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(restaurantsList1.getScene().getWindow());
        stage.showAndWait();
    }

    private Label updateFood(int id, String name, int restaurantId, double price) {
        if (name.equals("") || price != 0.00) {
            FoodCommunication.updateFood(new Food(id,
                                            name,
                                            restaurantId,
                                            price));
        } else {
            return new Label("All fields have to be entered");
        }

        return null;
    }

    @FXML
    private void submitNewFood() {
        boolean restaurantFound = false;
        int restaurantId = 0;
        if (restaurantsList1.getValue() != null) {
            restaurantId = Integer.parseInt(restaurantsList1.getValue().split(" ")[0]);
            restaurantFound = true;
        } else {
            System.out.println("No restaurants selected");
        }

        int foodID = 0;

        String foodName = foodNameInput.getText();

        String submitStatus;
        boolean correct = false;

        try {
            Double foodPrice = Double.parseDouble(foodPriceInput.getText());

            if (!foodName.equals("") && restaurantFound) {
                FoodCommunication.createFood(new Food(foodID,
                        foodName,
                        restaurantId,
                        foodPrice));
                submitStatus = "Food has been successfully added to \n"
                        + restaurantsList1.getValue().split(",")[0];
                correct = true;
                try {
                    refreshFoodPage();
                } catch (Exception e) {
                    System.out.println("Refresh failed");
                }
            } else {
                submitStatus = ("All fields have to be entered");
            }

            if (!restaurantFound) {
                submitStatus = ("Please select a restaurants");
            }
        } catch (NumberFormatException nfe) {
            submitStatus = ("Price must be a number");
        }

        if (correct) {
            PopupWidget.displaySuccess(submitStatus, "Success!");
        } else {
            PopupWidget.displayError(submitStatus, "Error!");
        }
    }
    
    @FXML
    private void refreshFoodPage() throws Exception {
        RoutingScene scene = (RoutingScene) mainBox.getScene();
        Route route = new XmlRoute(getClass().getResource("/AdminSceneFood.fxml"));
        scene.popRoute();
        scene.pushRoute(route);
    }
}
