package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.controllers.enums.Screen;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.controllers.interfaces.Controller;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.data.Database;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.Show;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

public class AddShowingController implements Initializable, Controller {
    private final int DURATION_SHOW = 150;

    @FXML
    private VBox mainScreenVBox;

    @FXML
    private DatePicker startDateDatePicker;

    @FXML
    private TextField startTimeTextField;

    @FXML
    private DatePicker endDateDatePicker;

    @FXML
    private TextField endTimeTextField;

    @FXML
    private TextField titleTextField;

    @FXML
    private Label addShowingTitleLabel;

    @FXML
    private Label invalidDataMessage;

    @FXML
    private Button addShowingsButton;

    private Database database;

    private Show show;

    private Screen mode;

    public void giveData(Database database, Show show, Screen mode) {
        this.database = database;
        this.show = show;
        this.mode = mode;
        loadScreen();
    }

    private void loadScreen() {
        // Stel het titelveld in als het geselecteerde veld
        titleTextField.requestFocus();

        if (mode.equals(Screen.EDIT)) {
            loadShow();
            loadEditScreen();
        }
    }

    @FXML
    protected void addButtonClick(ActionEvent event) throws IOException {
        handleAddOrEdit();
    }

    @FXML
    protected void cancelButtonClick(ActionEvent event) throws IOException {
        openManageShowingsScreen();
    }

    private void handleAddOrEdit() throws IOException {
        String title = titleTextField.getText();
        LocalDateTime startDateTime; LocalDateTime endDateTime;

        if (checkValitInput(title)) {
            return;
        }

        // Zet de data van het datumveld en tijdveld om naar een LocalDateTime en controleer of dit in het goede format is
        try {
            startDateTime = LocalDateTime.of(startDateDatePicker.getValue(), LocalTime.parse(startTimeTextField.getText(), getTimeFormatter()));
            endDateTime = LocalDateTime.of(endDateDatePicker.getValue(), LocalTime.parse(endTimeTextField.getText(), getTimeFormatter()));
        } catch (DateTimeParseException e) {
            return;
        }

        if (timeAndDateOfPast(startDateTime) || timeAndDateOfPast(endDateTime)) {
            return;
        }

        if (endDateTime.isBefore(startDateTime)) {
            displayErrorMessage("End date and time cannot be before start date and time");
            return;
        }

        addShowToDatabase(startDateTime, endDateTime, title);
        openManageShowingsScreen();
    }

    private void displayErrorMessage(String message) {
        invalidDataMessage.setVisible(true);
        invalidDataMessage.setText(message);
    }

    private void addShowToDatabase(LocalDateTime startDateTime, LocalDateTime endDateTime, String title) {
        // Maak een nieuwe voorstelling aan of wijzig deze in de "database"
        if (this.mode.equals(Screen.ADD)) {
            Show newShow = new Show(startDateTime, endDateTime, title);
            this.database.addShow(newShow);
            showSuccessPopup("Show \"" + newShow.getTitle() +"\" successfully added");
        }
        else if (this.mode.equals(Screen.EDIT)) {
            Show editShow = new Show(startDateTime, endDateTime, title, this.show.getSeats());
            this.database.editShow(this.show, editShow);
            showSuccessPopup("Show \"" + editShow.getTitle() + "\" successfully changed");
        }
    }

    private boolean checkValitInput(String title) {
        // Controleer dat er een titel is opgegeven, zo niet geef een foutmelding
        if (title.isEmpty()) {
            displayErrorMessage("No title entered, enter field title. Such as To Sir, with Love");
            return true;
        }
        else if (startDateDatePicker.getValue() == null || startTimeTextField.getText().isEmpty() ||
                endDateDatePicker.getValue() == null || endTimeTextField.getText().isEmpty()) {
            displayErrorMessage("No date and/or time entered, enter field date / time. Such as 12-04-2024 / 13:15.");
            return true;
        }
        else if (!isValidDate(startDateDatePicker.getEditor().getText()) || !isValidDate(endDateDatePicker.getEditor().getText()) ||
                !isValidTime(startTimeTextField.getText()) || !isValidTime(endTimeTextField.getText())) {
            displayErrorMessage("Incorrect format date / time, use DD-MM-YYYY / HH:MM. Such as 12-04-2024 / 13:15.");
            return true;
        }
        return false;
    }

    private boolean isValidDate(String date) {
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean isValidTime(String time) {
        try {
            LocalTime.parse(time, getTimeFormatter());
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private void openManageShowingsScreen() throws IOException {
        // Open het scherm voor het beheren van de voorstellingen in de VBox
        FXMLLoader fxmlLoader = loadShowingsVBox(mainScreenVBox, "manage-showings-view.fxml");
        ManageShowingsController manageShowingsController = fxmlLoader.getController();
        manageShowingsController.giveData(this.database);
    }

    private void loadShow() {
        // Toon de gegevens van de voorstelling op het scherm
        titleTextField.setText(this.show.getTitle());
        startTimeTextField.setText(this.show.getStartTime());
        endTimeTextField.setText(this.show.getEndTime());
        startDateDatePicker.setValue(this.show.getStartDate());
        endDateDatePicker.setValue(this.show.getEndDate());
    }

    private void loadEditScreen() {
        // Steld de tekst in naar een bewerken scherm
        String editScreenTitle = "Edit showing";
        addShowingTitleLabel.setText(editScreenTitle);
        addShowingsButton.setText(editScreenTitle);
    }

    @FXML
    protected void addStartDatumDatumPickerClick(ActionEvent event) {
        // Stel het veld van de einddatum automatisch in op hetzelfde als de startdatum bij nieuwe voorstellingen
        if (this.mode.equals(Screen.ADD)) {
            endDateDatePicker.setValue(startDateDatePicker.getValue());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Voeg een foutafhandeling toe voor wanneer een foute datum wordt ingevoerd
        startDateDatePicker.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            validDateDatePicker(startDateDatePicker);
        });
        endDateDatePicker.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            validDateDatePicker(endDateDatePicker);
        });

        // Voeg een foutafhandeling toe voor wanneer een foute tijd wordt ingevoerd en stel de eindtijd van de voorstelling in
        startTimeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            validTimeTextField(startTimeTextField);
            calculateEndTime();
        });
        endTimeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            validTimeTextField(endTimeTextField);
        });
    }

    private void calculateEndTime() {
        // Bepaal de eindtijd van de voorstelling
        if (startTimeTextField.getText() != null && this.mode.equals(Screen.ADD)) {
            try {
                LocalTime startTime = LocalTime.parse(startTimeTextField.getText(), getTimeFormatter());
                LocalTime endTime = startTime.plusMinutes(DURATION_SHOW);
                if (startTime.isAfter(LocalTime.of(21, 29))) {
                    endTime = LocalTime.of(23,59);
                }
                endTimeTextField.setText(endTime.format(getTimeFormatter()));
            } catch (DateTimeParseException e) {}
        }
    }

    private void validDateDatePicker(DatePicker datePicker) {
        // Geef een melding weer op het moment dat een verkeert format van de datum is ingevoerd
        if (isValidDate(datePicker.getEditor().getText())) {
            invalidDataMessage.setVisible(false);
            datePicker.setStyle("");
        }
        else {
            displayErrorMessage("Incorrect format date, use DD-MM-YYYY. Such as 12-04-2024.");
            datePicker.setStyle(getStyle());
        }
    }

    private static String getStyle() {
        return "-fx-border-color: red; -fx-border-width: 2px;";
    }

    private void validTimeTextField(TextField textField) {
        // Geeft een melding weer op het moment dat een verkeert format van de datum is ingevoerd
        if (isValidTime(textField.getText())) {
            invalidDataMessage.setVisible(false);
            textField.setStyle("");
        }
        else {
            displayErrorMessage("Incorrect format time, use HH:MM. Such as 13:15.");
            textField.setStyle(getStyle());
        }
    }

    private DateTimeFormatter getTimeFormatter() {
        return DateTimeFormatter.ofPattern("HH:mm");
    }

    private boolean timeAndDateOfPast(LocalDateTime dateTime) {
        // Geef een foutmelding wanneer de datum/tijd in het verleden licht
        if (dateTime.isBefore(LocalDateTime.now())) {
            displayErrorMessage("Cannot set a date and time from the past");
            return true;
        }
        return false;
    }
}