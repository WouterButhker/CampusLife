package nl.tudelft.oopp.demo.views;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import nl.tudelft.oopp.demo.communication.AuthenticationCommunication;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.communication.ImageCommunication;
import nl.tudelft.oopp.demo.communication.RestaurantCommunication;
import nl.tudelft.oopp.demo.communication.reservation.RoomReservationCommunication;
import nl.tudelft.oopp.demo.core.PopupRoute;
import nl.tudelft.oopp.demo.core.Route;
import nl.tudelft.oopp.demo.entities.*;
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
    private ImageView restaurantPicture;
    private Text restaurantTitle;
    private Text restaurantDescription;
    private List<FoodItemWidget> foodItems;
    private OrderWidget orderWidget;

    private List<RoomReservation> roomReservations;

    /**
     * Creates a RestaurantMenuRoute.
     * This is a route which displays the menu of a restaurant so users
     * can buy food
     * @param restaurant the restaurant to be displayed
     */
    public RestaurantMenuRoute(Restaurant restaurant) {
        super(new VBox());
        Calendar now = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        foodOrder = new FoodOrder(
                new User(AuthenticationCommunication.myUserId),
                dateFormat.format(now.getTime()),
                null,
                restaurant,
                null
        );
        foods = RestaurantCommunication.getAllFood(restaurant.getId());

        // Get reservations of rooms only and remove past events
        roomReservations = RoomReservationCommunication.getMyReservations();
        for (int i = 0; i < roomReservations.size(); i++) {
            RoomReservation reservation = roomReservations.get(i);
            if (reservation.getRoom() == null) {
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

        rootContainer = (VBox) getMainElement();
        rootContainer.getChildren().add(new AppBar());

        mainContainer = new HBox();
        rootContainer.getChildren().add(mainContainer);
        menuContainer = new VBox();
        menuScroll = new ScrollPane(menuContainer);
        menuScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        menuContainer.setPadding(new Insets(16));
        mainContainer.getChildren().add(menuScroll);

        Building building = BuildingCommunication.getBuildingByCode(restaurant.getBuildingCode());
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

        Image restaurantImage =
                new Image(ImageCommunication.getRestaurantImageUrl(restaurant.getId()));
        restaurantPicture = new ImageView(restaurantImage);
        restaurantPicture.setPreserveRatio(true);
        restaurantContainer.getChildren().add(restaurantPicture);

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

        for (FoodItemWidget foodItemWidget : foodItems) {
            foodItemWidget.setPrefWidth(newWidth * 0.55);
            foodItemWidget.setPrefHeight(newHeight * 0.1);
        }

        orderWidget.setPrefWidth(newWidth * 0.25);
        orderWidget.setPrefHeight(newHeight * 0.91);
    }
}
