package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.StartApplication;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.controllers.enums.Screen;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.data.Database;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.Show;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.User;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

public class AddShowingController implements Initializable {
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
        if (mode.equals(Screen.EDIT)) {
            loadShow();
            loadEditScreen();
        }
        loadScreen();
    }

    private void loadScreen() {
        // Stel het titelveld in als het geselecteerde veld
        titleTextField.requestFocus();
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
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        if (checkValitInput(title)) {
            return;
        }

        // Zet de data van het datumveld en tijdveld om naar een LocalDateTime en controleer of dit in het goede format is
        try {
            startDateTime = LocalDateTime.of(startDateDatePicker.getValue(), LocalTime.parse(startTimeTextField.getText(), timeFormatter));
            endDateTime = LocalDateTime.of(endDateDatePicker.getValue(), LocalTime.parse(endTimeTextField.getText(), timeFormatter));
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
        if (mode.equals(Screen.ADD)) {
            Show newShow = new Show(startDateTime, endDateTime, title);
            this.database.addShow(newShow);
        }
        else if (mode.equals(Screen.EDIT)) {
            Show editShow = new Show(startDateTime, endDateTime, title, this.show.getSeats());
            this.database.editShow(this.show, editShow);
        }
    }

    private boolean checkValitInput(String title) {
        // Controleer dat er een titel is opgegeven, zo niet geef een foutmelding
        if (title.isEmpty()) {
            displayErrorMessage("No title entered, enter field title. Such as To Sir, with Love");
            return true;
        }
        return false;
    }

    private void openManageShowingsScreen() throws IOException {
        // Open het scherm voor het beheren van de voorstellingen in de VBox
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("manage-showings-view.fxml"));
        VBox vBox = fxmlLoader.load();
        mainScreenVBox.getChildren().setAll(vBox);
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
        addShowingTitleLabel.setText("Edit showing");
        addShowingsButton.setText("Edit showing");
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

        // Voeg een foutafhandeling toe voor wanneer een foute tijd wordt ingevoerd
        startTimeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            validTimeTextField(startTimeTextField);
            if (startTimeTextField.getText() != null && this.mode.equals(Screen.ADD)) {
                try {
                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                    LocalTime startTime = LocalTime.parse(startTimeTextField.getText(), timeFormatter);
                    LocalTime endTime = startTime.plusMinutes(DURATION_SHOW);
                    endTimeTextField.setText(endTime.format(timeFormatter));
                } catch (DateTimeParseException e) {}
            }
        });
        endTimeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            validTimeTextField(endTimeTextField);
        });
    }

    private void validDateDatePicker(DatePicker datePicker) {
        // Geef een melding weer op het moment dat een verkeert format van de datum is ingevoerd
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            formatter.parse(datePicker.getEditor().getText());
            invalidDataMessage.setVisible(false);
            datePicker.setStyle("");
        } catch (DateTimeParseException e) {
            displayErrorMessage("Incorrect format date, use DD-MM-YYYY. Such as 12-04-2024.");
            datePicker.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        }
    }

    private void validTimeTextField(TextField textField) {
        // Geeft een melding weer op het moment dat een verkeert format van de datum is ingevoerd
        try {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime.parse(textField.getText(), timeFormatter);
            invalidDataMessage.setVisible(false);
            textField.setStyle("");
        } catch (Exception exception) {
            displayErrorMessage("Incorrect format time, use HH:MM. Such as 13:15.");
            textField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        }
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