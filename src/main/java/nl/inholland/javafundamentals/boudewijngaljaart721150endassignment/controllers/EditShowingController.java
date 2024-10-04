package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.StartApplication;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.data.Database;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.Show;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.User;

import java.io.IOException;
import java.time.LocalDate;
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
        LocalDate startDate = editShowingStartDateDatePicker.getValue();
        LocalDate endDate = editShowingEndDateDatePicker.getValue();
        String startTime = editShowingStartTimeTextFiels.getText();
        String endTime = editShowingEndTimeTextFiels.getText();

        // Voeg de datum en tijd samen in een LocalDateTime
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.parse(startTime, timeFormatter));
        LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.parse(endTime, timeFormatter));

        // Maak een nieuwe voorstelling aan en voeg deze toe aan de tijdelijke "database"
        Show editShow = new Show(startDateTime, endDateTime, title, this.show.getSeats());
        this.database.editShow(this. show, editShow);

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
