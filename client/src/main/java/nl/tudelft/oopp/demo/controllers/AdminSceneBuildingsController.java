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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
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
        loadOpeningHoursChoices();
        loadBuildings();
    }

    private void loadOpeningHoursChoices() {
        if (toChoicebox != null && fromChoicebox != null) {
            String[] times = new String[25];
            for (int i = 0; i < 25; i++) {
                if (i < 10) {
                    times[i] = "0" + i + ":00";
                } else {
                    times[i] = i + ":00";
                }
            }
            toChoicebox.getItems().addAll(times);
            fromChoicebox.getItems().addAll(times);
        }
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
            System.out.println("not a proper number");
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
                e.printStackTrace();
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
        submitStatus.setTextAlignment(TextAlignment.CENTER);
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

        Stage stage = new Stage();
        //stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(modifyBuildingsExit.getScene().getWindow());
        stage.showAndWait();
    }
}

