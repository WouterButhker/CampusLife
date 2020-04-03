package nl.tudelft.oopp.demo.views;

import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import nl.tudelft.oopp.demo.communication.AuthenticationCommunication;
import nl.tudelft.oopp.demo.communication.FavoriteRestaurantCommunication;
import nl.tudelft.oopp.demo.communication.ImageCommunication;
import nl.tudelft.oopp.demo.communication.RestaurantCommunication;
import nl.tudelft.oopp.demo.core.Route;
import nl.tudelft.oopp.demo.entities.FavoriteRestaurant;
import nl.tudelft.oopp.demo.entities.Food;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.entities.reservation.FoodOrder;
import nl.tudelft.oopp.demo.widgets.AppBar;
import nl.tudelft.oopp.demo.widgets.FoodItemWidget;
import nl.tudelft.oopp.demo.widgets.OrderWidget;

public class RestaurantMenuRoute extends Route {
    private FoodOrder foodOrder;
    private Restaurant restaurant;
    private List<Food> foods;

    private VBox rootContainer;
    private HBox mainContainer; // contains the menu on the left and cart on right
    private ScrollPane menuScroll;
    private VBox menuContainer; // contains the restaurant and menu items

    private HBox restaurantContainer;
    private VBox restaurantTextContainer;

    private StackPane imageContainer;
    private ImageView restaurantPicture;
    private ToggleButton favoriteButton;

    private Text restaurantTitle;
    private Text restaurantDescription;
    private List<FoodItemWidget> foodItems;
    private OrderWidget orderWidget;

    private FavoriteRestaurant favorite;

    /**
     * Creates a RestaurantMenuRoute.
     * This is a route which displays the menu of a restaurant so users
     * can buy food
     * @param restaurant the restaurant to be displayed
     */
    public RestaurantMenuRoute(Restaurant restaurant) {
        // TODO: Date and time
        // TODO: GET RIGHT USER
        foodOrder = new FoodOrder(new User(AuthenticationCommunication.myUserId),"Today",
                "ASAP", restaurant);
        foods = RestaurantCommunication.getAllFood(restaurant.getId());

        rootContainer = new VBox();
        rootContainer.getChildren().add(new AppBar());

        mainContainer = new HBox();
        rootContainer.getChildren().add(mainContainer);
        menuContainer = new VBox();
        menuScroll = new ScrollPane(menuContainer);
        menuScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        menuContainer.setPadding(new Insets(16));
        mainContainer.getChildren().add(menuScroll);
        orderWidget = new OrderWidget(foodOrder);
        mainContainer.getChildren().add(orderWidget);

        restaurantContainer = new HBox();
        menuContainer.getChildren().add(restaurantContainer);

        favorite = FavoriteRestaurantCommunication.isFavorite(restaurant);

        favoriteButton = new ToggleButton();
        favoriteButton.setSelected(favorite != null);
        favoriteButton.getStyleClass().add("favorite-button");
        favoriteButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (!oldValue) {
                favorite = FavoriteRestaurantCommunication.addFavorite(restaurant);
            } else {
                FavoriteRestaurantCommunication.removeFavorite(favorite.getId());
            }
        });

        Image restaurantImage =
                new Image(ImageCommunication.getRestaurantImageUrl(restaurant.getId()));
        restaurantPicture = new ImageView(restaurantImage);
        restaurantPicture.setPreserveRatio(true);

        imageContainer = new StackPane();
        imageContainer.setAlignment(Pos.BOTTOM_LEFT);
        imageContainer.getChildren().add(restaurantPicture);
        imageContainer.getChildren().add(favoriteButton);
        restaurantContainer.getChildren().add(imageContainer);

        restaurantTextContainer = new VBox();
        restaurantContainer.getChildren().add(restaurantTextContainer);
        restaurantTitle = new Text(restaurant.getName());
        restaurantTitle.getStyleClass().add("restaurant-name-text");
        restaurantTextContainer.getChildren().add(restaurantTitle);
        restaurantDescription = new Text(restaurant.getDescription());
        restaurantDescription.getStyleClass().add("restaurant-description-text");
        restaurantTextContainer.getChildren().add(restaurantDescription);

        // Add all items to the menu
        foodItems = new ArrayList<>();
        for (Food food : foods) {
            FoodItemWidget foodItemWidget = new FoodItemWidget(food);
            foodItemWidget.addEventHandler(
                    MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            foodOrder.addFood(food);
                            orderWidget.refresh();
                        }
                    }
            );
            foodItems.add(foodItemWidget);
            menuContainer.getChildren().add(foodItemWidget);
        }

        rootContainer.sceneProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                resizeRoute(newValue.getWidth(), newValue.getHeight());
                newValue.heightProperty().addListener((observable1, oldValue1, newValue1) -> {
                    resizeRoute(newValue.getWidth(), newValue.getHeight());
                });
                newValue.widthProperty().addListener((observable1, oldValue1, newValue1) -> {
                    resizeRoute(newValue.getWidth(), newValue.getHeight());
                });
            }
        });
    }

    private void resizeRoute(double newWidth, double newHeight) {
        double sides = newWidth * 0.1;
        menuContainer.setPadding(new Insets(16, sides, 16, sides));
        menuScroll.setPrefWidth(newWidth * 0.75);
        menuScroll.setMinWidth(newWidth * 0.75);
        menuScroll.setMaxWidth(newWidth * 0.75);
        menuScroll.setFitToWidth(true);

        menuContainer.setSpacing(newHeight * 0.02);
        restaurantContainer.setSpacing(newHeight * 0.02);
        restaurantTextContainer.setPadding(new Insets(newHeight * 0.02, 0, 0, 0));
        restaurantTextContainer.setSpacing(newHeight * 0.01);
        restaurantPicture.setFitHeight(newHeight * 0.25);
        restaurantTitle.setStyle("-fx-font-size: " + newHeight * 0.06);
        restaurantDescription.setWrappingWidth(newWidth * 0.4);
        restaurantDescription.setStyle("-fx-font-size: " + newHeight * 0.025);

        double favoriteSize = newHeight * 0.25 * 0.2;
        favoriteButton.setPrefWidth(favoriteSize);
        favoriteButton.setPrefHeight(favoriteSize);
        favoriteButton.setStyle(String.format(
                "-fx-background-size: %fpx %fpx",
                favoriteSize,
                favoriteSize
        ));

        for (FoodItemWidget foodItemWidget : foodItems) {
            foodItemWidget.setPrefWidth(newWidth * 0.55);
            foodItemWidget.setPrefHeight(newHeight * 0.1);
        }

        orderWidget.setPrefWidth(newWidth * 0.25);
        orderWidget.setPrefHeight(newHeight * 0.91);
    }

    @Override
    public Parent getRootElement() {
        return rootContainer;
    }
}
