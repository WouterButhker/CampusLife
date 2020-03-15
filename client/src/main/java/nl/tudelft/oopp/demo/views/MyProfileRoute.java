package nl.tudelft.oopp.demo.views;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import nl.tudelft.oopp.demo.communication.AuthenticationCommunication;
import nl.tudelft.oopp.demo.communication.ReservationCommunication;
import nl.tudelft.oopp.demo.core.Route;
import nl.tudelft.oopp.demo.entities.Reservation;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.widgets.*;

public class MyProfileRoute extends Route {
    private VBox rootElement;
    private VBox userDetails;
    private VBox eventContainer;

    private HBox horizontalContainer;

    ToggleButton upcoming;
    ToggleButton past;
    ToggleButton newEvent;
    private Rectangle rect;

    private List<Reservation> pastReservations;
    private List<Reservation> futureReservations;

    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy,HH:mm");
    private String currentDate;

    private ScrollPane scrollPane;

    /**
     * Instantiates a new MyProfileRoute.
     */
    public MyProfileRoute() {
        rootElement = new VBox();
        Boolean isAdmin = false;
        if (AuthenticationCommunication.myUserRole.equals("Admin")) {
            isAdmin = true;
        }
        AppBar appBar = new AppBar(isAdmin);
        rootElement.getChildren().add(appBar);
        addUserInformation();
        eventContainer = new VBox();
        eventContainer.setPadding(new Insets(0, 16, 16, 16));
        addToggleButton();
        rootElement.getChildren().addAll(eventContainer);
    }

    private void addToggleButton() {

        final ToggleGroup group = new ToggleGroup();
        upcoming = new ToggleButton("Upcoming events");
        upcoming.setSelected(true);
        upcoming.setToggleGroup(group);
        past = new ToggleButton("Past events");
        past.setToggleGroup(group);
        newEvent = new ToggleButton("New event");
        newEvent.setToggleGroup(group);

        past.setUserData(Color.LIGHTBLUE);
        newEvent.setUserData(Color.SALMON);

        rect = new Rectangle(145, 50);
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle toggle, Toggle newToggle) {
                if (past.isSelected()) {
                    displayPastEvents();
                } else if (newEvent.isSelected()) {
                    displayNewEvent();
                } else {
                    displayUpcomingEvents();
                }
            }
        });
        HBox toggleGroupBox = new HBox();
        toggleGroupBox.getChildren().addAll(upcoming, past, newEvent);
        eventContainer.getChildren().addAll(toggleGroupBox, rect);
        displayUpcomingEvents();
    }

    private void removeObject(Object obj) {
        if (eventContainer.getChildren().contains(obj)) {
            eventContainer.getChildren().remove(eventContainer.getChildren().indexOf(obj));
        }
    }

    private void cleanBeforeDisplaying() {
        removeObject(rect);
        removeObject(scrollPane);
    }

    private void sortMyReservationsByDate() {
        pastReservations = new ArrayList<>();
        futureReservations = new ArrayList<>();
        Date dateObj = new Date();
        currentDate = dateFormat.format(dateObj);
        List<Reservation> allMyReservations = ReservationCommunication.getMyReservations();
        for (Reservation reservation : allMyReservations) {
            String endTime = reservation.getTimeSlot().substring(19);
            if (endTime.compareTo(currentDate) <= 0) {
                pastReservations.add(reservation);
            } else {
                futureReservations.add(reservation);
            }
        }
    }

    private Rectangle makeSeparator(double size) {
        Rectangle separator = new Rectangle();
        separator.setFill(Color.GRAY);
        separator.setWidth(1);
        separator.setVisible(true);
        separator.setHeight(size);
        return separator;
    }

    private void makeListOfReservations(List<Reservation> reservations) {
        scrollPane = new ScrollPane();
        VBox reservationsList = new VBox();
        reservationsList.setSpacing(2);

        for (Reservation reservation : reservations) {
            HBox currentReservation = new HBox();
            Background background = new Background(
                    new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY));
            currentReservation.setBackground(background);
            currentReservation.setAlignment(Pos.CENTER_LEFT);
            currentReservation.setSpacing(7);
            currentReservation.setPadding(new Insets(16, 16, 16, 16));

            Text dateText = new Text("Date: " + reservation.getTimeSlot().substring(0, 10));
            currentReservation.getChildren().add(dateText);
            currentReservation.getChildren().add(makeSeparator(dateText.getFont().getSize()));

            Text timeText = new Text("Time: " + reservation.getTimeSlot().substring(11, 18)
                    + " " + reservation.getTimeSlot().substring(30));
            currentReservation.getChildren().add(timeText);
            currentReservation.getChildren().add(makeSeparator(dateText.getFont().getSize()));

            Text roomText = new Text("Room: " + reservation.getRoom());
            currentReservation.getChildren().add(roomText);

            reservationsList.getChildren().add(currentReservation);
        }
        scrollPane.setContent(reservationsList);
        scrollPane.fitToWidthProperty().set(true);
    }

    private void displayUpcomingEvents() {
        cleanBeforeDisplaying();
        sortMyReservationsByDate();
        makeListOfReservations(futureReservations);
        eventContainer.getChildren().addAll(scrollPane);
    }

    private void displayPastEvents() {
        cleanBeforeDisplaying();
        sortMyReservationsByDate();
        makeListOfReservations(pastReservations);
        eventContainer.getChildren().addAll(scrollPane);
    }

    private void displayNewEvent() {
        cleanBeforeDisplaying();
        if (!eventContainer.getChildren().contains(rect)) {
            eventContainer.getChildren().addAll(rect);
        }
        rect.setFill((Color) newEvent.getUserData());
    }

    private void addUserInformation() {
        horizontalContainer = new HBox();
        horizontalContainer.setAlignment(Pos.TOP_LEFT);
        horizontalContainer.setPadding(new Insets(16, 16, 16, 16));
        horizontalContainer.setSpacing(10);

        Image profileImage = new Image("/images/myProfile.png");
        RectangularImageButton profilePicture = new RectangularImageButton(profileImage, "");
        profilePicture.setFitHeight(100);
        horizontalContainer.getChildren().add(profilePicture);

        userDetails = new VBox();
        userDetails.setAlignment(Pos.CENTER_LEFT);
        userDetails.setPadding(new Insets(16, 16, 16, 16));
        userDetails.setSpacing(10);

        userDetails.getChildren().add(oneBoldOneRegular(
                "Username: ", AuthenticationCommunication.myUsername));
        userDetails.getChildren().add(oneBoldOneRegular(
                "Role: ", AuthenticationCommunication.myUserRole));

        horizontalContainer.getChildren().add(userDetails);
        rootElement.getChildren().add(horizontalContainer);
    }

    private TextFlow oneBoldOneRegular(String boldString, String regularString) {
        TextFlow boldAndRegular = new TextFlow();
        Text boldText = new Text(boldString);
        boldText.setStyle("-fx-font-weight: bold");
        Text regularText = new Text(regularString);
        boldAndRegular.getChildren().addAll(boldText, regularText);
        return boldAndRegular;
    }

    @Override
    public Parent getRootElement() {
        return rootElement;
    }
}
