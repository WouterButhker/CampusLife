package nl.tudelft.oopp.demo.core;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class XmlRoute extends Route {
    Parent root;

    /**
     * Creates a new XmlRoute to display the .fxml file given as the xmlUrl.
     * @param xmlUrl the URL to the .fxml file
     * @throws IOException thrown when xmlUrl is an invalid URL
     */
    public XmlRoute(URL xmlUrl) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(xmlUrl);
        root = loader.load();
    }

    @Override
    public Parent getRootElement() {
        return root;
    }
}
