package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.StartApplication;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.data.Database;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.Show;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.User;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AddShowingController {
    @FXML
    private VBox mainScreenVBox;

    @FXML
    private DatePicker addShowingStartDateDatePicker;

    @FXML
    private TextField addShowingStartTimeTextFiels;

    @FXML
    private DatePicker addShowingEndDateDatePicker;

    @FXML
    private TextField addShowingEndTimeTextFiels;

    @FXML
    private TextField addShowingTitleTextFiels;

    @FXML
    private Label invalitDataMessage;

    private User user;

    private Database database;

    public void giveData(User user, Database database) {
        this.user = user;
        this.database = database;
    }

    @FXML
    protected void addButtonClick(ActionEvent event) throws IOException {
        // Haal de gegevens op uit het scherm
        String title = addShowingTitleTextFiels.getText();
        LocalDateTime startDateTime;
        LocalDateTime endDateTime;
        LocalTime startTime;
        LocalTime endTime;
        try {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            startTime = LocalTime.parse(addShowingStartTimeTextFiels.getText(), timeFormatter);
            endTime = LocalTime.parse(addShowingEndTimeTextFiels.getText(), timeFormatter);
        } catch (Exception exception) {
            // Toon een foutmelding dat een verkeert format voor de datum wordt gebruikt
            invalitDataMessage.setVisible(true);
            invalitDataMessage.setText("Incorrect format time, use HH:MM. Such as 13:15.");
            return;
        }

        // Voeg de datum en tijd samen in een LocalDateTime
        try {
            startDateTime = LocalDateTime.of(addShowingStartDateDatePicker.getValue(), startTime);
            endDateTime = LocalDateTime.of(addShowingEndDateDatePicker.getValue(), endTime);
        } catch (Exception exception) {
            invalitDataMessage.setVisible(true);
            invalitDataMessage.setText("Incorrect format date, use DD-MM-YYYY. Such as 12-04-2024.");
            return;
        }

        // Maak een nieuwe voorstelling aan en voeg deze toe aan de tijdelijke "database"
        Show newShow = new Show(startDateTime, endDateTime, title);
        this.database.addShow(newShow);

        // Open het overzicht om de voorstellingen te beheren
        openManageShowingsScreen();
    }

    @FXML
    protected void cancelButtonClick(ActionEvent event) throws IOException {
        // Open het overzicht om de voorstellingen te beheren
        openManageShowingsScreen();
    }

    private void openManageShowingsScreen() throws IOException {
        // Toon het scherm in de VBox
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("manage-showings-view.fxml"));
        VBox vBox = fxmlLoader.load();
        mainScreenVBox.getChildren().setAll(vBox);
        ManageShowingsController manageShowingsController = fxmlLoader.getController();
        manageShowingsController.giveData(this.user, this.database);
    }

    @FXML
    protected void addStartDatumDatumPickerClick(ActionEvent event) throws IOException {
        addShowingEndDateDatePicker.setValue(addShowingStartDateDatePicker.getValue());
    }
}
