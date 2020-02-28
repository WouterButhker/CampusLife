package nl.tudelft.oopp.demo.controllers;

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
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminSceneBuildingsController implements Initializable {

    @FXML
    private Button modifyBuildingsExit;
    
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
            loadOpeningHoursChoices(toChoicebox, fromChoicebox);
        }
        loadBuildings();
    }

    private void loadOpeningHoursChoices(ChoiceBox<String> to, ChoiceBox<String> from) {
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
            String[] buildings = BuildingCommunication.getBuildingsCodeAndName();
            anchorPaneBuildings.setPrefHeight(61 * numBuildings);
            //One HBox is 60 x 400
            //The entire AnchorPane holding all the HBoxes is 60*numBuildings x 400
            for (int i = 0; i < numBuildings; i++) {
                HBox building = new HBox();
                building.setMaxWidth(400);
                Image image = new Image("images/TuDelftTempIMG.jpg");
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(65);
                imageView.setFitHeight(40);
                Label text = new Label(buildings[i]);
                text.setPrefSize(225, 40);

                Button modify = new Button("modify");
                modify.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        createModifyPopup();
                    }
                });
                modify.setPrefSize(45, 40);
                modify.setPadding(new Insets(0, 0, 0 ,0));

                Button delete = new Button("delete");
                delete.setPrefSize(45, 40);
                String[] split = buildings[i].split(" ");
                delete.setId(split[0]);
                delete.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Button button = (Button) event.getSource();
                        BuildingCommunication.deleteBuildingFromDatabase(Integer.parseInt(button.getId()));
                        loadBuildings();
                    }
                });
                delete.setPadding(new Insets(0, 0 , 0 ,0));

                building.setPadding(new Insets(5, 5, 5,5));
                String css = "-fx-border-color: black;\n"
                        + "-fx-border-insets: 4\n;"
                        + "-fx-border-style: solid\n;"
                        + "-fx-border-width: 1;"
                        + "-fx-border-radius: 10;";
                building.setStyle(css);
                building.getChildren().addAll(imageView, text, modify, delete);
                buildingsList.getChildren().add(building);
            }
        }
    }

    @FXML
    private void modifyBuildingsExit() throws IOException {
        Stage stage = (Stage) modifyBuildingsExit.getScene().getWindow();
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/AdminScene.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void refreshBuildingsPage() throws IOException {
        Stage stage = (Stage) modifyBuildingsExit.getScene().getWindow();
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/AdminSceneBuildings.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
        if (!location.equals("") && !name.equals("") && codeFound && (fromChoicebox.getValue() != null && toChoicebox.getValue() != null)
                && fromChoicebox.getValue().compareTo(toChoicebox.getValue()) < 0) {
            BuildingCommunication.addBuildingToDatabase(buildingCode, name, location, openingHours);
            submitStatus.setText("Building successfully added!");
            try {
                refreshBuildingsPage();
            } catch (IOException e) {
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
        stage.initOwner(modifyBuildingsExit.getScene().getWindow());
        stage.showAndWait();
    }

    private void createModifyPopup() {
        VBox root = new VBox();
        root.setPrefSize(400, 500);

        Text header = new Text("Modify your building");
        header.setFont(Font.font("System", 24));
        HBox headerBox = new HBox(header);
        headerBox.setPadding(new Insets(20, 100 ,10 ,100));

        Label addressText = new Label("Address :");
        HBox addressTextBox = new HBox(addressText);
        addressText.setPadding(new Insets(20, 175 ,0 ,175));

        Pane spacer1 = new Pane();
        spacer1.setPrefSize(125, 20);
        TextField address = new TextField("address");
        address.setPrefSize(150,20);
        HBox addressBox = new HBox(spacer1, address);
        addressBox.setPadding(new Insets(10, 0, 0, 0));

        Label nameText = new Label("Name :");
        HBox nameTextBox = new HBox(nameText);
        nameTextBox.setPadding(new Insets(10, 175 ,0 ,175));

        Pane spacer2 = new Pane();
        spacer2.setPrefSize(125, 20);
        TextField name = new TextField("name");
        name.setPrefSize(150, 20);
        HBox nameBox = new HBox(spacer2, name);
        nameBox.setPadding(new Insets(10, 0, 0, 0));

        Label buildingCodeText = new Label("Building Code :");
        HBox buildingCodeTextBox = new HBox(buildingCodeText);
        buildingCodeTextBox.setPadding(new Insets(10, 150 ,0 ,150));

        Pane spacer3 = new Pane();
        spacer3.setPrefSize(125, 20);
        TextField buildingCode = new TextField("buildingCode");
        buildingCode.setPrefSize(150, 20);
        HBox buildingCodeBox = new HBox(spacer3, buildingCode);
        buildingCodeBox.setPadding(new Insets(10, 0, 0, 0));

        Label openingHoursText = new Label("Opening hours :");
        HBox openingHoursTextBox = new HBox(openingHoursText);
        openingHoursTextBox.setPadding(new Insets(10, 150 ,0 ,150));

        Label fromText = new Label("From:");
        fromText.setPrefSize(75, 20);
        Label toText = new Label("To:");
        toText.setPrefSize(75,20);
        Pane spacer6 = new Pane();
        spacer6.setPrefSize(100, 20);
        Pane spacer7 = new Pane();
        spacer7.setPrefSize(50, 20);
        HBox fromToBox = new HBox(spacer6, fromText, spacer7, toText);
        fromToBox.setPadding(new Insets(10, 0, 00, 0));

        Pane spacer4 = new Pane();
        spacer4.setPrefSize(100, 20);
        Pane spacer5 = new Pane();
        spacer5.setPrefSize(50, 20);
        ChoiceBox<String> from = new ChoiceBox<>();
        from.setPrefSize(75, 20);
        ChoiceBox<String> to = new ChoiceBox<>();
        to.setPrefSize(75, 20);
        loadOpeningHoursChoices(to, from);
        HBox openingHours = new HBox(spacer4, from, spacer5, to);
        openingHours.setPadding(new Insets(10, 0 ,10 , 0));

        Button submit = new Button("submit");
        submit.setPrefSize(100, 20);
        HBox submitBox = new HBox(submit);
        submitBox.setPadding(new Insets(10, 150,10, 150));
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        root.getChildren().addAll(headerBox, addressTextBox, addressBox, nameTextBox, nameBox, buildingCodeTextBox, buildingCodeBox, openingHoursTextBox, fromToBox, openingHours, submitBox);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(modifyBuildingsExit.getScene().getWindow());
        stage.showAndWait();
    }


}

