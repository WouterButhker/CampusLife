package nl.tudelft.oopp.demo.widgets;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import nl.tudelft.oopp.demo.communication.reservation.FoodOrderCommunication;
import nl.tudelft.oopp.demo.entities.FoodOrderQuantity;
import nl.tudelft.oopp.demo.entities.reservation.FoodOrder;

public class FoodOrderItem extends Group {
    private FoodOrder foodOrder;

    private VBox mainContainer;

    private VBox labelContainer;
    private HBox titleContainer;
    private ToggleButton arrowToggle;
    private Text title;
    private Text timeText;

    private VBox innerContainer;

    public FoodOrderItem(FoodOrder foodOrder) {
        this.foodOrder = foodOrder;

        mainContainer = new VBox();
        mainContainer.getStyleClass().add("food-order-item-bg");
        mainContainer.setPrefWidth(500);
        mainContainer.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                arrowToggle.setSelected(!arrowToggle.isSelected());
            }
        });

        labelContainer = new VBox();
        labelContainer.setPadding(new Insets(16, 0, 16, 0));
        labelContainer.setAlignment(Pos.CENTER_LEFT);
        mainContainer.getChildren().add(labelContainer);

        titleContainer = new HBox();
        titleContainer.setAlignment(Pos.CENTER_LEFT);
        labelContainer.getChildren().add(titleContainer);

        arrowToggle = new ToggleButton();
        arrowToggle.getStyleClass().add("expand-button");
        arrowToggle.setPrefWidth(40);
        arrowToggle.setPrefHeight(40);
        arrowToggle.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue && newValue != oldValue) {
                expand();
            } else if (!newValue && newValue != oldValue) {
                contract();
            }
        });
        titleContainer.getChildren().add(arrowToggle);

        title = new Text(String.format(
                "%s - Order #%d",
                foodOrder.getRestaurant().getName(),
                foodOrder.getId()
        ));
        title.getStyleClass().add("food-order-item-title");
        titleContainer.getChildren().add(title);

        SimpleDateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Calendar orderTime = Calendar.getInstance();
        try {
            orderTime.setTime(originalFormat.parse(foodOrder.getTimeSlot()));
        } catch (Exception e) {
            System.out.println("There are FoodOrders with invalid timeslots"
                    + ". Please delete all FoodOrders");
        }
        SimpleDateFormat format = new SimpleDateFormat("        EEE d. MMMM HH:mm");
        String timeString = format.format(orderTime.getTime());
        timeText = new Text(timeString);
        timeText.getStyleClass().add("food-order-item-time");
        labelContainer.getChildren().add(timeText);

        innerContainer = new VBox();
        innerContainer.setSpacing(8);
        innerContainer.setPadding(new Insets(0, 0, 0, 64));
        mainContainer.getChildren().add(innerContainer);

        getChildren().add(mainContainer);
    }

    private void expand() {
        boolean isTakeout = (foodOrder.getRoom() == null);
        Text orderType = new Text("Order type: " + (isTakeout ? "Takeout" : "Delivery"));
        orderType.getStyleClass().add("food-item-name");
        innerContainer.getChildren().add(orderType);
        Rectangle padding = new Rectangle();
        padding.setHeight(20);
        padding.setFill(Color.TRANSPARENT);
        innerContainer.getChildren().add(padding);

        List<FoodOrderQuantity> quantityList = FoodOrderCommunication.getAllQuantities(foodOrder);

        double totalPrice = 0;
        for (FoodOrderQuantity quantity : quantityList) {
            innerContainer.getChildren().add(createQuantityItem(quantity));
            totalPrice += quantity.getQuantity() * quantity.getFood().getPrice();
        }

        padding = new Rectangle();
        padding.setHeight(16);
        padding.setFill(Color.TRANSPARENT);
        innerContainer.getChildren().add(padding);

        HBox totalContainer = new HBox();
        totalContainer.setAlignment(Pos.CENTER_LEFT);
        Text totalText = new Text("Total");
        totalText.getStyleClass().add("food-item-quantity");
        totalText.setWrappingWidth(165);
        totalContainer.getChildren().add(totalText);
        Text costText = new Text(String.format("€ %.2f", totalPrice));
        costText.setWrappingWidth(80);
        costText.getStyleClass().add("food-item-price");
        totalContainer.getChildren().add(costText);
        innerContainer.getChildren().add(totalContainer);

        padding = new Rectangle();
        padding.setHeight(16);
        padding.setFill(Color.TRANSPARENT);
        innerContainer.getChildren().add(padding);
    }

    private Node createQuantityItem(FoodOrderQuantity quantity) {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER_LEFT);
        Text foodName = new Text(quantity.getFood().getName());
        foodName.setWrappingWidth(120);
        foodName.getStyleClass().add("food-item-name");
        container.getChildren().add(foodName);
        Text quantityText = new Text("x" + quantity.getQuantity());
        quantityText.setWrappingWidth(45);
        quantityText.getStyleClass().add("food-item-quantity");
        container.getChildren().add(quantityText);
        double price = quantity.getQuantity() * quantity.getFood().getPrice();
        Text priceText = new Text(String.format("€ %.2f", price));
        priceText.setWrappingWidth(80);
        priceText.getStyleClass().add("food-item-price");
        container.getChildren().add(priceText);

        return container;
    }

    private void contract() {
        innerContainer.getChildren().clear();
    }
}
