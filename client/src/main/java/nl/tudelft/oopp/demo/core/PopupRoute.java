package nl.tudelft.oopp.demo.core;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import nl.tudelft.oopp.demo.communication.reservation.FoodOrderCommunication;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PopupRoute extends Route {

    private StackPane rootElement;
    private Rectangle fadeBox;

    private Node mainElement;

    private Node popup;
    private Group popupGroup;

    private ChangeListener<Number> heightListener;
    private ChangeListener<Number> widthListener;

    public PopupRoute(Node mainElement) {
        this.mainElement = mainElement;
        rootElement = new StackPane();
        rootElement.setAlignment(Pos.CENTER);
        rootElement.getChildren().add(mainElement);

        fadeBox = new Rectangle();
        fadeBox.setFill(new Color(0, 0, 0, 0.2));
        fadeBox.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                removePopup();
            }
        });
        heightListener = (observable, oldValue, newValue) -> {
            fadeBox.setHeight(newValue.doubleValue());
        };
        widthListener = (observable, oldValue, newValue) -> {
            fadeBox.setWidth(newValue.doubleValue());
        };
    }

    public Node getMainElement() {
        return mainElement;
    }

    public void showPopup(Node popup) {
        removePopup();

        this.popup = popup;
        rootElement.getChildren().addAll(fadeBox, popup);
    }

    public void removePopup() {
        if (popup != null) {
            rootElement.getChildren().remove(1, 3);
        }
        popup = null;
    }

    @Override
    protected void setRoutingScene(RoutingScene routingScene) {
        if (getRoutingScene() != null) {
            routingScene.widthProperty().removeListener(widthListener);
            routingScene.heightProperty().removeListener(heightListener);
        }
        super.setRoutingScene(routingScene);

        fadeBox.setWidth(routingScene.getWidth());
        fadeBox.setHeight(routingScene.getHeight());
        routingScene.widthProperty().addListener(widthListener);
        routingScene.heightProperty().addListener(heightListener);
    }

    @Override
    public final Parent getRootElement() {
        return rootElement;
    }
}
