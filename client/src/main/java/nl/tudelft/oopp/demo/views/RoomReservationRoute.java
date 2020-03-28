package nl.tudelft.oopp.demo.views;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.tudelft.oopp.demo.communication.AuthenticationCommunication;
import nl.tudelft.oopp.demo.communication.RoomReservationCommunication;
import nl.tudelft.oopp.demo.core.Route;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.widgets.AgendaWidget;
import nl.tudelft.oopp.demo.widgets.AppBar;
import nl.tudelft.oopp.demo.widgets.CalendarWidget;
import nl.tudelft.oopp.demo.widgets.ReservationWidget;

public class RoomReservationRoute extends Route {
    private VBox rootElement;

    private HBox horizontalContainer;

    private CalendarWidget calendarWidget;
    private AgendaWidget agendaWidget;
    private ReservationWidget reservationWidget;

    private Calendar selectedDate = Calendar.getInstance();
    private Calendar fromTime;
    private Calendar toTime;

    private List<RoomReservation> reservations;
    private Room room;

    /**
     * Instantiates a new RoomReservationRoute which displays the options
     * to reserve a room.
     * @param room the room to be reserved
     */
    public RoomReservationRoute(Room room) {
        this.room = room;
        rootElement = new VBox();

        reservations = RoomReservationCommunication.getAllReservations();

        AppBar appBar = new AppBar();
        rootElement.getChildren().add(appBar);

        horizontalContainer = new HBox();
        horizontalContainer.setAlignment(Pos.CENTER);
        horizontalContainer.setPadding(new Insets(16, 16, 16, 16));
        horizontalContainer.setSpacing(32);
        rootElement.getChildren().add(horizontalContainer);

        calendarWidget = new CalendarWidget();
        calendarWidget.setListener(new CalendarWidget.Listener() {
            @Override
            public void onDayClicked(Calendar day) {
                agendaWidget.removeSelection();
                selectedDate = day;
                reservationWidget.setPeriod(null, null);
                agendaWidget.setAvailabilities(computeAvailabilities());
            }
        });
        agendaWidget = new AgendaWidget(new AgendaWidget.Listener() {
            @Override
            public void onBlockSelected(int begin, int end, boolean available) {
                fromTime = (Calendar) selectedDate.clone();
                fromTime.set(Calendar.HOUR_OF_DAY, begin);
                fromTime.set(Calendar.MINUTE, 0);
                toTime = (Calendar) selectedDate.clone();
                toTime.set(Calendar.HOUR_OF_DAY, end);
                toTime.set(Calendar.MINUTE, 0);

                reservationWidget.setPeriod(fromTime, toTime);
                reservationWidget.setAvailable(available);
            }
        });
        reservationWidget = new ReservationWidget(room, new ReservationWidget.Listener() {
            @Override
            public void onReserveClicked() {
                if (fromTime != null) {
                    int user = AuthenticationCommunication.myUserId;
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy,HH:mm");
                    String fromString = format.format(fromTime.getTime());
                    String toString = format.format(toTime.getTime());
                    String timeslot = String.format("%s - %s", fromString, toString);
                    RoomReservationCommunication
                            .addReservationToDatabase(user, room.getRoomCode(), timeslot);
                    reservations.add(new RoomReservation(user, room, timeslot));
                    agendaWidget.setAvailabilities(computeAvailabilities());
                    reservationWidget.setAvailable(false);
                    System.out.println(user + " " + room.getRoomCode() + " " + timeslot);
                }
            }
        });
        horizontalContainer.getChildren().add(calendarWidget);
        horizontalContainer.getChildren().add(agendaWidget);
        horizontalContainer.getChildren().add(reservationWidget);

        horizontalContainer.sceneProperty().addListener((obs2, oldScene, newScene) -> {
            if (newScene != null) {
                resizeDisplay(newScene.getWidth());
                agendaWidget.setPrefHeight(newScene.getHeight() * 0.65);
                newScene.widthProperty().addListener((obs, oldWidth, newWidth) -> {
                    resizeDisplay(newWidth.doubleValue());
                });
                newScene.heightProperty().addListener((obs, oldHeight, newHeight) -> {
                    agendaWidget.setPrefHeight(newHeight.doubleValue() * 0.65);
                });
            }
        });

        agendaWidget.setAvailabilities(computeAvailabilities());
    }

    private boolean[] computeAvailabilities() {
        boolean[] availabilities = new boolean[24];
        for (int i = 0; i < 24; i++) {
            availabilities[i] = true;
        }
        for (RoomReservation reservation : reservations) {
            System.out.println(reservation);
            if (reservation.getRoom() != null && reservation.getRoom().getRoomCode().equals(room.getRoomCode())) {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy,HH:mm");
                try {
                    String fromTimeString = reservation.getTimeSlot().substring(0, 16);
                    Calendar fromTime = Calendar.getInstance();
                    fromTime.setTime(format.parse(fromTimeString));
                    String toTimeString = reservation.getTimeSlot().substring(19);
                    Calendar toTime = Calendar.getInstance();
                    toTime.setTime(format.parse(toTimeString));

                    boolean sameDay =
                            (fromTime.get(Calendar.DAY_OF_YEAR)
                                    == selectedDate.get(Calendar.DAY_OF_YEAR))
                            && (fromTime.get(Calendar.YEAR)
                                    == selectedDate.get(Calendar.YEAR));

                    System.out.println(sameDay);
                    System.out.println(fromTime.get(Calendar.HOUR_OF_DAY));
                    if (sameDay) {
                        availabilities[fromTime.get(Calendar.HOUR_OF_DAY)] = false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return availabilities;
    }



    private void resizeDisplay(double newWidth) {
        calendarWidget.setPrefWidth(newWidth * 0.3);
        agendaWidget.setPrefWidth(newWidth * 0.3);
        reservationWidget.setPrefWidth(newWidth * 0.3);
    }

    @Override
    public Parent getRootElement() {
        return rootElement;
    }
}
