package nl.tudelft.oopp.demo.views;

import java.util.Calendar;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.tudelft.oopp.demo.core.Route;
import nl.tudelft.oopp.demo.entities.BikeReservation;
import nl.tudelft.oopp.demo.widgets.*;



public class BikesReservationRoute extends Route {

    private VBox rootElement;

    private HBox horizontalContainer;

    private CalendarWidget calendarWidget;
    private AgendaWidget agendaWidget;
    private BikeReservationWidget bikeReservationWidget;

    private Calendar selectedDate = Calendar.getInstance();
    private Calendar fromTime;
    private Calendar toTime;

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
                agendaWidget.removeSelection();
                selectedDate = day;
                bikeReservationWidget.setPeriod(null, null);
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

                bikeReservationWidget.setPeriod(fromTime, toTime);
                bikeReservationWidget.dateSelected(true);
            }
        });

        bikeReservationWidget = new BikeReservationWidget();

        horizontalContainer.getChildren().addAll(calendarWidget, agendaWidget,
                bikeReservationWidget);

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


    }

    private void resizeDisplay(double newWidth) {
        calendarWidget.setPrefWidth(newWidth * 0.3);
        agendaWidget.setPrefWidth(newWidth * 0.3);
        bikeReservationWidget.setPrefWidth(newWidth * 0.3);
    }

    @Override
    public Parent getRootElement() {
        return rootElement;
    }
}
