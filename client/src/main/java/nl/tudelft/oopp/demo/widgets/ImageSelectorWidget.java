package nl.tudelft.oopp.demo.widgets;

import javafx.stage.FileChooser;

import java.io.File;

public class ImageSelectorWidget {

    public static File getImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {

            System.out.println("File selected: " + selectedFile.getName());
        }
        else {
            System.out.println("File selection cancelled.");
        }

        return selectedFile;
    }
}
