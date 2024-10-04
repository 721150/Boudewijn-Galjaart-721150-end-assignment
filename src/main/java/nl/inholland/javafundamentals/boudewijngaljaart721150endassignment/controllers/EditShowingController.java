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

public class EditShowingController {
    @FXML
    private VBox mainScreenVBox;

    @FXML
    private DatePicker editShowingStartDateDatePicker;

    @FXML
    private TextField editShowingStartTimeTextFiels;

    @FXML
    private DatePicker editShowingEndDateDatePicker;

    @FXML
    private TextField editShowingEndTimeTextFiels;

    @FXML
    private TextField editShowingTitleTextFiels;

    @FXML
    private Label invalitDataMessage;

    private User user;

    private Database database;

    private Show show;

    public void giveData(User user, Database database, Show show) {
        this.user = user;
        this.database = database;
        this.show = show;
        loadShow();
    }

    @FXML
    protected void editButtonClick(ActionEvent event) throws IOException { // aanpassen
        // Haal de gegevens op uit het scherm
        String title = editShowingTitleTextFiels.getText();
        LocalDateTime startDateTime;
        LocalDateTime endDateTime;
        LocalTime startTime;
        LocalTime endTime;
        try {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            startTime = LocalTime.parse(editShowingStartTimeTextFiels.getText(), timeFormatter);
            endTime = LocalTime.parse(editShowingEndTimeTextFiels.getText(), timeFormatter);
        } catch (Exception exception) {
            // Toon een foutmelding dat een verkeert format voor de datum wordt gebruikt
            invalitDataMessage.setVisible(true);
            invalitDataMessage.setText("Incorrect format time, use HH:MM. Such as 13:15.");
            return;
        }

        // Voeg de datum en tijd samen in een LocalDateTime
        try {
            startDateTime = LocalDateTime.of(editShowingStartDateDatePicker.getValue(), startTime);
            endDateTime = LocalDateTime.of(editShowingEndDateDatePicker.getValue(), endTime);
        } catch (Exception exception) {
            invalitDataMessage.setVisible(true);
            invalitDataMessage.setText("Incorrect format date, use DD-MM-YYYY. Such as 12-04-2024.");
            return;
        }

        // Maak een nieuwe voorstelling aan en voeg deze toe aan de tijdelijke "database"
        Show editShow = new Show(startDateTime, endDateTime, title, this.show.getSeats());
        this.database.editShow(this.show, editShow);

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

    private void loadShow() {
        // Toon de gegevens van de voorstelling op het scherm
        editShowingTitleTextFiels.setText(show.getTitle());
        editShowingStartTimeTextFiels.setText(show.getStartTime());
        editShowingEndTimeTextFiels.setText(show.getEndTime());
        editShowingStartDateDatePicker.setValue(show.getStartDate());
        editShowingEndDateDatePicker.setValue(show.getEndDate());
    }
}
