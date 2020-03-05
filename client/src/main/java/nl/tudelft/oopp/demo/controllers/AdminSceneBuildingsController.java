package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import nl.tudelft.oopp.demo.widgets.AppBar;

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
    private AnchorPane anchorPaneBuildings;

    @FXML
    private VBox buildingsList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (toChoicebox != null && fromChoicebox != null) {
            loadOpeningHoursChoices(fromChoicebox, toChoicebox);
        }
        loadBuildings();
        addAppBar();
    }

    private void addAppBar() {
        mainBox.getChildren().add(0, new AppBar());
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

    private void loadBuildings() {
        if (anchorPaneBuildings != null && buildingsList != null) {
            buildingsList.getChildren().clear();
            int numBuildings = BuildingCommunication.countAllBuildings();
            List<Building> buildings = BuildingCommunication.getAllBuildings();
            anchorPaneBuildings.setPrefHeight(82 * numBuildings);
            //One HBox is 60 x 400
            //The entire AnchorPane holding all the HBoxes is 60*numBuildings x 400
            for (int i = 0; i < numBuildings; i++) {
                HBox building = new HBox();
                building.setMaxWidth(400);
                Image image = new Image("images/TuDelftTempIMG.jpg");
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(65);
                imageView.setFitHeight(60);
                Label text = new Label("Building Code: " + buildings.get(i).getCode()
                        + " | " + buildings.get(i).getName()
                        + "\nOpening Hours: " + buildings.get(i).getOpeningHours() + "\n"
                        + buildings.get(i).getLocation());
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
        String openingHours = fromChoicebox.getValue() + "-" + toChoicebox.getValue();
        Text submitStatus = new Text();
        if (!location.equals("") && !name.equals("") && codeFound
               && (fromChoicebox.getValue() != null && toChoicebox.getValue() != null)
               &&  fromChoicebox.getValue().compareTo(toChoicebox.getValue()) < 0) {
            BuildingCommunication.addBuildingToDatabase(buildingCode, name, location, openingHours);
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

        if ((fromChoicebox.getValue() != null && toChoicebox.getValue() != null)
                && fromChoicebox.getValue().compareTo(toChoicebox.getValue()) >= 0) {
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
        root.setPrefSize(400, 500);

        Text header = new Text("Modify your building");
        header.setFont(Font.font("System", 24));
        HBox headerBox = new HBox(header);
        headerBox.setPadding(new Insets(20, 100,10,100));

        Label addressText = new Label("Address :");
        addressText.setPadding(new Insets(20, 175,0,175));
        HBox addressTextBox = new HBox(addressText);
        addressTextBox.setPadding(new Insets(0));

        Pane spacer1 = new Pane();
        spacer1.setPrefSize(125, 20);
        TextField address = new TextField(building.getLocation());
        address.setPrefSize(150,20);
        HBox addressBox = new HBox(spacer1, address);
        addressBox.setPadding(new Insets(10, 0, 0, 0));

        Label nameText = new Label("Name :");
        HBox nameTextBox = new HBox(nameText);
        nameTextBox.setPadding(new Insets(10, 175,0,175));

        Pane spacer2 = new Pane();
        spacer2.setPrefSize(125, 20);
        TextField name = new TextField(building.getName());
        name.setPrefSize(150, 20);
        HBox nameBox = new HBox(spacer2, name);
        nameBox.setPadding(new Insets(10, 0, 0, 0));

        Label buildingCodeText = new Label("Building Code :");
        HBox buildingCodeTextBox = new HBox(buildingCodeText);
        buildingCodeTextBox.setPadding(new Insets(10, 150,0,150));

        Pane spacer3 = new Pane();
        spacer3.setPrefSize(125, 20);
        TextField buildingCode = new TextField(Integer.toString(building.getCode()));
        buildingCode.setEditable(false);
        buildingCode.setPrefSize(150, 20);
        HBox buildingCodeBox = new HBox(spacer3, buildingCode);
        buildingCodeBox.setPadding(new Insets(10, 0, 0, 0));

        Label openingHoursText = new Label("Opening hours :");
        HBox openingHoursTextBox = new HBox(openingHoursText);
        openingHoursTextBox.setPadding(new Insets(10, 150,0,150));

        Label fromText = new Label("From:");
        fromText.setPrefSize(75, 20);
        Label toText = new Label("To:");
        toText.setPrefSize(75,20);
        Pane spacer6 = new Pane();
        spacer6.setPrefSize(100, 20);
        Pane spacer7 = new Pane();
        spacer7.setPrefSize(50, 20);
        HBox fromToBox = new HBox(spacer6, fromText, spacer7, toText);
        fromToBox.setPadding(new Insets(10, 0, 0, 0));

        Pane spacer4 = new Pane();
        spacer4.setPrefSize(100, 20);
        Pane spacer5 = new Pane();
        spacer5.setPrefSize(50, 20);
        ChoiceBox<String> from = new ChoiceBox<>();
        from.setPrefSize(75, 20);
        ChoiceBox<String> to = new ChoiceBox<>();
        to.setPrefSize(75, 20);
        loadOpeningHoursChoices(to, from);
        String[] chosenHours = building.getOpeningHours().split("-");
        from.setValue(chosenHours[0]);
        to.setValue(chosenHours[1]);
        HBox openingHours = new HBox(spacer4, from, spacer5, to);
        openingHours.setPadding(new Insets(10, 0,10, 0));

        Button submit = new Button("submit");
        submit.setPrefSize(100, 20);
        HBox submitBox = new HBox(submit);
        submitBox.setPadding(new Insets(10, 150,10, 150));
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String openingHours = from.getValue() + "-" + to.getValue();
                Label status = modifyBuilding(address.getText(), name.getText(),
                        building.getCode(), openingHours);
                if (status == null) {
                    Button button = (Button) event.getSource();
                    Stage stage = (Stage) button.getScene().getWindow();
                    stage.close();
                } else {
                    try {
                        root.getChildren().remove(11);
                        root.getChildren().add(status);
                    } catch (IndexOutOfBoundsException e) {
                        root.getChildren().add(status);
                    }
                }

            }
        });
        root.getChildren().addAll(headerBox, addressTextBox, addressBox, nameTextBox,
                nameBox, buildingCodeTextBox, buildingCodeBox, openingHoursTextBox,
                fromToBox, openingHours, submitBox);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(mainBox.getScene().getWindow());
        stage.showAndWait();
    }

    private Label modifyBuilding(String address, String name,
                                 int buildingCode, String openingHours) {
        boolean openingHoursCorrect = false;
        String[] fromTo = openingHours.split("-");
        Label result = null;
        if (fromTo[0].compareTo(fromTo[1]) < 0) {
            openingHoursCorrect = true;
        } else {
            result = new Label("These opening hours don't make sense");
        }

        if (!address.equals("") && !name.equals("") && openingHoursCorrect) {
            BuildingCommunication.addBuildingToDatabase(buildingCode, name, address, openingHours);
        } else {
            if (result == null) {
                result = new Label("All the fields have to be entered");
            }
        }
        if (result != null) {
            result.setPadding(new Insets(10, 100, 0,100));
        }
        return result;
    }
}

