package nl.tudelft.oopp.demo.views;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.communication.FavoriteRestaurantCommunication;
import nl.tudelft.oopp.demo.communication.ImageCommunication;
import nl.tudelft.oopp.demo.communication.RestaurantCommunication;
import nl.tudelft.oopp.demo.communication.reservation.RoomReservationCommunication;
import nl.tudelft.oopp.demo.core.PopupRoute;
import nl.tudelft.oopp.demo.entities.*;
import nl.tudelft.oopp.demo.entities.FavoriteRestaurant;
import nl.tudelft.oopp.demo.entities.Food;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.entities.reservation.FoodOrder;
import nl.tudelft.oopp.demo.entities.reservation.RoomReservation;
import nl.tudelft.oopp.demo.widgets.*;

public class RestaurantMenuRoute extends PopupRoute {
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
    private List<RoomReservation> roomReservations;
    private Building building;
    private Image restaurantImage;

    /**
     * Creates a RestaurantMenuRoute.
     * This is a route which displays the menu of a restaurant so users
     * can buy food
     * @param restaurant the restaurant to be displayed
     */
    public RestaurantMenuRoute(Restaurant restaurant) {
        super(new VBox());
        this.restaurant = restaurant;

        rootContainer = (VBox) getMainElement();
        rootContainer.getChildren().add(new AppBar());

        showPopup(new LoadingPopup(), false);
        new Thread(() -> {
            loadData();
            Platform.runLater(() -> {
                buildDisplay();
                removePopup();
            });
        }).start();
    }

    private void loadData() {
        foods = RestaurantCommunication.getAllFood(restaurant.getId());
        roomReservations = RoomReservationCommunication.getMyReservations();
        building = BuildingCommunication.getBuildingByCode(restaurant.getBuildingCode());
        favorite = FavoriteRestaurantCommunication.isFavorite(restaurant);
        restaurantImage = new Image(ImageCommunication
                .getRestaurantImageUrl(restaurant.getId()).get(0));
    }

    private void buildDisplay() {
        Calendar now = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        foodOrder = new FoodOrder(
                new User(AuthenticationCommunication.myUserId),
                dateFormat.format(now.getTime()),
                null,
                restaurant,
                null
        );

        // Get reservations of rooms only and remove past events
        for (int i = 0; i < roomReservations.size(); i++) {
            RoomReservation reservation = roomReservations.get(i);
            if (reservation.getRoom() == null) {
                roomReservations.remove(i);
                i--;
            } else if (!reservation.getRoom().getBuilding().getCode()
                    .equals(restaurant.getBuildingCode())) {
                roomReservations.remove(i);
                i--;
            } else {
                SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy,HH:mm");
                Calendar reservationTime = Calendar.getInstance();
                String time = reservation.getTimeSlot().substring(0, 17);
                try {
                    reservationTime.setTime(dateTimeFormat.parse(time));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (reservationTime.getTimeInMillis() + 60 * 60 * 1000 < now.getTimeInMillis()) {
                    roomReservations.remove(i);
                    i--;
                }
            }
        }

        mainContainer = new HBox();
        rootContainer.getChildren().add(mainContainer);
        menuContainer = new VBox();
        menuScroll = new ScrollPane(menuContainer);
        menuScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        menuContainer.setPadding(new Insets(16));
        mainContainer.getChildren().add(menuScroll);

        Weekdays weekdays = new Weekdays(building.getOpeningHours());
        int dayOfWeek = new CalendarWidgetLogic().getDayOfWeek(Calendar.getInstance());
        boolean isBuildingOpen = weekdays.isOpenAt(
                dayOfWeek,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE)
        );
        orderWidget = new OrderWidget(foodOrder, isBuildingOpen, roomReservations, this);
        mainContainer.getChildren().add(orderWidget);

        restaurantContainer = new HBox();
        menuContainer.getChildren().add(restaurantContainer);

        favoriteButton = new ToggleButton();
        favoriteButton.setSelected(favorite != null);
        favoriteButton.getStyleClass().add("favorite-button");
        favoriteButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            showPopup(new LoadingPopup(), false);
            new Thread(() -> {
                if (!oldValue) {
                    favorite = FavoriteRestaurantCommunication.addFavorite(restaurant);
                } else {
                    FavoriteRestaurantCommunication.removeFavorite(favorite.getId());
                }

                Platform.runLater(this::removePopup);
            }).start();
        });

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

        resizeRoute(getRoutingScene().getWidth(), getRoutingScene().getHeight());
        getRoutingScene().heightProperty().addListener((observable1, oldValue1, newValue1) -> {
            resizeRoute(getRoutingScene().getWidth(), getRoutingScene().getHeight());
        });
        getRoutingScene().widthProperty().addListener((observable1, oldValue1, newValue1) -> {
            resizeRoute(getRoutingScene().getWidth(), getRoutingScene().getHeight());
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
}
