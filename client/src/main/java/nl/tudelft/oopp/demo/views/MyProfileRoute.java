package nl.tudelft.oopp.demo.views;

import static nl.tudelft.oopp.demo.communication.ImageCommunication.updateUserImage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import nl.tudelft.oopp.demo.communication.AuthenticationCommunication;
import nl.tudelft.oopp.demo.communication.reservation.RoomReservationCommunication;
import nl.tudelft.oopp.demo.core.Route;
import nl.tudelft.oopp.demo.entities.reservation.RoomReservation;
import nl.tudelft.oopp.demo.widgets.AppBar;
import nl.tudelft.oopp.demo.widgets.ImageSelectorWidget;
import nl.tudelft.oopp.demo.widgets.RectangularImageButton;

public class MyProfileRoute extends Route {
    private VBox rootElement;
    private VBox userDetails;
    private VBox eventContainer;
    private VBox newEventBox;

    private HBox horizontalContainer;

    ToggleButton upcoming;
    ToggleButton past;
    ToggleButton newEvent;
    private Rectangle rect;

    private List<RoomReservation> pastReservations;
    private List<RoomReservation> futureReservations;

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

                if (!upcoming.isSelected() && !past.isSelected() && !newEvent.isSelected()) {
                    upcoming.setSelected(true);
                }

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
        removeObject(newEventBox);
    }

    private void sortMyReservationsByDate() {
        pastReservations = new ArrayList<>();
        futureReservations = new ArrayList<>();
        Date dateObj = new Date();
        currentDate = dateFormat.format(dateObj);
        List<RoomReservation> allMyReservations = RoomReservationCommunication.getMyReservations();
        for (RoomReservation reservation : allMyReservations) {
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

    private void makeListOfReservations(List<RoomReservation> reservations) {
        scrollPane = new ScrollPane();
        VBox reservationsList = new VBox();
        reservationsList.setSpacing(2);

        for (int i = 0; i < reservations.size(); i++) {
            RoomReservation reservation = reservations.get(i);
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

            Text roomText = new Text("Type: my reservation");
            if (reservation.getRoom() != null) {
                roomText = new Text("Room: " + reservation.getRoom().getRoomCode());
            }

            currentReservation.getChildren().add(roomText);

            int finalI = i;
            Button delete = new Button("X");
            delete.setPrefSize(30, 30);
            delete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    int id = reservations.get(finalI).getId();
                    RoomReservationCommunication.deleteReservationFromDatabase(id);
                    if (past.isSelected()) {
                        displayPastEvents();
                    } else if (upcoming.isSelected()) {
                        displayUpcomingEvents();
                    }
                }
            });
            StackPane deletePane = new StackPane(delete);
            deletePane.setAlignment(Pos.CENTER_RIGHT);

            currentReservation.getChildren().add(deletePane);
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
        newEventBox = new VBox();
        newEventBox.setMaxWidth(eventContainer.getWidth() / 2);
        newEventBox.setSpacing(3);
        newEventBox.setPadding(new Insets(0, 16, 16, 0));
        TextField t1 = new TextField();
        t1.setPromptText("Type");
        TextField t2 = new TextField();
        t2.setPromptText("DD/MM/YYYY");
        TextField t3 = new TextField();
        t3.setPromptText("HH:MM");
        TextField t4 = new TextField();
        t4.setPromptText("HH:MM");
        Button submit = new Button("Submit");
        Text txt1 = new Text("Type");
        Text txt2 = new Text("Date");
        Text txt3 = new Text("Start Time");
        Text txt4 = new Text("End Time");
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String type = "-" + t1.getText();
                String timeslot = t2.getText() + "," + t3.getText()
                        + " - " + t2.getText() + "," + t4.getText();
                if (t1.getText() != null
                        && t2.getText() != null
                        && t3.getText() != null
                        && t4.getText() != null) {
                    RoomReservationCommunication.addReservationToDatabase(
                            AuthenticationCommunication.myUserId, type, timeslot);
                }

            }
        });
        newEventBox.getChildren().addAll(txt1, t1, txt2, t2, txt3, t3, txt4, t4, submit);
        eventContainer.getChildren().addAll(newEventBox);
    }

    private void addUserInformation() {
        horizontalContainer = new HBox();
        horizontalContainer.setAlignment(Pos.TOP_LEFT);
        horizontalContainer.setPadding(new Insets(16, 16, 16, 16));
        horizontalContainer.setSpacing(10);
        loadHorizontalContainer();

        //Integer rand = Math.abs(new Random().nextInt()) % AuthenticationCommunication.ids.size();
        //System.out.println(rand);
        //String imageId = AuthenticationCommunication.ids.get(rand);


        rootElement.getChildren().add(horizontalContainer);
    }

    private void loadHorizontalContainer() {
        Image profileImage = new Image(AuthenticationCommunication.myImageUrl);
        RectangularImageButton profilePicture = new RectangularImageButton(profileImage, "");
        profilePicture.setFitHeight(90);
        horizontalContainer.getChildren().add(profilePicture);

        userDetails = new VBox();
        userDetails.setAlignment(Pos.CENTER_LEFT);
        userDetails.setPadding(new Insets(16, 16, 16, 16));
        userDetails.setSpacing(10);

        userDetails.getChildren().add(oneBoldOneRegular(
                "Username: ", AuthenticationCommunication.myUsername));
        userDetails.getChildren().add(oneBoldOneRegular(
                "Role: ", AuthenticationCommunication.myUserRole));

        ImageSelectorWidget imageSelectorWidget = new ImageSelectorWidget();

        Button save = new Button("Save");
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updateUserImage(imageSelectorWidget.getImage());
                horizontalContainer.getChildren().clear();
                imageSelectorWidget.removeChild(save);
                loadHorizontalContainer();
            }
        });
        imageSelectorWidget.addChild(save);
        horizontalContainer.getChildren().addAll(userDetails, imageSelectorWidget);
        //
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
