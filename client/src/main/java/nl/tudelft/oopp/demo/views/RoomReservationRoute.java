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
import nl.tudelft.oopp.demo.entities.reservation.RoomReservation;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.entities.Weekdays;
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
    private Weekdays openingHours;

    /**
     * Instantiates a new RoomReservationRoute which displays the options
     * to reserve a room.
     * @param room the room to be reserved
     */
    public RoomReservationRoute(Room room) {
        this.room = room;
        openingHours = new Weekdays(room.getBuilding().getOpeningHours());
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
                setOpeningTime();
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
        }, 5);
        reservationWidget = new ReservationWidget(room, new ReservationWidget.Listener() {
            @Override
            public void onReserveClicked() {
                if (fromTime != null) {
                    int userId = AuthenticationCommunication.myUserId;
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy,HH:mm");
                    String fromString = format.format(fromTime.getTime());
                    String toString = format.format(toTime.getTime());
                    String timeslot = String.format("%s - %s", fromString, toString);
                    String date = timeslot.substring(0, 9);
                    RoomReservationCommunication
                            .addReservationToDatabase(userId, room.getRoomCode(), timeslot);
                    reservations.add(new RoomReservation(new User(userId), room, date, timeslot));
                    agendaWidget.setAvailabilities(computeAvailabilities());
                    reservationWidget.setAvailable(false);
                    System.out.println(userId + " " + room.getRoomCode() + " " + timeslot);
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
        setOpeningTime();
    }

    private void setOpeningTime() {
        int dayOfWeek = selectedDate.get(Calendar.DAY_OF_WEEK);
        dayOfWeek -= 2;
        if (dayOfWeek == -2) {
            dayOfWeek = 5;
        } else if (dayOfWeek == -1) {
            dayOfWeek = 6;
        }
        String openingTime = openingHours.getWeekdays().get(dayOfWeek);
        if (!openingTime.equals(Weekdays.CLOSED)) {
            int openingHour = Integer.parseInt(openingTime.substring(0, 2));
            int closingHour = Integer.parseInt(openingTime.substring(6, 8));
            agendaWidget.setMinHour(openingHour);
            agendaWidget.setMaxHour(closingHour - 1);
        } else {
            agendaWidget.setMinHour(-1);
            agendaWidget.setMaxHour(-1);
        }
    }

    private boolean[] computeAvailabilities() {
        boolean[] availabilities = new boolean[24];
        for (int i = 0; i < 24; i++) {
            availabilities[i] = true;
        }
        for (RoomReservation reservation : reservations) {
            System.out.println(reservation);
            if (reservation.getRoom() != null
                    && reservation.getRoom().getRoomCode().equals(room.getRoomCode())) {
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
