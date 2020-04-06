package nl.tudelft.oopp.demo.views;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.tudelft.oopp.demo.communication.AuthenticationCommunication;
import nl.tudelft.oopp.demo.communication.ImageCommunication;
import nl.tudelft.oopp.demo.communication.reservation.RoomReservationCommunication;
import nl.tudelft.oopp.demo.core.PopupRoute;
import nl.tudelft.oopp.demo.core.Route;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.entities.Weekdays;
import nl.tudelft.oopp.demo.entities.reservation.RoomReservation;
import nl.tudelft.oopp.demo.widgets.*;

public class RoomReservationRoute extends PopupRoute {
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
    private Image roomImage;
    private Weekdays openingHours;

    /**
     * Instantiates a new RoomReservationRoute which displays the options
     * to reserve a room.
     * @param room the room to be reserved
     */
    public RoomReservationRoute(Room room) {
        super(new VBox());
        this.room = room;
        openingHours = new Weekdays(room.getBuilding().getOpeningHours());
        rootElement = (VBox) getMainElement();

        AppBar appBar = new AppBar();
        rootElement.getChildren().add(appBar);

        showPopup(new LoadingPopup(), false);
        new Thread(() -> {
            loadData();

            Platform.runLater(() -> {
                buildDisplay();
                removePopup();
            });
        }).start();
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
            Calendar now = Calendar.getInstance();
            int nowDay = now.get(Calendar.DAY_OF_WEEK);
            int nowMonth = now.get(Calendar.DAY_OF_MONTH);
            int dayClean = selectedDate.get(Calendar.DAY_OF_WEEK);
            int month = selectedDate.get(Calendar.DAY_OF_MONTH);
            int hours = now.get(Calendar.HOUR_OF_DAY);
            int minutes = now.get(Calendar.MINUTE);
            if (minutes != 0) {
                hours++;
            }
            if (nowDay == dayClean
                    && nowMonth == month && hours > openingHour) {
                openingHour = hours;
            }
            agendaWidget.setMinHour(openingHour);
            int closingHour = Integer.parseInt(openingTime.substring(6, 8));
            agendaWidget.setMaxHour(closingHour - 1);
        } else {
            agendaWidget.setMinHour(-1);
            agendaWidget.setMaxHour(-1);
        }
    }

    private void loadData() {
        reservations = RoomReservationCommunication.getAllReservations();
        roomImage = new Image(ImageCommunication.getRoomImageUrl(room.getRoomCode()).get(0));
    }

    private void buildDisplay() {
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
        reservationWidget = new ReservationWidget(roomImage, room, new ReservationWidget.Listener() {
            @Override
            public void onReserveClicked() {
                if (fromTime != null) {
                    SimpleDateFormat dayFormat = new SimpleDateFormat("EEE. d MMMM");
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                    String confirmationString = String.format(
                            "Are you sure you want to reserve %s on %s from %s to %s?",
                            room.getName(),
                            dayFormat.format(fromTime.getTime()),
                            timeFormat.format(fromTime.getTime()),
                            timeFormat.format(toTime.getTime())
                    );
                    showPopup(new ConfirmationPopup(
                            "Confirm reservation",
                            confirmationString,
                            new ConfirmationPopup.Listener() {
                                @Override
                                public void onConfirmClicked() {
                                    reserveRoom();
                                }

                                @Override
                                public void onCancelClicked() {
                                    removePopup();
                                }
                            }), true);
                }
            }
        });
        horizontalContainer.getChildren().add(calendarWidget);
        horizontalContainer.getChildren().add(agendaWidget);
        horizontalContainer.getChildren().add(reservationWidget);

        resizeDisplay(getRoutingScene().getWidth());
        agendaWidget.setPrefHeight(getRoutingScene().getHeight() * 0.65);
        getRoutingScene().widthProperty().addListener((obs, oldWidth, newWidth) -> {
            if (getRoutingScene() != null) {
                resizeDisplay(newWidth.doubleValue());
            }
        });
        getRoutingScene().heightProperty().addListener((obs, oldHeight, newHeight) -> {
            if (getRoutingScene() != null) {
                agendaWidget.setPrefHeight(newHeight.doubleValue() * 0.65);
            }
        });

        agendaWidget.setAvailabilities(computeAvailabilities());
        setOpeningTime();
    }

    private void reserveRoom() {
        showPopup(new LoadingPopup(), false);
        new Thread(() -> {
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

            Platform.runLater(() -> {
                SimpleDateFormat dayFormat = new SimpleDateFormat("EEE. d MMMM");
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                String infoString = String.format(
                        "%s has been successfully reserved on %s from %s to %s",
                        room.getName(),
                        dayFormat.format(fromTime.getTime()),
                        timeFormat.format(fromTime.getTime()),
                        timeFormat.format(toTime.getTime())
                );
                showPopup(new InformationPopup(
                        "Success",
                        infoString,
                        new InformationPopup.Listener() {
                            @Override
                            public void onOkClicked() {
                                removePopup();
                            }
                        }
                ), false);
            });
        }).start();
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
}
