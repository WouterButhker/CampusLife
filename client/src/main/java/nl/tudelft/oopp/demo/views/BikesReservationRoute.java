package nl.tudelft.oopp.demo.views;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import nl.tudelft.oopp.demo.communication.AuthenticationCommunication;
import nl.tudelft.oopp.demo.communication.reservation.BikeReservationCommunication;
import nl.tudelft.oopp.demo.core.Route;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.entities.reservation.BikeReservation;
import nl.tudelft.oopp.demo.widgets.AppBar;
import nl.tudelft.oopp.demo.widgets.BikeReservationWidget;
import nl.tudelft.oopp.demo.widgets.CalendarWidget;
import nl.tudelft.oopp.demo.widgets.PopupWidget;

public class BikesReservationRoute extends Route {

    private VBox rootElement;

    private HBox horizontalContainer;

    private CalendarWidget calendarWidget;
    private BikeReservationWidget bikeReservationWidgetPickUp;
    private BikeReservationWidget bikeReservationWidgetDropOff;

    private StackPane reserveButton;

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
                bikeReservationWidgetPickUp.setSelectedDate(day);
                bikeReservationWidgetPickUp.setAvailabilities();
                bikeReservationWidgetPickUp.removeSelection();
                bikeReservationWidgetPickUp.setTimeSelected(null);
                bikeReservationWidgetPickUp.calculateNumBikes();
                bikeReservationWidgetDropOff.setSelectedDate(day);
                bikeReservationWidgetDropOff.setAvailabilities();
                bikeReservationWidgetDropOff.removeSelection();
                bikeReservationWidgetDropOff.setTimeSelected(null);
                bikeReservationWidgetDropOff.calculateNumBikes();
                toggleButton();
            }
        });
        calendarWidget.getChildren().add(createReserveButton());

        bikeReservationWidgetPickUp = new BikeReservationWidget("Pick up building");
        bikeReservationWidgetDropOff = new BikeReservationWidget("Drop off building");
        bikeReservationWidgetPickUp.setListener(new BikeReservationWidget.Listener() {
            @Override
            public void changed() {
                toggleButton();
            }
        });
        bikeReservationWidgetDropOff.setListener(new BikeReservationWidget.Listener() {
            @Override
            public void changed() {
                toggleButton();
            }
        });

        horizontalContainer.getChildren().addAll(calendarWidget, bikeReservationWidgetPickUp,
                bikeReservationWidgetDropOff);

        horizontalContainer.sceneProperty().addListener((obs2, oldScene, newScene) -> {
            if (newScene != null) {
                resizeDisplay(newScene.getWidth());
                newScene.widthProperty().addListener((obs, oldWidth, newWidth) -> {
                    resizeDisplay(newWidth.doubleValue());
                });
            }
        });
        toggleButton();
    }

    private void toggleButton() {
        if (allConditionsSelected()) {
            reserveButton.setDisable(false);
        } else {
            reserveButton.setDisable(true);
        }
    }

    private Node createReserveButton() {
        VBox res = new VBox();
        res.setAlignment(Pos.CENTER);
        res.setPadding(new Insets(20, 0, 0, 0));
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);
        VBox hoverGlow = new VBox();
        Rectangle hoverGlowRectangle = new Rectangle();
        hoverGlow.getChildren().add(hoverGlowRectangle);
        hoverGlowRectangle.setFill(Color.TRANSPARENT);
        hoverGlow.getStyleClass().add("hover-glow");
        hoverGlow.setVisible(false);
        text = new Label("Reserve");
        container.getChildren().addAll(text);
        reserveButton = new StackPane();
        reserveButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                reserveBike();
                bikeReservationWidgetPickUp.calculateNumBikes();
                bikeReservationWidgetDropOff.calculateNumBikes();
            }
        });
        reserveButton.getStyleClass().add("available-date-box");
        reserveButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hoverGlow.setVisible(true);
            }
        });
        reserveButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hoverGlow.setVisible(false);
            }
        });
        container.setPadding(new Insets(5, 10, 5, 10));
        reserveButton.getChildren().add(container);
        reserveButton.getChildren().add(hoverGlow);
        res.getChildren().add(new Group(reserveButton));
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
        List<HBox> boxes = bikeReservationWidgetPickUp.getBoxes();
        HBox box = boxes.get(bikeReservationWidgetPickUp.getSelectedInList());
        Label label = (Label) box.getChildren().get(1);
        Integer bikes = Integer.parseInt(label.getText().split("bikes : ")[1]);
        if (allConditionsSelected() && bikes > 0) {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm");
            String pickUp = dateFormat.format(pickUpTime.getTime());
            String dropOff = dateFormat.format(dropOffTime.getTime());
            String slot = pickUp + "-" + dropOff;
            DateFormat dayFormat = new SimpleDateFormat("dd/MM/yyyy");
            String date = dayFormat.format(pickUpTime.getTime());
            BikeReservation res = new BikeReservation(
                    new User(AuthenticationCommunication.myUserId),
                      pickUpBuilding, dropOffBuilding, date, slot);
            BikeReservationCommunication.createBikeReservation(res);
            PopupWidget.displaySuccess("Bike Reserved!", "Bike reserved");
        } else {
            PopupWidget.displayError("There are no bikes available at the pickup building",
                    "An error has occurred");
        }
    }

    private boolean allConditionsSelected() {
        Building pickUpBuilding = bikeReservationWidgetPickUp.getSelected();
        Building dropOffBuilding = bikeReservationWidgetDropOff.getSelected();
        Calendar pickUpTime = bikeReservationWidgetPickUp.getTimeSelected();
        Calendar dropOffTime = bikeReservationWidgetDropOff.getTimeSelected();
        if (pickUpBuilding != null && dropOffBuilding != null && pickUpTime != null
                && dropOffTime != null && pickUpBuilding.getBikes() > 0) {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm");
            String pickUp = dateFormat.format(pickUpTime.getTime());
            String dropOff = dateFormat.format(dropOffTime.getTime());
            return pickUp.compareTo(dropOff) < 0;
        }
        return false;
    }

    @Override
    public Parent getRootElement() {
        return rootElement;
    }
}
