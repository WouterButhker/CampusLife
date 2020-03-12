package nl.tudelft.oopp.demo.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.core.Route;
import nl.tudelft.oopp.demo.core.RoutingScene;
import nl.tudelft.oopp.demo.core.XmlRoute;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Weekdays;
import nl.tudelft.oopp.demo.widgets.AppBar;
import nl.tudelft.oopp.demo.widgets.WeekWidget;


public class AdminSceneBuildingsController implements Initializable {

    @FXML
    private VBox mainBox;
    
    @FXML
    private TextField locationInput;

    @FXML
    private TextField nameInput;

    @FXML
    private TextField buildingCodeInput;

    @FXML
    private ChoiceBox<String> fromChoicebox;

    @FXML
    private ChoiceBox<String> toChoicebox;

    @FXML
    private ChoiceBox<String> options;

    @FXML
    private ChoiceBox<String> open;

    @FXML
    private AnchorPane anchorPaneBuildings;

    @FXML
    private VBox buildingsList;

    @FXML
    private CheckBox hasBikeStationCheck;

    @FXML
    private TextField bikeAmountInput;

    @FXML
    private ScrollPane scrollPane;

    private WeekWidget week;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (toChoicebox != null && fromChoicebox != null) {
            loadOpeningHoursChoices(fromChoicebox, toChoicebox);
            loadCalendarFunctionality(fromChoicebox, toChoicebox);
            loadOpenClosed(open);
            loadOptions(options);
        }
        bikeAmountInput.setVisible(false); //the amount input is made
        // invisible before the 'has bike station' checkbox is selected.
        loadBuildings();
        addAppBar();
        AddWeekCalendar(new Weekdays());
    }

    private void addAppBar() {
        mainBox.getChildren().add(0, new AppBar());
    }

    private void AddWeekCalendar(Weekdays weekdays) {
        this.week = new WeekWidget(weekdays);
        mainBox.getChildren().add(1, week);
        List<Node> times = week.getTimes().getChildren();
        for (int i = 0; i < times.size(); i++) {
            times.get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    StackPane source = (StackPane) event.getSource();
                    int current = Integer.parseInt(source.getId());
                    week.setCurrent(current);
                    week.redraw();
                    updateCurrent();
                }
            });
        }
    }

    private void loadOpeningHoursChoices(ChoiceBox<String> from, ChoiceBox<String> to) {
        String[] times = new String[25];
        for (int i = 0; i < 25; i++) {
            if (i < 10) {
                times[i] = "0" + i + ":00";
            } else {
                times[i] = i + ":00";
            }
        }
        to.getItems().addAll(times);
        from.getItems().addAll(times);
    }

    private void loadOpenClosed(ChoiceBox<String> open) {
        String[] options = {"Open", Weekdays.getClosed()};
        open.getItems().addAll(options);
        SingleSelectionModel<String> singleSelectionModel = open.getSelectionModel();
        singleSelectionModel.selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue,
                                String newValue) {
                if (newValue.equals(options[1])) {
                    fromChoicebox.setVisible(false);
                    toChoicebox.setVisible(false);
                } else {
                    fromChoicebox.setVisible(true);
                    toChoicebox.setVisible(true);
                }
                updateCurrent();
            }
        });
        open.setValue("Open");
    }

    private void loadOptions(ChoiceBox<String> options) {
        String mondayToFriday = "Weekdays open and weekend closed";
        String allDaysOpen = "Open on all days";
        options.getItems().addAll(mondayToFriday, allDaysOpen, "Custom");
        SelectionModel<String> singleSelectionModel = options.getSelectionModel();
        singleSelectionModel.selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue,
                                String newValue) {
                updateCurrent();
                if (newValue.equals(mondayToFriday) || newValue.equals(allDaysOpen)) {
                    open.setValue("Open");
                }
            }
        });
        options.setValue("Custom");
    }

    private void loadCalendarFunctionality(ChoiceBox<String> fromChoicebox,
                                           ChoiceBox<String> toChoicebox) {
        fromChoicebox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updateCurrent();
            }
        });
        toChoicebox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updateCurrent();
            }
        });
    }

    private void updateCurrent() {
        //if the building should be closed on that day
        if (open.getValue().equals(Weekdays.getClosed())) {
            week.dayChanged(week.getCurrent(), Weekdays.getClosed());
            return;
        }

        //making the openinghours string
        String from = fromChoicebox.getValue();
        String to = toChoicebox.getValue();
        if (from == null || to == null) {
            return;
        }
        String openingHours = from + "-" + to;

        //if presets have been set
        if (options.getValue().equals("Weekdays open and weekend closed")) {
            week.weekendClosed(openingHours);
            return;
        }

        if (options.getValue().equals("Open on all days")) {
            week.setAllDays(openingHours);
            return;
        }

        week.dayChanged(week.getCurrent(), openingHours);
    }

    private void loadBuildings() {
        if (anchorPaneBuildings != null && buildingsList != null) {
            buildingsList.getChildren().clear();
            int numBuildings = BuildingCommunication.countAllBuildings();
            List<Building> buildings = BuildingCommunication.getAllBuildings();
            int height = 82 * numBuildings;
            if (height <= scrollPane.getPrefHeight()) {
                scrollPane.setPrefWidth(400);
            } else {
                scrollPane.setPrefWidth(417);
            }
            anchorPaneBuildings.setPrefHeight(height);
            //One HBox is 60 x 400
            //The entire AnchorPane holding all the HBoxes is 60*numBuildings x 400
            for (int i = 0; i < numBuildings; i++) {
                HBox building = new HBox();
                building.setMaxWidth(400);
                Image image = new Image("images/TuDelftTempIMG.jpg");
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(65);
                imageView.setFitHeight(60);
                Integer bikes = buildings.get(i).getBikes();
                String bikesString;
                if (bikes == null) {
                    bikesString = "no station";
                } else {
                    bikesString = Integer.toString(bikes);
                }
                Label text = new Label("Building Code: " + buildings.get(i).getCode()
                        + " | " + buildings.get(i).getName()
                        + "\nOpening Hours: " + buildings.get(i).getOpeningHours() + "\n"
                        + buildings.get(i).getLocation() + " | Bikes: " + bikesString);
                text.setPrefSize(225, 60);
                text.setPadding(new Insets(0, 0, 0, 10));

                int finalI = i;
                Button modify = new Button("modify");
                modify.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        createModifyPopup(buildings.get(finalI));
                    }
                });
                modify.setPrefSize(45, 40);
                modify.setPadding(new Insets(0, 0, 0,0));
                StackPane modifyPane = new StackPane(modify);
                modifyPane.setPadding(new Insets(10, 0, 10, 0));

                Button delete = new Button("delete");
                delete.setPrefSize(45, 40);
                delete.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        int code = buildings.get(finalI).getCode();
                        BuildingCommunication.deleteBuildingFromDatabase(code);
                        loadBuildings();
                    }
                });
                delete.setPadding(new Insets(0, 0,0,0));
                StackPane deletePane = new StackPane(delete);
                deletePane.setPadding(new Insets(10, 0, 10, 0));

                building.setPadding(new Insets(5, 5, 5,5));
                String css = "-fx-border-color: black;\n"
                        + "-fx-border-insets: 4\n;"
                        + "-fx-border-style: solid\n;"
                        + "-fx-border-width: 1;"
                        + "-fx-border-radius: 10;";
                building.setStyle(css);
                building.getChildren().addAll(imageView, text, modifyPane, deletePane);
                buildingsList.getChildren().add(building);
            }
        }
    }

    @FXML
    private void refreshBuildingsPage() throws Exception {
        RoutingScene scene = (RoutingScene) mainBox.getScene();
        Route route = new XmlRoute(getClass().getResource("/AdminSceneBuildings.fxml"));
        scene.popRoute();
        scene.pushRoute(route);
    }

    @FXML
    private void submitNewBuilding() {
        String location = locationInput.getText();
        String name = nameInput.getText();
        boolean codeFound = false;
        int buildingCode = 0;
        try {
            buildingCode = Integer.parseInt(buildingCodeInput.getText().trim());
            codeFound = true;
        } catch (NumberFormatException e) {
            System.out.println("Not a proper number");
        }
        String openingHours = week.getWeekDays().toString();
        Text submitStatus = new Text();
        Integer bikes = null;
        if (hasBikeStationCheck.isSelected()) {
            try {
                bikes = Integer.parseInt(bikeAmountInput.getText());
            } catch (NumberFormatException e) {
                submitStatus.setText("Invalid number");
            }
        }
        if (!location.equals("") && !name.equals("") && codeFound
               && week.getWeekDays().checkCorrectness()) {
            BuildingCommunication.addBuildingToDatabase(buildingCode,
                    name, location, openingHours, bikes);
            submitStatus.setText("Building successfully added!");
            try {
                refreshBuildingsPage();
            } catch (Exception e) {
                System.out.println("Refresh failed");
            }
        } else {
            submitStatus.setText("The input is wrong or not all fields are entered");
        }

        if (!codeFound) {
            submitStatus.setText("The building code has to be a number!");
        }

        if (!week.getWeekDays().checkCorrectness()) {
            submitStatus.setText("These opening hours don't make sense");
        }

        Button back = new Button("Okay! take me back");
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Button button = (Button) event.getSource();
                Stage stage = (Stage) button.getScene().getWindow();
                stage.close();
                loadBuildings();
            }
        });
        VBox buildingBox = new VBox(submitStatus, back);
        buildingBox.setPrefSize(300, 200);
        buildingBox.setAlignment(Pos.CENTER);
        AnchorPane root = new AnchorPane(buildingBox);
        root.setPrefSize(300, 200);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        //stage.initOwner(mainBox.getScene().getWindow());
        stage.showAndWait();
    }

    private void createModifyPopup(Building building) {
        VBox root = new VBox();
        root.setPrefSize(600, 700);

        Text header = new Text("Modify your building");
        header.setFont(Font.font("System", 22));
        HBox headerBox = new HBox(header);
        headerBox.setPadding(new Insets(20, 200,10,200));

        Label addressText = new Label("Address :");
        addressText.setPadding(new Insets(20, 275,0,275));
        HBox addressTextBox = new HBox(addressText);
        addressTextBox.setPadding(new Insets(0));

        Pane spacer1 = new Pane();
        spacer1.setPrefSize(225, 20);
        TextField address = new TextField(building.getLocation());
        address.setPrefSize(150,20);
        HBox addressBox = new HBox(spacer1, address);
        addressBox.setPadding(new Insets(10, 0, 0, 0));

        Label nameText = new Label("Name :");
        HBox nameTextBox = new HBox(nameText);
        nameTextBox.setPadding(new Insets(10, 275,0,275));

        Pane spacer2 = new Pane();
        spacer2.setPrefSize(225, 20);
        TextField name = new TextField(building.getName());
        name.setPrefSize(150, 20);
        HBox nameBox = new HBox(spacer2, name);
        nameBox.setPadding(new Insets(10, 0, 0, 0));

        Label buildingCodeText = new Label("Building Code :");
        HBox buildingCodeTextBox = new HBox(buildingCodeText);
        buildingCodeTextBox.setPadding(new Insets(10, 250,0,250));

        Pane spacer3 = new Pane();
        spacer3.setPrefSize(225, 20);
        TextField buildingCode = new TextField(Integer.toString(building.getCode()));
        buildingCode.setEditable(false);
        buildingCode.setPrefSize(150, 20);
        HBox buildingCodeBox = new HBox(spacer3, buildingCode);
        buildingCodeBox.setPadding(new Insets(10, 0, 0, 0));

        Label openingHoursText = new Label("Opening hours :");
        HBox openingHoursTextBox = new HBox(openingHoursText);
        openingHoursTextBox.setPadding(new Insets(10, 250,0,250));

        Label openClosedText = new Label("Open/Closed");
        openClosedText.setPrefSize(75, 20);
        Label fromText = new Label("From:");
        fromText.setPrefSize(75, 20);
        Label toText = new Label("To:");
        toText.setPrefSize(75,20);
        Pane spacer6 = new Pane();
        spacer6.setPrefSize(137.5, 20);
        Pane spacer7 = new Pane();
        spacer7.setPrefSize(50, 20);
        Pane spacer12 = new Pane();
        spacer12.setPrefSize(50, 20);
        HBox fromToBox = new HBox(spacer6, openClosedText, spacer12, fromText, spacer7, toText);
        fromToBox.setPadding(new Insets(10, 0, 0, 0));

        Pane spacer4 = new Pane();
        spacer4.setPrefSize(137.5, 20);
        Pane spacer5 = new Pane();
        spacer5.setPrefSize(50, 20);
        Pane spacer11 = new Pane();
        spacer11.setPrefSize(50, 20);
        ChoiceBox<String> options = new ChoiceBox<>();
        options.setPrefSize(75, 20);
        options.getItems().addAll("Open", Weekdays.getClosed());
        options.setValue("Open");
        ChoiceBox<String> from = new ChoiceBox<>();
        from.setPrefSize(75, 20);
        ChoiceBox<String> to = new ChoiceBox<>();
        to.setPrefSize(75, 20);

        //The calendar updates when you click on another day with this
        WeekWidget week = new WeekWidget(new Weekdays(building.getOpeningHours()));
        List<Node> times = week.getTimes().getChildren();
        for (int i = 0; i < times.size(); i++) {
            times.get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    StackPane source = (StackPane) event.getSource();
                    int current = Integer.parseInt(source.getId());
                    week.setCurrent(current);
                    week.redraw();

                    if (options.getValue().equals(Weekdays.getClosed())) {
                        week.dayChanged(week.getCurrent(), Weekdays.getClosed());
                        return;
                    }

                    //making the openinghours string
                    String fromText = from.getValue();
                    String toText = to.getValue();
                    if (fromText == null || toText == null) {
                        return;
                    }
                    String openingHours = fromText + "-" + toText;
                    week.dayChanged(week.getCurrent(), openingHours);
                }
            });
        }

        //The calendar updates the current clicked one with new opening hours
        EventHandler<ActionEvent> fromToEventHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (options.getValue().equals(Weekdays.getClosed())) {
                    week.dayChanged(week.getCurrent(), Weekdays.getClosed());
                    return;
                }

                //making the openinghours string
                String fromText = from.getValue();
                String toText = to.getValue();
                if (fromText == null || toText == null) {
                    return;
                }
                String openingHours = fromText + "-" + toText;
                week.dayChanged(week.getCurrent(), openingHours);
            }
        };
        from.setOnAction(fromToEventHandler);
        to.setOnAction(fromToEventHandler);
        loadOpeningHoursChoices(to, from);

        //The open/closed dropdown changes the current and shows/hides the to and from ChoiceBoxes
        options.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ChoiceBox<String> source = (ChoiceBox<String>) event.getSource();
                String status = source.getValue();
                if (status.equals(Weekdays.getClosed())) {
                    fromText.setVisible(false);
                    toText.setVisible(false);
                    from.setVisible(false);
                    to.setVisible(false);
                    week.dayChanged(week.getCurrent(), Weekdays.getClosed());
                }
                if (status.equals("Open")) {
                    fromText.setVisible(true);
                    toText.setVisible(true);
                    from.setVisible(true);
                    to.setVisible(true);
                    if (from.getValue() != null || to.getValue() != null) {
                        week.dayChanged(week.getCurrent(), from.getValue() + "-" + to.getValue());
                    }
                }
            }
        });
        HBox openingHours = new HBox(spacer4, options, spacer11, from, spacer5, to);
        openingHours.setPadding(new Insets(10, 0,10, 0));

        HBox calender = new HBox();
        Pane calenderSpacer = new Pane();
        calenderSpacer.setPrefSize(20, 140);
        calender.getChildren().addAll(calenderSpacer, week);

        //HBox for the text saying "Bike station:"
        Pane spacer8 = new Pane();
        spacer8.setPrefSize(260, 20);
        Label bikeStationText = new Label("Bike station:");
        bikeStationText.setPrefSize(75, 20);

        //HBox for the CheckBox and the TextField
        Pane spacer9 = new Pane();
        spacer9.setPrefSize(200, 20);
        CheckBox hasBikeStationCB = new CheckBox("has bike station");
        TextField bikesAmountInput = new TextField();
        bikesAmountInput.setPromptText("Amount");
        bikesAmountInput.setMaxWidth(60);
        if (building.getBikes() != null) {
            hasBikeStationCB.setSelected(true);
        }
        bikesAmountInput.setVisible(hasBikeStationCB.isSelected());
        if (bikesAmountInput.isVisible()) {
            bikesAmountInput.setText(Integer.toString(building.getBikes()));
        }
        Pane spacer10 = new Pane();
        spacer10.setPrefSize(20, 20);
        hasBikeStationCB.setOnAction((value) -> {
            if (hasBikeStationCB.isSelected()) {
                bikesAmountInput.setVisible(true);
            } else {
                bikesAmountInput.setVisible(false);
            }
        });
        HBox bikeStationInput = new HBox(spacer9, hasBikeStationCB, spacer10, bikesAmountInput);
        bikeStationInput.setPadding(new Insets(10, 0, 0, 0));

        Button submit = new Button("submit");
        submit.setPrefSize(100, 20);
        HBox submitBox = new HBox(submit);
        submitBox.setPadding(new Insets(10, 250,10, 250));
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String bikes = bikesAmountInput.getText();
                if (!hasBikeStationCB.isSelected()) {
                    bikes = null;
                }
                Label status = modifyBuilding(address.getText(), name.getText(),
                        building.getCode(), week.getWeekDays().toString(),
                        bikes, week.getWeekDays().checkCorrectness());
                if (status == null) {
                    Button button = (Button) event.getSource();
                    Stage stage = (Stage) button.getScene().getWindow();
                    stage.close();
                    loadBuildings();
                } else {
                    try {
                        root.getChildren().remove(14);
                        root.getChildren().add(status);
                    } catch (IndexOutOfBoundsException e) {
                        root.getChildren().add(status);
                    }
                }

            }
        });
        HBox bikeStationTextBox = new HBox(spacer8, bikeStationText);
        root.getChildren().addAll(headerBox, addressTextBox, addressBox, nameTextBox,
                nameBox, buildingCodeTextBox, buildingCodeBox, openingHoursTextBox,
                fromToBox, openingHours, calender, bikeStationTextBox, bikeStationInput,
                submitBox);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(mainBox.getScene().getWindow());
        stage.showAndWait();
    }

    private Label modifyBuilding(String address, String name, int buildingCode,
                                 String openingHours, String bikes, boolean openingHoursCorrect) {
        Label result = null;
        Integer bikesInt = null;
        // checking if bikes input is actually valid
        if (bikes == null) {
            bikesInt = null;
        } else {
            try {
                bikesInt = Integer.parseInt(bikes.trim());
                if (bikesInt < 0) {
                    result = new Label("The amount of bikes cannot be negative");
                }
            } catch (NumberFormatException e) {
                result = new Label("The amount of bikes is not a number");
            }
        }

        if (!openingHoursCorrect) {
            result = new Label("These opening hours don't make sense!");
        }

        if (!address.equals("") && !name.equals("") && openingHoursCorrect) {
            BuildingCommunication.addBuildingToDatabase(buildingCode,
                    name, address, openingHours, bikesInt);
        } else {
            if (result == null) {
                result = new Label("All the fields have to be entered");
            }
        }
        if (result != null) {
            result.setPadding(new Insets(10, 175, 0,175));
        }
        return result;
    }

    @FXML
    private void hasBikeStation() {
        if (hasBikeStationCheck.isSelected()) {
            bikeAmountInput.setVisible(true);
        } else {
            bikeAmountInput.setVisible(false);
        }
    }
}

