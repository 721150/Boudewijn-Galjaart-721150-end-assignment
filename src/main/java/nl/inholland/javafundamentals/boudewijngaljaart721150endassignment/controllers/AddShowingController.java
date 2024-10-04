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
import java.util.List;

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
        LocalDate startDate = addShowingStartDateDatePicker.getValue();
        LocalDate endDate = addShowingEndDateDatePicker.getValue();
        String startTime = addShowingStartTimeTextFiels.getText();
        String endTime = addShowingEndTimeTextFiels.getText();

        // Voeg de datum en tijd samen in een LocalDateTime
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.parse(startTime, timeFormatter));
        LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.parse(endTime, timeFormatter));

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
