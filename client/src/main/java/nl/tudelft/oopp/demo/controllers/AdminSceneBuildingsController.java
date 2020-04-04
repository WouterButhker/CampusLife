package nl.tudelft.oopp.demo.controllers;

import static nl.tudelft.oopp.demo.communication.ImageCommunication.getBuildingImageUrl;

import java.io.File;
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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.communication.ImageCommunication;
import nl.tudelft.oopp.demo.core.Route;
import nl.tudelft.oopp.demo.core.RoutingScene;
import nl.tudelft.oopp.demo.core.XmlRoute;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Weekdays;
import nl.tudelft.oopp.demo.widgets.AppBar;
import nl.tudelft.oopp.demo.widgets.ImageSelectorWidget;
import nl.tudelft.oopp.demo.widgets.PopupWidget;
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
    private VBox buildingsBox;

    @FXML
    private CheckBox hasBikeStationCheck;

    @FXML
    private TextField bikeAmountInput;

    @FXML
    private VBox scrollPaneVBox;

    @FXML
    private VBox settingsBox;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Button submit;

    @FXML
    private Button refresh;

    private ImageSelectorWidget imageSelectorWidget;

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
        addImageSelectorWidget();
        addWeekCalendar(new Weekdays());
        addStyle();
    }

    private void addStyle() {
        mainBox.getStylesheets().add("css/admin-scene.css");
        mainBox.setStyle("-fx-background-color: -primary-color-light");
        submit.getStyleClass().add("adminButton");
        refresh.getStyleClass().add("adminButton");
        fromChoicebox.getStyleClass().add("choice-box");
        toChoicebox.getStyleClass().add("choice-box");
        open.getStyleClass().add("choice-box");
        options.getStyleClass().add("choice-box");
        nameInput.getStyleClass().add("text-field");
    }

    private void addAppBar() {
        mainBox.getChildren().add(0, new AppBar());
    }

    private void addImageSelectorWidget() {
        Pane spacerPane = new Pane();
        spacerPane.setPrefWidth(10);
        imageSelectorWidget = new ImageSelectorWidget();
        imageSelectorWidget.getChooseFileButton().getStyleClass().add("adminButtonSmall");
        HBox box = new HBox();
        box.getChildren().addAll(spacerPane, imageSelectorWidget);
        settingsBox.getChildren().add(7, box);
    }

    private void addWeekCalendar(Weekdays weekdays) {
        this.week = new WeekWidget(weekdays);
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().add(week);
        settingsBox.getChildren().add(4, box);
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
        String[] options = {"Open", Weekdays.CLOSED};
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
        if (open.getValue().equals(Weekdays.CLOSED)) {
            week.dayChanged(week.getCurrent(), Weekdays.CLOSED);
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
        if (buildingsBox != null) {
            buildingsBox.getChildren().clear();
            List<Building> buildings = BuildingCommunication.getAllBuildings();
            int numBuildings = buildings.size();
            int height = 82 * numBuildings;
            if (height <= scrollPane.getPrefHeight()) {
                scrollPaneVBox.setPrefWidth(650);
                scrollPane.setPrefWidth(650);
            } else {
                scrollPaneVBox.setPrefWidth(667);
                scrollPane.setPrefWidth(667);
            }
            //One HBox is 60 x scrollPaneViewPortWidth
            for (int i = 0; i < numBuildings; i++) {
                HBox building = new HBox();
                building.setPrefWidth(scrollPane.getPrefViewportWidth());
                building.setMaxWidth(scrollPane.getPrefViewportWidth());
                Image image = new Image(getBuildingImageUrl(buildings.get(i).getCode()));
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
                String openingHours = splitOpeningHours(buildings.get(i).getOpeningHours());
                Label text = new Label("Building Code: " + buildings.get(i).getCode()
                        + " | " + buildings.get(i).getName()
                        + " | " + buildings.get(i).getLocation()
                        + " | Bikes: " + bikesString
                        + "\nOpening Hours: " + openingHours);
                text.setPrefSize(scrollPane.getPrefViewportWidth() - 65 - 10 - 12
                        - 55 - 55 - 10, 60);
                text.setPadding(new Insets(0, 0, 0, 10));

                int finalI = i;
                Button modify = new Button("modify");
                modify.getStyleClass().add("adminButtonSmall");
                modify.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        createModifyPopup(buildings.get(finalI));
                    }
                });
                modify.setPrefSize(55, 40);
                modify.setPadding(new Insets(0, 0, 0,0));
                StackPane modifyPane = new StackPane(modify);
                modifyPane.setPadding(new Insets(10, 0, 10, 0));

                Button delete = new Button("delete");
                delete.getStyleClass().add("adminButtonSmall");
                delete.setPrefSize(55, 40);
                delete.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        boolean confirmation = PopupWidget.displayBool("Are you sure about "
                              + "deleting this?\nThe change will be irreversible.", "Confirmation");
                        if (confirmation) {
                            int code = buildings.get(finalI).getCode();
                            BuildingCommunication.deleteBuilding(code);
                            loadBuildings();
                        }
                    }
                });
                delete.setPadding(new Insets(0, 0,0,0));
                StackPane deletePane = new StackPane(delete);
                deletePane.setPadding(new Insets(10, 0, 10, 10));

                building.setPadding(new Insets(5, 5, 5,5));
                String css = "-fx-border-color: black;\n"
                        + "-fx-border-insets: 4\n;"
                        + "-fx-border-style: solid\n;"
                        + "-fx-border-width: 1;"
                        + "-fx-border-radius: 10;"
                        + "-fx-background-color: -primary-color";
                building.setStyle(css);
                building.getChildren().addAll(imageView, text, modifyPane, deletePane);
                buildingsBox.getChildren().add(building);
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
        String submitStatus;
        Integer bikes = null;

        if (hasBikeStationCheck.isSelected()) {
            try {
                bikes = Integer.parseInt(bikeAmountInput.getText());
            } catch (NumberFormatException e) {
                submitStatus = ("Invalid number");
            }
        }
        if (!location.equals("") && !name.equals("") && codeFound
               && week.getWeekDays().checkCorrectness()
               && imageSelectorWidget.imageSelected()) {
            Building building = new Building(buildingCode, name, location, openingHours, bikes);
            BuildingCommunication.saveBuilding(building);
            ImageCommunication.updateBuildingImage(buildingCode, imageSelectorWidget.getImage());
            submitStatus = ("Building successfully added!");
            try {
                refreshBuildingsPage();
            } catch (Exception e) {
                System.out.println("Refresh failed");
            }
        } else {
            submitStatus = ("The input is wrong or not all fields are entered");
        }

        if (!codeFound) {
            submitStatus = ("The building code has to be a number!");
        }

        if (!week.getWeekDays().checkCorrectness()) {
            submitStatus = ("These opening hours don't make sense");
        }

        if (!imageSelectorWidget.imageSelected()) {
            submitStatus = ("Image has to be selected");
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
        if (submitStatus.equals("Building successfully added!")) {
            PopupWidget.displaySuccess(submitStatus, "Success!");
            loadBuildings();
        } else {
            PopupWidget.displayError(submitStatus, "Error!");
        }
    }

    private void createModifyPopup(Building building) {
        VBox root = new VBox();
        root.setStyle("-fx-background-color: -primary-color");
        root.setPrefSize(600, 700);

        Text header = new Text("Modify your building");
        header.setFont(Font.font("System", 22));
        HBox headerBox = new HBox(header);
        headerBox.setPadding(new Insets(20, 0,10,0));
        headerBox.setAlignment(Pos.CENTER);

        Label addressText = new Label("Address :");
        addressText.setPadding(new Insets(20, 0,0,0));
        HBox addressTextBox = new HBox(addressText);
        addressTextBox.setAlignment(Pos.CENTER);

        TextField address = new TextField(building.getLocation());
        address.setPrefSize(150,20);
        HBox addressBox = new HBox(address);
        addressBox.setPadding(new Insets(10, 0, 0, 0));
        addressBox.setAlignment(Pos.CENTER);

        Label nameText = new Label("Name :");
        HBox nameTextBox = new HBox(nameText);
        nameTextBox.setPadding(new Insets(10, 0,0,0));
        nameTextBox.setAlignment(Pos.CENTER);

        TextField name = new TextField(building.getName());
        name.setPrefSize(150, 20);
        HBox nameBox = new HBox(name);
        nameBox.setPadding(new Insets(10, 0, 0, 0));
        nameBox.setAlignment(Pos.CENTER);

        Label buildingCodeText = new Label("Building Code :");
        HBox buildingCodeTextBox = new HBox(buildingCodeText);
        buildingCodeTextBox.setPadding(new Insets(10, 0, 0, 0));
        buildingCodeTextBox.setAlignment(Pos.CENTER);

        TextField buildingCode = new TextField(Integer.toString(building.getCode()));
        buildingCode.setEditable(false);
        buildingCode.setPrefSize(150, 20);
        HBox buildingCodeBox = new HBox(buildingCode);
        buildingCodeBox.setPadding(new Insets(10, 0, 0, 0));
        buildingCodeBox.setAlignment(Pos.CENTER);

        Label openClosedText = new Label("Open/Closed");
        openClosedText.setPrefSize(75, 20);
        Label fromText = new Label("From:");
        fromText.setPrefSize(75, 20);
        Label toText = new Label("To:");
        toText.setPrefSize(75,20);
        Pane spacer7 = new Pane();
        spacer7.setPrefSize(50, 20);
        Pane spacer12 = new Pane();
        spacer12.setPrefSize(50, 20);
        HBox fromToBox = new HBox(openClosedText, spacer12, fromText, spacer7, toText);
        fromToBox.setPadding(new Insets(10, 0, 0, 0));
        fromToBox.setAlignment(Pos.CENTER);

        Pane spacer5 = new Pane();
        spacer5.setPrefSize(50, 20);
        Pane spacer11 = new Pane();
        spacer11.setPrefSize(50, 20);
        ChoiceBox<String> options = new ChoiceBox<>();
        options.getStyleClass().add("choice-box");
        options.setPrefSize(75, 20);
        options.getItems().addAll("Open", Weekdays.CLOSED);
        options.setValue("Open");
        ChoiceBox<String> from = new ChoiceBox<>();
        from.getStyleClass().add("choice-box");
        from.setPrefSize(75, 20);
        ChoiceBox<String> to = new ChoiceBox<>();
        to.getStyleClass().add("choice-box");
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

                    if (options.getValue().equals(Weekdays.CLOSED)) {
                        week.dayChanged(week.getCurrent(), Weekdays.CLOSED);
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
                if (options.getValue().equals(Weekdays.CLOSED)) {
                    week.dayChanged(week.getCurrent(), Weekdays.CLOSED);
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
                if (status.equals(Weekdays.CLOSED)) {
                    fromText.setVisible(false);
                    toText.setVisible(false);
                    from.setVisible(false);
                    to.setVisible(false);
                    week.dayChanged(week.getCurrent(), Weekdays.CLOSED);
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
        HBox openingHours = new HBox(options, spacer11, from, spacer5, to);
        openingHours.setPadding(new Insets(10, 0,10, 0));
        openingHours.setAlignment(Pos.CENTER);

        HBox calender = new HBox(week);
        calender.setPadding(new Insets(10, 0, 10, 0));
        calender.setAlignment(Pos.CENTER);

        //HBox for the text saying "Bike station:"
        Label bikeStationText = new Label("Bike station:");
        bikeStationText.setPrefSize(75, 20);
        HBox bikeStationTextBox = new HBox(bikeStationText);
        bikeStationTextBox.setAlignment(Pos.CENTER);

        //HBox for the CheckBox and the TextField
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
        HBox bikeStationInput = new HBox(hasBikeStationCB, spacer10, bikesAmountInput);
        bikeStationInput.setPadding(new Insets(10, 0, 0, 0));
        bikeStationInput.setAlignment(Pos.CENTER);

        HBox imageSelectorWidgetBox = new HBox();
        imageSelectorWidgetBox.setAlignment(Pos.CENTER);
        ImageSelectorWidget imageSelectorWidget = new ImageSelectorWidget();
        imageSelectorWidget.getChooseFileButton().getStyleClass().add("adminButtonSmall");
        imageSelectorWidgetBox.getChildren().add(imageSelectorWidget);
        imageSelectorWidgetBox.setPadding(new Insets(10, 10, 0, 0));

        Button submit = new Button("submit");
        submit.getStyleClass().add("adminButtonSmall");
        submit.setPrefSize(100, 20);
        HBox submitBox = new HBox(submit);
        submitBox.setPadding(new Insets(10, 0,10, 0));
        submitBox.setAlignment(Pos.CENTER);
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String bikes = bikesAmountInput.getText();
                if (!hasBikeStationCB.isSelected()) {
                    bikes = null;
                }
                Node status = modifyBuilding(address.getText(), name.getText(),
                        building.getCode(), week.getWeekDays().toString(),
                        bikes, week.getWeekDays().checkCorrectness(),
                        imageSelectorWidget.getImage());
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

        root.getChildren().addAll(headerBox, addressTextBox, addressBox, nameTextBox,
                nameBox, buildingCodeTextBox, buildingCodeBox, fromToBox, openingHours,
                calender, bikeStationTextBox, bikeStationInput,
                imageSelectorWidgetBox, submitBox);
        Stage stage = new Stage();
        stage.setTitle("Modifying " + building.getName());
        stage.getIcons().add(new Image("images/modifyingImage.png"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("css/palette.css");
        scene.getStylesheets().add("css/admin-scene.css");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(mainBox.getScene().getWindow());
        stage.showAndWait();
    }

    private Node modifyBuilding(String location, String name, int buildingCode,
                                 String openingHours, String bikes, boolean openingHoursCorrect,
                                File image) {
        Label message = null;
        Integer bikesInt = null;
        // checking if bikes input is actually valid
        if (bikes == null) {
            bikesInt = null;
        } else {
            try {
                bikesInt = Integer.parseInt(bikes.trim());
                if (bikesInt < 0) {
                    message = new Label("The amount of bikes cannot be negative");
                }
            } catch (NumberFormatException e) {
                message = new Label("The amount of bikes is not a number");
            }
        }

        if (!openingHoursCorrect) {
            message = new Label("These opening hours don't make sense!");
        }

        if (!location.equals("") && !name.equals("") && openingHoursCorrect) {
            Building building = new Building(buildingCode, name, location, openingHours,
                     bikesInt);
            BuildingCommunication.updateBuilding(building);
            if (image != null) {
                ImageCommunication.updateBuildingImage(buildingCode, image);
            }

        } else {
            if (message == null) {
                message = new Label("All the fields have to be entered");
            }
        }
        HBox res = null;
        if (message != null) {
            message.setStyle("-fx-text-fill: red");
            res = new HBox();
            res.setAlignment(Pos.CENTER);
            res.getChildren().add(message);
        }
        return res;
    }

    private String splitOpeningHours(String openingHours) {
        String[] strings = openingHours.split(", ");
        String res = "";
        for (int i = 0; i < 4; i++) {
            res = res + strings[i] + ", ";
        }
        res = res + "\n";
        for (int i = 4; i < 7; i++) {
            res = res + strings[i];
            if (i != 6) {
                res = res + ", ";
            }
        }
        return res;
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

