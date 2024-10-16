package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.controllers.interfaces;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.StartApplication;

import java.io.IOException;

public interface Controller {
    default void showSuccessPopup(String message) {
        // Toon een melding aan de gebruiker om te bevestigen dat de actie is geslaagd
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Action successful");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    default FXMLLoader loadShowingsVBox(VBox mainScreenVBox, String name) throws IOException {
        // Toon het scherm in de VBox
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource(name));
        VBox vBox = fxmlLoader.load();
        mainScreenVBox.getChildren().setAll(vBox);
        return fxmlLoader;
    }
}
