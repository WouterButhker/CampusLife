package nl.tudelft.oopp.demo.widgets;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import nl.tudelft.oopp.demo.communication.AuthenticationCommunication;
import nl.tudelft.oopp.demo.communication.FoodOrderCommunication;
import nl.tudelft.oopp.demo.communication.RestaurantCommunication;
import nl.tudelft.oopp.demo.core.RoutingScene;
import nl.tudelft.oopp.demo.entities.Food;
import nl.tudelft.oopp.demo.entities.FoodOrder;

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
    private Button deliveryButton;

    private List<OrderItem> orderItems;
    private List<Food> foods;

    public OrderWidget(FoodOrder foodOrder) {
        this.foodOrder = foodOrder;
        foods = RestaurantCommunication.getAllFood(foodOrder.getRestaurant());

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
        takeoutButton.getStyleClass().add("order-button");
        takeoutButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FoodOrderCommunication.createFoodOrder(foodOrder);

                RoutingScene routingScene = (RoutingScene) takeoutButton.getScene();
                try {
                    routingScene.popRoute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        bottomContainer.getChildren().add(takeoutButton);
        deliveryButton = new Button("Order delivery");
        deliveryButton.getStyleClass().add("order-button");
        //bottomContainer.getChildren().add(deliveryButton);

        prefWidthProperty().addListener((observable, oldValue, newValue) -> {
            resizeWidget(newValue.doubleValue(), getPrefHeight());
        });
        prefHeightProperty().addListener((observable, oldValue, newValue) -> {
            resizeWidget(getPrefWidth(), newValue.doubleValue());
        });

        refresh();
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
        deliveryButton.setStyle("-fx-font-size: " + newHeight * 0.015);
        deliveryButton.setPrefWidth(newWidth * 0.9);
        deliveryButton.setPrefHeight(newHeight * 0.08);

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
            addButton.setStyle(String.format("-fx-background-size: %fpx %fpx", buttonSize, buttonSize));

            removeButton.setPrefWidth(buttonSize);
            removeButton.setPrefHeight(buttonSize);
            removeButton.setStyle(String.format("-fx-background-size: %fpx %fpx", buttonSize, buttonSize));
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
