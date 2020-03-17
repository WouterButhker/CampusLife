package nl.tudelft.oopp.demo.views;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Stack;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import nl.tudelft.oopp.demo.communication.AuthenticationCommunication;
import nl.tudelft.oopp.demo.communication.BikeReservationCommunication;
import nl.tudelft.oopp.demo.core.Route;
import nl.tudelft.oopp.demo.entities.BikeReservation;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.widgets.*;



public class BikesReservationRoute extends Route {

    private VBox rootElement;

    private HBox horizontalContainer;

    private CalendarWidget calendarWidget;
    private BikeReservationWidget bikeReservationWidgetPickUp;
    private BikeReservationWidget bikeReservationWidgetDropOff;

    private Calendar selectedDate = Calendar.getInstance();
    private Calendar fromTime;
    private Calendar toTime;

    private Label text;

    /**
     * Creates a new BikesReservationRoute that creates the GUI to reserve a bike.
     */
    public BikesReservationRoute() {
        rootElement = new VBox();

        rootElement.getChildren().add(new AppBar());

        horizontalContainer = new HBox();
        horizontalContainer.setAlignment(Pos.CENTER);
        horizontalContainer.setPadding(new Insets(16, 16, 16, 16));
        horizontalContainer.setSpacing(32);
        rootElement.getChildren().add(horizontalContainer);

        calendarWidget = new CalendarWidget();
        calendarWidget.setListener(new CalendarWidget.Listener() {
            @Override
            public void onDayClicked(Calendar day) {
                bikeReservationWidgetDropOff.setSelectedDate(day);
                bikeReservationWidgetPickUp.setSelectedDate(day);
            }
        });
        calendarWidget.getChildren().add(createReserveButton());

        bikeReservationWidgetPickUp = new BikeReservationWidget("Pick up building");

        bikeReservationWidgetDropOff = new BikeReservationWidget("Drop off building");

        horizontalContainer.getChildren().addAll(calendarWidget, bikeReservationWidgetPickUp,
                bikeReservationWidgetDropOff);

        horizontalContainer.sceneProperty().addListener((obs2, oldScene, newScene) -> {
            if (newScene != null) {
                resizeDisplay(newScene.getWidth());
                //agendaWidget.setPrefHeight(newScene.getHeight() * 0.65);
                newScene.widthProperty().addListener((obs, oldWidth, newWidth) -> {
                    resizeDisplay(newWidth.doubleValue());
                });
                newScene.heightProperty().addListener((obs, oldHeight, newHeight) -> {
                    //agendaWidget.setPrefHeight(newHeight.doubleValue() * 0.65);
                });
            }
        });
    }

    private Node createReserveButton() {
        VBox res = new VBox();
        res.setAlignment(Pos.CENTER);
        res.setPadding(new Insets(20, 0, 0, 0));
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);
        StackPane stackPane = new StackPane();
        VBox hoverGlow = new VBox();
        Rectangle hoverGlowRectangle = new Rectangle();
        hoverGlow.getChildren().add(hoverGlowRectangle);
        hoverGlowRectangle.setFill(Color.TRANSPARENT);
        hoverGlow.getStyleClass().add("hover-glow");
        hoverGlow.setVisible(false);
        text = new Label("Reserve");
        container.getChildren().addAll(text);
        stackPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                reserveBike();
                System.out.println("Clicked!");
                System.out.println(stackPane.getPrefWidth());
                System.out.println(calendarWidget.getPrefWidth());
                System.out.println(text.getPrefWidth());
            }
        });
        stackPane.getStyleClass().add("available-date-box");
        stackPane.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hoverGlow.setVisible(true);
            }
        });
        stackPane.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hoverGlow.setVisible(false);
            }
        });
        container.setPadding(new Insets(5, 10, 5, 10));
        stackPane.getChildren().add(container);
        stackPane.getChildren().add(hoverGlow);
        res.getChildren().add(new Group(stackPane));
        return res;
    }

    private void resizeDisplay(double newWidth) {
        calendarWidget.setPrefWidth(newWidth * 0.3);
        bikeReservationWidgetPickUp.setPrefWidth(newWidth * 0.3);
        bikeReservationWidgetDropOff.setPrefWidth(newWidth * 0.3);
        text.setStyle("-fx-font-size:" + newWidth * 0.02);
    }

    private void reserveBike() {
        Building pickUpBuilding = bikeReservationWidgetPickUp.getSelected();
        Building dropOffBuilding = bikeReservationWidgetDropOff.getSelected();
        Calendar pickUpTime = bikeReservationWidgetPickUp.getTimeSelected();
        Calendar dropOffTime = bikeReservationWidgetDropOff.getTimeSelected();
        if (pickUpBuilding != null && dropOffBuilding != null && pickUpTime != null && dropOffTime != null) {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm");
            String pickUp = dateFormat.format(pickUpTime.getTime());
            String dropOff = dateFormat.format(dropOffTime.getTime());
            if (pickUp.compareTo(dropOff) < 0) {
                String slot = pickUp + "-" + dropOff;
                DateFormat dayFormat = new SimpleDateFormat("dd/MM/yyyy");
                String date = dayFormat.format(pickUpTime.getTime());
                BikeReservationCommunication.addReservationToTheDatabase(AuthenticationCommunication.myUserId,
                        pickUpBuilding.getCode(), dropOffBuilding.getCode(), date, slot);
            }
        }

    }

    @Override
    public Parent getRootElement() {
        return rootElement;
    }
}
