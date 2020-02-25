package nl.tudelft.oopp.demo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.communication.ServerCommunication;

public class AdminSceneController implements Initializable {

    @FXML
    private ChoiceBox<String> bikeBuildings;

    @FXML
    private Text numberBikes;

    @FXML
    private Button modifyBuildingsEnter;

    @FXML
    private Button modifyBuildingsExit;

    @FXML
    private Button modifyRoomsEnter;

    @FXML
    private Button modifyRoomsExit;

    @FXML
    private Button modifyFoodEnter;

    @FXML
    private Button modifyFoodExit;

    @FXML
    private Button modifyRightsEnter;

    @FXML
    private Button modifyRightsExit;

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
    private VBox vBoxBuildings;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDataBikes();
        loadOpeningHoursChoices();
        loadBuildings();
    }


    private void loadDataBikes() {
        if (bikeBuildings != null) {
            bikeBuildings.getItems().addAll(BuildingCommunication.getBuildingsCodeAndName());
            SingleSelectionModel<String> selectionModel = bikeBuildings.getSelectionModel();
            selectionModel.selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable,
                                    String oldValue,
                                    String newValue) {
                    updateNumberBikes();
                }
            });
        }
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
        if (anchorPaneBuildings != null && vBoxBuildings != null) {
            int numBuildings = 10;
            String[] buildings = BuildingCommunication.getBuildingsCodeAndName();
            anchorPaneBuildings.setPrefHeight(50 * numBuildings);
            //One HBox is 50 x 300
            //The entire AnchorPane holding all the HBoxes is 50*numBuildings x 300
            for (int i = 0; i < numBuildings; i++) {
                HBox hBox = new HBox();
                hBox.setMaxWidth(300);
                Image image = new Image("images/TuDelftTempIMG.jpg");
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(65);
                imageView.setFitHeight(40);
                Label text = new Label(buildings[i]);
                text.setPrefSize(125, 40);
                Button modify = new Button("modify");
                modify.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                    }
                });
                modify.setPrefSize(50, 40);
                Button delete = new Button("delete");
                delete.setPrefSize(50, 40);
                hBox.setPadding(new Insets(5, 5, 5,5));
                String css = "-fx-border-color: black;\n"
                        + "-fx-border-insets: 5\n;"
                        + "-fx-border-style: solid";
                hBox.setStyle(css);
                hBox.getChildren().addAll(imageView, text, modify, delete);
                vBoxBuildings.getChildren().add(hBox);
            }
        }
    }


    private int updateNumberBikes() {
        String building = bikeBuildings.getValue();
        // Relay to the backend what the building is and
        // retrieve a number of bikes and store it in int bikes
        int bikes = 5;
        numberBikes.setText(Integer.toString(bikes));
        // return bikes;
        return bikes;
    }

    /**
     * Add a bike to the database.
     */
    public void addBike() {
        if (bikeBuildings.getValue() != null) {
            int bikes = updateNumberBikes();
            bikes++;
            //send the new bikes to the backend
            updateNumberBikes();
        }
    }

    /**
     * Remove a bike from the database.
     */
    public void removeBike() {
        if (bikeBuildings.getValue() != null) {
            int bikes = updateNumberBikes();
            bikes--;
            //send the new bikes to the backend
            updateNumberBikes();
        }
    }

    @FXML
    private void modifyBuildingsEnter() throws IOException {
        Stage stage = (Stage) modifyBuildingsEnter.getScene().getWindow();
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/AdminSceneBuildings.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
    private void modifyRoomsEnter() throws IOException {
        Stage stage = (Stage) modifyRoomsEnter.getScene().getWindow();
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/AdminSceneRooms.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void modifyRoomsExit() throws IOException {
        Stage stage = (Stage) modifyRoomsExit.getScene().getWindow();
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/AdminScene.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void modifyFoodEnter() throws IOException {
        Stage stage = (Stage) modifyFoodEnter.getScene().getWindow();
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/AdminSceneFood.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void modifyFoodExit() throws IOException {
        Stage stage = (Stage) modifyFoodExit.getScene().getWindow();
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/AdminScene.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void modifyRightsEnter() throws IOException {
        Stage stage = (Stage) modifyRightsEnter.getScene().getWindow();
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/AdminSceneRights.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void modifyRightsExit() throws IOException {
        Stage stage = (Stage) modifyRightsExit.getScene().getWindow();
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/AdminScene.fxml"));
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
            e.printStackTrace();
        }
        String openingHours = fromChoicebox.getValue() + "-" + toChoicebox.getValue();
        Text submitStatus = new Text();
        if (location != null && name != null && codeFound && openingHours != null) {
            BuildingCommunication.addBuildingToDatabase(buildingCode, name, location, openingHours);
            submitStatus.setText("Building successfully added!");
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

        StackPane root = new StackPane(submitStatus);
        root.setPrefSize(300, 200);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(modifyBuildingsExit.getScene().getWindow());
        stage.showAndWait();
    }


}
