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

    private Rectangle emptySpace;
    private ImageView addIcon;

    public FoodItemWidget(Food food) {
        this.food = food;
        setAlignment(Pos.CENTER);
        getStyleClass().add("food-item-box");

        foodName = new Text(food.getName());
        foodName.getStyleClass().add("food-name-text");

        priceText = new Text("€" + food.getPrice().toString());
        priceText.getStyleClass().add("food-price-text");

        contentContainer = new VBox();
        contentContainer.setAlignment(Pos.CENTER_LEFT);
        contentContainer.getChildren().addAll(foodName, priceText);
        getChildren().add(contentContainer);

        emptySpace = new Rectangle();
        emptySpace.setFill(Color.TRANSPARENT);
        emptySpace.setHeight(1);
        getChildren().add(emptySpace);

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
        contentContainer.setSpacing(newHeight * 0.05);
        priceText.setStyle("-fx-font-size: " + newHeight * 0.2);

        double textWidthEstimate = food.getName().length() * newHeight * 0.25 / 1.4;
        double spaceWidth = newWidth - 8 * 2 - iconSize - textWidthEstimate;
        emptySpace.setWidth(spaceWidth);
    }
}
