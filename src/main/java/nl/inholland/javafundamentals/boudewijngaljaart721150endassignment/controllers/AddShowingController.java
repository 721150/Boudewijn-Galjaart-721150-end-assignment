package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.StartApplication;

import java.io.IOException;

public class AddShowingController {
    @FXML
    private VBox mainScreenVBox;

    @FXML
    private DatePicker addShowingStartDateDatePicker;

    @FXML
    private TextField addShowingStartTimeTextFiels;

    @FXML
    protected void addButtonClick(ActionEvent event) {

    }

    @FXML
    protected void cancelButtonClick(ActionEvent event) throws IOException {
        // Toon het scherm in de VBox
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("manage-showings-view.fxml"));
        VBox vBox = fxmlLoader.load();
        mainScreenVBox.getChildren().setAll(vBox);
    }
}
