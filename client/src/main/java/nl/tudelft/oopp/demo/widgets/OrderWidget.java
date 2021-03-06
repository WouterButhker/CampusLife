package nl.tudelft.oopp.demo.widgets;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import nl.tudelft.oopp.demo.communication.RestaurantCommunication;
import nl.tudelft.oopp.demo.communication.reservation.FoodOrderCommunication;
import nl.tudelft.oopp.demo.core.PopupRoute;
import nl.tudelft.oopp.demo.core.RoutingScene;
import nl.tudelft.oopp.demo.entities.Food;
import nl.tudelft.oopp.demo.entities.reservation.FoodOrder;
import nl.tudelft.oopp.demo.entities.reservation.RoomReservation;

public class OrderWidget extends StackPane {
    private FoodOrder foodOrder;

    private Rectangle background;
    private VBox mainContainer;
    private VBox orderTextContainer;
    private Text orderText;
    private VBox ordersContainer;
    private Rectangle orderTextSeparator;
    private Rectangle orderItemsSeparator;

    private VBox bottomContainer;
    private HBox totalContainer;
    private Text totalText;
    private Text priceText;
    private Button takeoutButton;
    private Text takeoutText;
    private Button deliveryButton;
    private Text deliveryText;

    private List<OrderItem> orderItems;
    private List<Food> foods;

    private boolean isTakeoutEnabled;

    /**
     * Creates an OrderWidget.
     * The OrderWidget works like a 'cart' for ordering food.
     * It displays all food currently on the order
     * @param foodOrder the foodOrder to display
     */
    public OrderWidget(FoodOrder foodOrder,
                       boolean isTakeoutEnabled,
                       List<RoomReservation> reservations,
                       PopupRoute popupRoute) {
        this.isTakeoutEnabled = isTakeoutEnabled;
        this.foodOrder = foodOrder;
        foods = RestaurantCommunication.getAllFood(foodOrder.getRestaurant().getId());

        background = new Rectangle();
        background.setFill(Color.WHITE);
        background.getStyleClass().add("order-box");
        getChildren().add(background);
        DropShadow containerShadow = new DropShadow();
        containerShadow.setWidth(8);
        containerShadow.setHeight(0);
        containerShadow.setOffsetX(0);
        containerShadow.setOffsetY(4);
        containerShadow.setRadius(8);
        background.setEffect(containerShadow);

        mainContainer = new VBox();
        mainContainer.setAlignment(Pos.TOP_CENTER);
        getChildren().add(mainContainer);

        orderTextContainer = new VBox();
        orderTextContainer.setAlignment(Pos.CENTER);
        mainContainer.getChildren().add(orderTextContainer);
        orderText = new Text("Order");
        orderText.getStyleClass().add("order-text");
        orderTextContainer.getChildren().add(orderText);

        orderTextSeparator = createSeparator();
        mainContainer.getChildren().add(orderTextSeparator);

        ordersContainer = new VBox();
        ordersContainer.setPadding(new Insets(16, 0, 16, 0));
        mainContainer.getChildren().add(ordersContainer);
        orderItems = new ArrayList<>();

        orderItemsSeparator = createSeparator();
        mainContainer.getChildren().add(orderItemsSeparator);

        // Add total text and buttons to order
        bottomContainer = new VBox();
        bottomContainer.setAlignment(Pos.CENTER);
        bottomContainer.setSpacing(16);
        mainContainer.getChildren().add(bottomContainer);
        bottomContainer.setPadding(new Insets(24, 8, 16, 8));

        totalContainer = new HBox();
        totalContainer.setPadding(new Insets(0, 0, 16, 0));
        totalContainer.setAlignment(Pos.CENTER);
        totalText = new Text(" Total");
        totalText.setTextAlignment(TextAlignment.LEFT);
        totalText.getStyleClass().add("order-quantity-text");
        totalContainer.getChildren().add(totalText);
        priceText = new Text();
        priceText.setTextAlignment(TextAlignment.RIGHT);
        priceText.getStyleClass().add("order-name-text");
        totalContainer.getChildren().add(priceText);
        bottomContainer.getChildren().add(totalContainer);

        takeoutButton = new Button("Order takeout");
        if (isTakeoutEnabled) {
            takeoutButton.getStyleClass().add("order-button");
        } else {
            takeoutButton.getStyleClass().add("order-button-disabled");
        }
        takeoutButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (isTakeoutEnabled) {
                    popupRoute.showPopup(new ConfirmationPopup(
                            "Confirm Order",
                            "Are you done selecting food?",
                            new ConfirmationPopup.Listener() {
                                @Override
                                public void onConfirmClicked() {
                                    confirmTakeout(popupRoute);
                                }

                                @Override
                                public void onCancelClicked() {
                                    popupRoute.removePopup();
                                }
                            }), true);
                }
            }
        });
        bottomContainer.getChildren().add(takeoutButton);
        if (!isTakeoutEnabled) {
            takeoutText = new Text("The restaurant is currently closed");
            takeoutText.setTextAlignment(TextAlignment.CENTER);
            takeoutText.getStyleClass().add("info-text");
            bottomContainer.getChildren().add(takeoutText);
        }
        deliveryButton = new Button("Order delivery");
        if (!reservations.isEmpty()) {
            deliveryButton.getStyleClass().add("order-button");
        } else {
            deliveryButton.getStyleClass().add("order-button-disabled");
        }
        deliveryButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!reservations.isEmpty()) {
                    selectDeliveryTime(popupRoute, reservations);
                }
            }
        });
        bottomContainer.getChildren().add(deliveryButton);
        if (reservations.isEmpty()) {
            deliveryText = new Text("There are no reservations to deliver to");
            deliveryText.setTextAlignment(TextAlignment.CENTER);
            deliveryText.getStyleClass().add("info-text");
            bottomContainer.getChildren().add(deliveryText);
        }

        prefWidthProperty().addListener((observable, oldValue, newValue) -> {
            resizeWidget(newValue.doubleValue(), getPrefHeight());
        });
        prefHeightProperty().addListener((observable, oldValue, newValue) -> {
            resizeWidget(getPrefWidth(), newValue.doubleValue());
        });

        refresh();
    }

    private void confirmTakeout(PopupRoute popupRoute) {
        popupRoute.showPopup(new LoadingPopup(), false);
        new Thread(() -> {
            Calendar now = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            foodOrder.setTimeSlot(dateFormat.format(now.getTime()));

            FoodOrderCommunication.createFoodOrder(foodOrder);

            Platform.runLater(() -> {
                popupRoute.showPopup(new InformationPopup(
                        "Success!",
                        "Your order has successfully been placed.",
                        new InformationPopup.Listener() {
                            @Override
                            public void onOkClicked() {
                                popupRoute.removePopup();
                                RoutingScene routingScene =
                                        (RoutingScene) takeoutButton.getScene();
                                try {
                                    routingScene.popRoute();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                ), false);
            });
        }).start();
    }

    private void selectDeliveryTime(PopupRoute popupRoute, List<RoomReservation> reservations) {
        Calendar now = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        foodOrder.setTimeSlot(dateFormat.format(now.getTime()));

        List<ListItem> items = new ArrayList<>();
        for (RoomReservation reservation : reservations) {
            items.add(new ListItem(
                    reservation.getRoom().getName(),
                    reservation.getTimeSlot()
            ));
        }
        popupRoute.showPopup(new ListPopup(
                "Select delivery time",
                items,
                new ListPopup.Listener() {
                    @Override
                    public void onItemClicked(int index) {
                        popupRoute.showPopup(new ConfirmationPopup(
                                "Confirm Order",
                                "Are you done selecting food?",
                                new ConfirmationPopup.Listener() {
                                    @Override
                                    public void onConfirmClicked() {
                                        confirmDelivery(popupRoute, reservations.get(index));
                                    }

                                    @Override
                                    public void onCancelClicked() {
                                        selectDeliveryTime(popupRoute, reservations);
                                    }
                                }), true);
                    }
                }
        ), true);
    }

    private void confirmDelivery(PopupRoute popupRoute, RoomReservation reservation) {
        popupRoute.showPopup(new LoadingPopup(), false);
        new Thread(() -> {
            Calendar now = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            foodOrder.setTimeSlot(dateFormat.format(now.getTime()));
            foodOrder.setRoom(reservation);
            FoodOrderCommunication.createFoodOrder(foodOrder);

            Platform.runLater(() -> {
                popupRoute.showPopup(new InformationPopup(
                        "Success!",
                        "Your order has successfully been placed.",
                        new InformationPopup.Listener() {
                            @Override
                            public void onOkClicked() {
                                popupRoute.removePopup();
                                RoutingScene routingScene =
                                        (RoutingScene) takeoutButton.getScene();
                                try {
                                    routingScene.popRoute();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                ), false);
            });
        }).start();
    }

    private Rectangle createSeparator() {
        Rectangle separator = new Rectangle();
        separator.setStyle("-fx-fill: #E0E0E0");
        separator.setHeight(1);
        return separator;
    }

    private void updateOrderItems() {
        orderItemsSeparator.setVisible(!foodOrder.getFoodsList().isEmpty());
        bottomContainer.setVisible(!foodOrder.getFoodsList().isEmpty());
        orderItems.clear();
        ordersContainer.getChildren().clear();


        foodOrder.getFoodsList().forEach((pair) -> {
            Food food = null;
            for (Food food1 : foods) {
                if (food1.getId().equals(pair.get(0))) {
                    food = food1;
                    break;
                }
            }
            int quantity = pair.get(1);
            OrderItem orderItem = new OrderItem(food, quantity, new OrderItem.Listener() {
                @Override
                public void onAddClicked(Food food) {
                    foodOrder.addFood(food);
                    refresh();
                }

                @Override
                public void onRemoveClicked(Food food) {
                    foodOrder.removeFood(food);
                    refresh();
                }
            });
            orderItem.setPrefWidth(getPrefWidth() * 0.8);
            orderItem.setPrefHeight(getPrefHeight() * 0.05);
            ordersContainer.getChildren().add(orderItem);
            orderItems.add(orderItem);
        });
    }

    /**
     * Notifies the OrderWidget that the FoodOrder has changed.
     */
    public void refresh() {
        updateOrderItems();

        priceText.setText(String.format("€ %.2f ", calculatePrice()));
    }

    private double calculatePrice() {
        AtomicReference<Double> price = new AtomicReference<>((double) 0);
        foodOrder.getFoodsList().forEach((pair) -> {
            Food food = null;
            for (Food food1 : foods) {
                if (food1.getId().equals(pair.get(0))) {
                    food = food1;
                    break;
                }
            }
            int quantity = pair.get(1);
            Food finalFood = food;
            price.updateAndGet(v -> (v + finalFood.getPrice() * quantity));
        });
        return price.get();
    }

    private void resizeWidget(double newWidth, double newHeight) {
        newWidth -= 16;
        background.setWidth(newWidth + 16);
        background.setHeight(newHeight);

        totalText.setWrappingWidth(newWidth * 0.45);
        totalText.setStyle("-fx-font-size: " + newHeight * 0.015);
        priceText.setWrappingWidth(newWidth * 0.45);
        priceText.setStyle("-fx-font-size: " + newHeight * 0.015);

        takeoutButton.setStyle("-fx-font-size: " + newHeight * 0.015);
        takeoutButton.setPrefWidth(newWidth * 0.9);
        takeoutButton.setPrefHeight(newHeight * 0.08);

        if (takeoutText != null) {
            takeoutText.setStyle("-fx-font-size: " + newHeight * 0.015);
            takeoutText.setWrappingWidth(newWidth * 0.45);
        }

        deliveryButton.setStyle("-fx-font-size: " + newHeight * 0.015);
        deliveryButton.setPrefWidth(newWidth * 0.9);
        deliveryButton.setPrefHeight(newHeight * 0.08);

        if (deliveryText != null) {
            deliveryText.setStyle("-fx-font-size: " + newHeight * 0.015);
            deliveryText.setWrappingWidth(newWidth * 0.45);
        }

        for (OrderItem orderItem : orderItems) {
            orderItem.setPrefWidth(newWidth * 0.8);
            orderItem.setPrefHeight(newHeight * 0.05);
        }

        orderTextSeparator.setWidth(newWidth * 0.85);
        orderItemsSeparator.setWidth(newWidth * 0.85);
        orderText.setStyle("-fx-font-size: " + newHeight * 0.03);
        orderTextContainer.setPadding(new Insets(newHeight * 0.04));
    }

    private static class OrderItem extends HBox {
        private Food food;
        private int quantity;

        private Text quantityText;
        private Text nameText;
        private Text priceText;

        private Button addButton;
        private Button removeButton;

        private Listener listener;

        public OrderItem(Food food, int quantity, Listener listener) {
            this.listener = listener;
            setAlignment(Pos.CENTER);

            quantityText = new Text();
            quantityText.setTextAlignment(TextAlignment.CENTER);
            quantityText.getStyleClass().add("order-quantity-text");
            getChildren().add(quantityText);
            nameText = new Text(food.getName());
            nameText.setTextAlignment(TextAlignment.LEFT);
            quantityText.getStyleClass().add("order-name-text");
            getChildren().add(nameText);

            addButton = new Button();
            addButton.getStyleClass().add("add-button");
            addButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    listener.onAddClicked(food);
                }
            });
            getChildren().add(addButton);
            removeButton = new Button();
            removeButton.getStyleClass().add("remove-button");
            removeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    listener.onRemoveClicked(food);
                }
            });
            getChildren().add(removeButton);

            priceText = new Text();
            priceText.setTextAlignment(TextAlignment.CENTER);
            quantityText.getStyleClass().add("order-name-text");
            getChildren().add(priceText);

            this.food = food;
            setQuantity(quantity);

            prefWidthProperty().addListener((observable, oldValue, newValue) -> {
                resizeWidget(newValue.doubleValue(), getPrefHeight());
            });
            prefHeightProperty().addListener((observable, oldValue, newValue) -> {
                resizeWidget(getPrefWidth(), newValue.doubleValue());
            });
        }

        private void resizeWidget(double newWidth, double newHeight) {
            double fontSize = newHeight * 0.3;
            quantityText.setStyle("-fx-font-size: " + fontSize);
            nameText.setStyle("-fx-font-size: " + fontSize);
            priceText.setStyle("-fx-font-size: " + fontSize);

            quantityText.setWrappingWidth(newWidth * 0.2);
            nameText.setWrappingWidth(newWidth * 0.5);
            priceText.setWrappingWidth(newWidth * 0.3);

            double buttonSize = newHeight * 0.7;
            addButton.setPrefWidth(buttonSize);
            addButton.setPrefHeight(buttonSize);
            addButton.setStyle(
                    String.format("-fx-background-size: %fpx %fpx", buttonSize, buttonSize));

            removeButton.setPrefWidth(buttonSize);
            removeButton.setPrefHeight(buttonSize);
            removeButton.setStyle(
                    String.format("-fx-background-size: %fpx %fpx", buttonSize, buttonSize));
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;

            quantityText.setText(String.format("%dx   ", quantity));
            priceText.setText(String.format("€ %.2f", food.getPrice() * quantity));
        }

        public interface Listener {
            void onAddClicked(Food food);

            void onRemoveClicked(Food food);
        }
    }
}
