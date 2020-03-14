package nl.tudelft.oopp.demo.widgets;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import nl.tudelft.oopp.demo.entities.Food;

public class FoodItemWidget extends HBox {
    private Food food;

    private VBox contentContainer;
    private Text foodName;
    private Text priceText;

    private ImageView addIcon;

    public FoodItemWidget(Food food) {
        this.food = food;
        setAlignment(Pos.CENTER);
        getStyleClass().add("food-item-box");

        foodName = new Text(food.getName());
        foodName.getStyleClass().add("food-name-text");

        priceText = new Text(String.format("€ %.2f", food.getPrice()));
        priceText.getStyleClass().add("food-price-text");

        contentContainer = new VBox();
        contentContainer.setAlignment(Pos.CENTER_LEFT);
        contentContainer.getChildren().addAll(foodName, priceText);
        getChildren().add(contentContainer);

        Image addBoxImage = new Image("/images/add_box.png");
        addIcon = new ImageView(addBoxImage);
        getChildren().add(addIcon);

        prefWidthProperty().addListener((observable, oldValue, newValue) -> {
            resizeWidget(newValue.doubleValue(), getPrefHeight());
        });
        prefHeightProperty().addListener((observable, oldValue, newValue) -> {
            resizeWidget(getPrefWidth(), newValue.doubleValue());
        });
    }

    private void resizeWidget(double newWidth, double newHeight) {
        double iconSize = newHeight * 0.4;
        addIcon.setFitWidth(iconSize);
        addIcon.setFitHeight(iconSize);

        foodName.setStyle("-fx-font-size: " + newHeight * 0.25);
        foodName.setWrappingWidth(newWidth * 0.85);
        contentContainer.setSpacing(newHeight * 0.05);
        priceText.setStyle("-fx-font-size: " + newHeight * 0.2);
    }
}
