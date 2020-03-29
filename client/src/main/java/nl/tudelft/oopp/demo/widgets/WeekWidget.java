package nl.tudelft.oopp.demo.widgets;

import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import nl.tudelft.oopp.demo.entities.Weekdays;


public class WeekWidget extends VBox {

    private List<DayBox> dayBoxList;
    private List<StackPane> hours;
    private List<Label> labels;
    private Weekdays weekDays;
    private int current;

    private HBox days;
    private HBox times;

    /**
     * Constructor for a WeekWidget that shows a week and it's opening hours.
     * Functionality to change the currently selected day to another colour is added,
     * but no other logic is added so when using this WeekWidget, you should set the
     * on action of the StackPanes again with the current EventHandler and the
     * wanted functionality.
     * @param weekdays The opening hours of the week.
     */
    public WeekWidget(Weekdays weekdays) {
        this.weekDays = weekdays;
        this.current = -1;
        dayBoxList = new ArrayList<>();
        char[] days = {'M', 'T', 'W', 'T', 'F', 'S', 'S'};
        String cssDays = "-fx-border-color: black;\n"
                + "-fx-border-insets: 4\n;"
                + "-fx-border-style: solid\n;"
                + "-fx-border-width: 1;"
                + "-fx-border-radius: 10;"
                + "-fx-background-color: #6cffab;"
                + "-fx-background-insets: 4;"
                + "-fx-background-radius: 10;";
        for (int i = 0; i < 7; i++) {
            DayBox dayBox = new DayBox(days[i]);
            dayBox.setMaxSize(80, 40);
            dayBox.setMinSize(80, 40);
            dayBox.setStyle(cssDays);
            dayBoxList.add(dayBox);
        }
        this.days = new HBox();
        this.days.getChildren().addAll(dayBoxList);
        this.times = new HBox();
        hours = new ArrayList<>(7);
        labels = new ArrayList<>(7);
        for (int i = 0; i < 7; i++) {
            Label label = new Label(weekDays.getWeekdays().get(i));
            labels.add(label);
            VBox container = new VBox();
            container.setAlignment(Pos.CENTER);
            container.setMinSize(80, 60);
            container.setMaxSize(80, 60);
            container.getChildren().add(label);
            StackPane stackPane = new StackPane();
            stackPane.setId(String.valueOf(i));
            String css = "-fx-border-color: black;\n"
                    + "-fx-border-insets: 4\n;"
                    + "-fx-border-style: solid\n;"
                    + "-fx-border-width: 1;"
                    + "-fx-border-radius: 10;"
                    + "-fx-background-color: yellow;"
                    + "-fx-background-insets: 4;"
                    + "-fx-background-radius: 10;";
            container.setStyle(css);
            stackPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    StackPane source = (StackPane) event.getSource();
                    current = Integer.parseInt(source.getId());
                    redraw();
                }
            });
            hours.add(stackPane);
            stackPane.getChildren().add(container);
            times.getChildren().add(stackPane);
        }
        Text header = new Text("The week currently");
        header.setTextAlignment(TextAlignment.CENTER);
        header.setFont(new Font(22));
        StackPane textContainer = new StackPane();
        textContainer.getChildren().add(header);
        textContainer.setMaxSize(560, 40);
        textContainer.setMinSize(560, 40);
        this.getChildren().addAll(textContainer, this.days, times);
    }

    /**
     * Method that colours the currently selected day orange and resets the other ones to yellow.
     */
    public void redraw() {
        String css = "-fx-border-color: black;\n"
                + "-fx-border-insets: 4\n;"
                + "-fx-border-style: solid\n;"
                + "-fx-border-width: 1;"
                + "-fx-border-radius: 10;"
                + "-fx-background-color: yellow;"
                + "-fx-background-insets: 4;"
                + "-fx-background-radius: 10;";
        for (int i = 0; i < 7; i++) {
            hours.get(i).getChildren().get(0).setStyle(css);
        }
        String cssCurrent = "-fx-border-color: black;\n"
                + "-fx-border-insets: 4\n;"
                + "-fx-border-style: solid\n;"
                + "-fx-border-width: 1;"
                + "-fx-border-radius: 10;"
                + "-fx-background-color: orange;"
                + "-fx-background-insets: 4;"
                + "-fx-background-radius: 10;";
        if (current >= 0 && current < 7) {
            hours.get(current).getChildren().get(0).setStyle(cssCurrent);
        }
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getCurrent() {
        return current;
    }

    /**
     * Method that changes the opening hours of a day in the display and in the WeekDays Object.
     * Will display the text in red if the opening hours are incorrect.
     * @param id The day which has to be changed, 0 is monday, 6 is sunday etc.
     * @param newOpeningHours The new opening hours that have to be changed
     */
    public void dayChanged(int id, String newOpeningHours) {
        if (id < 0 || id > 6) {
            return;
        }

        if (newOpeningHours.equals(Weekdays.getClosed())) {
            weekDays.setClosed(id);
        } else {
            weekDays.getWeekdays().set(id, newOpeningHours);
        }

        if (!newOpeningHours.equals(Weekdays.getClosed())
                && newOpeningHours.split("-")[0].compareTo(newOpeningHours.split("-")[1]) >= 0) {
            labels.get(id).setStyle("-fx-text-fill: red");
        } else {
            labels.get(id).setStyle("-fx-text-fill: black");
        }
        labels.get(id).setText(newOpeningHours);
    }

    /**
     * Method that sets all of the days to these opening hours.
     * @param newOpeningHours Opening hours to be set.
     */
    public void setAllDays(String newOpeningHours) {
        for (int i = 0; i < 7; i++) {
            dayChanged(i, newOpeningHours);
        }
    }

    /**
     * Method that sets all of the days except the weekend to these opening hours.
     * @param newOpeningHours Opening hours to be set.
     */
    public void weekendClosed(String newOpeningHours) {
        List<String> weekdays = weekDays.getWeekdays();
        for (int i = 0; i < 5; i++) {
            dayChanged(i, newOpeningHours);
        }
        dayChanged(5, Weekdays.getClosed());
        dayChanged(6, Weekdays.getClosed());
    }

    public Weekdays getWeekDays() {
        return weekDays;
    }

    public HBox getTimes() {
        return times;
    }
}
