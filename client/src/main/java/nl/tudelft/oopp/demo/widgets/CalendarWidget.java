package nl.tudelft.oopp.demo.widgets;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * The CalendarWidget is used to display a calendar whose dates can be selected.
 */
public class CalendarWidget extends VBox {
    private GridPane calendarGrid;
    private Calendar currentMonth;

    private HBox topBar;
    private HBox monthTextContainer;
    private Button leftArrow;
    private ToggleButton rightArrow;
    private Text monthText;

    private List<DayBox> dayBoxes;
    private List<DateBox> dateBoxes;

    private int selectedDay;
    private Listener listener;

    private Rectangle filler;

    /**
     * Creates a new CalendarWidget.
     */
    public CalendarWidget() {
        setStyle("-fx-background-color: -primary-color-light; -fx-background-radius: 8;");
        calendarGrid = new GridPane();
        calendarGrid.setHgap(8);
        calendarGrid.setVgap(8);
        currentMonth = Calendar.getInstance();
        selectedDay = currentMonth.get(Calendar.DAY_OF_MONTH);
        currentMonth.set(Calendar.DAY_OF_MONTH, 1);
        setPadding(new Insets(8));

        dayBoxes = new ArrayList<>();
        char[] days = {'M', 'T', 'W', 'T', 'F', 'S', 'S'};
        for (int i = 0; i < 7; i++) {
            DayBox dayBox = new DayBox(days[i]);
            GridPane.setConstraints(dayBox, i, 1, 1, 1);
            dayBoxes.add(dayBox);
        }
        dateBoxes = new ArrayList<>();
        for (int i = 0; i < 31; i++) {
            DateBox dateBox = new DateBox(i + 1, true, ((i + 1) == selectedDay), false);
            int finalI = i;
            dateBox.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    setSelectedDay(finalI + 1);

                    Calendar selected = (Calendar) currentMonth.clone();
                    selected.set(Calendar.DAY_OF_MONTH, finalI + 1);
                    if (listener != null) {
                        listener.onDayClicked(selected);
                    }
                }
            });
            dateBoxes.add(dateBox);
        }

        filler = new Rectangle();
        filler.setFill(Color.TRANSPARENT);
        GridPane.setConstraints(filler, 6, 7, 1, 1);

        leftArrow = new Button();
        leftArrow.getStyleClass().add("left-arrow");
        leftArrow.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                prevMonth();
            }
        });

        rightArrow = new ToggleButton();
        rightArrow.getStyleClass().add("right-arrow");
        rightArrow.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                nextMonth();
            }
        });

        topBar = new HBox();
        monthTextContainer = new HBox();
        monthTextContainer.setAlignment(Pos.CENTER);
        monthText = new Text("");
        monthText.getStyleClass().add("month-text");
        monthTextContainer.getChildren().add(monthText);
        topBar.getChildren().addAll(leftArrow, monthTextContainer, rightArrow);
        getChildren().add(topBar);
        getChildren().add(calendarGrid);

        redrawCalendar();

        resizeDisplay(getPrefWidth());
        prefWidthProperty().addListener((obs, oldWidth, newWidth) -> {
            resizeDisplay(newWidth.doubleValue());
        });
    }

    private void setSelectedDay(int selectedDay) {
        dateBoxes.get(this.selectedDay - 1).setSelected(false);
        this.selectedDay = selectedDay;
        dateBoxes.get(this.selectedDay - 1).setSelected(true);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    private void nextMonth() {
        currentMonth.add(Calendar.MONTH, 1);

        redrawCalendar();

        Calendar selected = (Calendar) currentMonth.clone();
        selected.set(Calendar.DAY_OF_MONTH, selectedDay + 1);
        if (listener != null) {
            listener.onDayClicked(selected);
        }
    }

    private void prevMonth() {
        currentMonth.add(Calendar.MONTH, -1);

        redrawCalendar();

        Calendar selected = (Calendar) currentMonth.clone();
        selected.set(Calendar.DAY_OF_MONTH, selectedDay + 1);
        if (listener != null) {
            listener.onDayClicked(selected);
        }
    }

    private void resizeDisplay(double newWidth) {
        for (DayBox dayBox : dayBoxes) {
            dayBox.setPrefWidth(newWidth / 7);
        }
        for (DateBox dateBox : dateBoxes) {
            dateBox.setPrefWidth(newWidth / 7);
        }
        filler.setWidth(newWidth / 7);
        filler.setHeight(newWidth / 7);

        monthText.setStyle("-fx-font-size: " + newWidth / 7 / 3);

        monthTextContainer.setPrefWidth(newWidth / 7 * 5 + 8 * 6);
        monthTextContainer.setPrefHeight(newWidth / 7);
        leftArrow.setPrefWidth(newWidth / 7);
        leftArrow.setMinHeight(newWidth / 7);
        rightArrow.setPrefWidth(newWidth / 7);
        rightArrow.setPrefHeight(newWidth / 7);
    }

    private void redrawCalendar() {
        calendarGrid.getChildren().clear();

        calendarGrid.getChildren().add(filler);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
        monthText.setText(dateFormat.format(currentMonth.getTime()));

        calendarGrid.getChildren().addAll(dayBoxes);

        Calendar currentTime = Calendar.getInstance();
        boolean isCurrentMonth =
                (currentMonth.get(Calendar.MONTH) == currentTime.get(Calendar.MONTH)
            && (currentMonth.get(Calendar.YEAR) == currentTime.get(Calendar.YEAR)));
        int firstPosition = currentMonth.get(Calendar.DAY_OF_WEEK) - 2;
        if (firstPosition == -1) {
            firstPosition = 6;
        }
        for (int i = 0; i < currentMonth.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            DateBox dateBox = dateBoxes.get(i);

            if (isCurrentMonth && (i + 1) == currentTime.get(Calendar.DAY_OF_MONTH)) {
                dateBox.setToday(true);
            } else {
                dateBox.setToday(false);
            }

            GridPane.setConstraints(
                    dateBox,
                    (i + firstPosition) % 7,
                    (i + firstPosition) / 7 + 2,
                    1,
                    1
            );
            calendarGrid.getChildren().add(dateBox);
        }
    }

    private class DayBox extends StackPane {
        private char day;

        private VBox container;
        private Text numberText;

        public DayBox(char day) {
            this.day = day;

            createComponents();

            resizeDisplay(getWidth());
            prefWidthProperty().addListener((obs, oldWidth, newWidth) -> {
                resizeDisplay(newWidth.doubleValue());
            });
        }

        private void createComponents() {
            numberText = new Text("" + day);
            numberText.getStyleClass().add("day-box-text");

            container = new VBox();
            container.setAlignment(Pos.CENTER);
            container.getChildren().add(numberText);
            getChildren().add(container);
        }

        private void resizeDisplay(double newWidth) {
            container.setMinWidth(newWidth);
            container.setMinHeight(newWidth);
            container.setMaxWidth(newWidth);
            container.setMaxHeight(newWidth);
        }
    }

    private class DateBox extends StackPane {
        private int day;
        private boolean isAvailable;
        private boolean isSelected;
        private boolean isToday;

        private VBox background;
        private Text numberText;
        private VBox hoverGlow;
        private Rectangle hoverGlowRectangle;

        public DateBox(int day, boolean isAvailable, boolean isSelected, boolean isToday) {
            this.day = day;
            this.isAvailable = isAvailable;
            this.isSelected = isSelected;
            this.isToday = isToday;

            createComponents();

            resizeDisplay(getWidth());
            prefWidthProperty().addListener((obs, oldWidth, newWidth) -> {
                resizeDisplay(newWidth.doubleValue());
            });

            setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    hoverGlow.setVisible(true);
                }
            });
            setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    hoverGlow.setVisible(false);
                }
            });

            updateBackground();
        }

        public void setSelected(boolean selected) {
            this.isSelected = selected;
            updateBackground();
        }

        public void setAvailable(boolean available) {
            this.isAvailable = available;
            updateBackground();
        }

        public void setToday(boolean today) {
            this.isToday = today;
            updateBackground();
        }

        private void updateBackground() {
            background.getStyleClass().clear();
            if (isSelected) {
                background.getStyleClass().add("selected-date-box");
            } else if (isAvailable) {
                background.getStyleClass().add("available-date-box");
            } else {
                background.getStyleClass().add("unavailable-date-box");
            }
        }

        private void createComponents() {
            background = new VBox();
            background.setAlignment(Pos.CENTER);
            getChildren().add(background);

            numberText = new Text("" + day);
            numberText.getStyleClass().add("date-box-text");
            background.getChildren().add(numberText);

            hoverGlow = new VBox();
            hoverGlowRectangle = new Rectangle();
            hoverGlow.getChildren().add(hoverGlowRectangle);
            hoverGlowRectangle.setFill(Color.TRANSPARENT);
            hoverGlow.getStyleClass().add("hover-glow");
            hoverGlow.setVisible(false);
            getChildren().add(hoverGlow);
        }

        private void resizeDisplay(double newWidth) {
            background.setMinWidth(newWidth);
            background.setMinHeight(newWidth);
            background.setMaxWidth(newWidth);
            background.setMaxHeight(newWidth);

            hoverGlowRectangle.setWidth(newWidth);
            hoverGlowRectangle.setHeight(newWidth);
        }
    }

    public interface Listener {
        void onDayClicked(Calendar day);
    }
}
