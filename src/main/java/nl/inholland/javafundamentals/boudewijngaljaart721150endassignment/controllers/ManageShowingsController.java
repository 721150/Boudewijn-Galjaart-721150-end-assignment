package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.StartApplication;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.controllers.enums.Screen;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.data.Database;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.Show;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManageShowingsController implements Initializable {
    @FXML
    private VBox mainScreenVBox;

    @FXML
    private TableView showingsTableInformationTebleView;

    @FXML
    private Button editShowingButton;

    @FXML
    private Button deleteShowingButton;

    @FXML
    private Label deleteFailMessage;

    private Database database;

    private ObservableList<Show> shows = FXCollections.observableArrayList();

    public void giveData(Database database) {
        this.database = database;
        this.shows.setAll(this.database.getShows());
    }

    @FXML
    protected void addShowingButtonClick(ActionEvent actionEvent) throws IOException {
        // Toon het scherm voor het toevoegen van een voorstelling in de VBox
        loadShowingsVBox(null, Screen.ADD);
    }

    @FXML
    protected void editShowingButtonClick(ActionEvent actionEvent) throws IOException {
        // Toon het scherm voor het bewerken van een voorstelling in de VBox
        loadShowingsVBox(getSelectedItem(), Screen.EDIT);
    }

    @FXML
    protected void deleteShowingButtonClick(ActionEvent actionEvent) throws IOException {
        // Verwijder een voorstellign uit de VBox en verzameling (alleen indien geen kaarten zijn verkocht)
        Show show = getSelectedItem();
        if (show.getNumberOfSeatsLeft() == show.getTotalNumberOfSeats()) {
            this.database.deleteShow(show);
            this.shows.remove(show);
            showSuccessPopup("Show successfully deleted");
        }
        else {
            // Toon het bericht dat een voorstelling niet kan worden verwijderd
            deleteFailMessage.setVisible(true);
        }
    }

    private void showSuccessPopup(String message) {
        // Toon een melding aan de gebruiker om te bevestigen dat de actie is geslaagd
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Action successful");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Show getSelectedItem() {
        // Haal de geselecteerde voorstelling op uit de tabel
        return (Show) showingsTableInformationTebleView.getSelectionModel().getSelectedItem();
    }

    private void loadShowingsVBox(Show show, Screen screen) throws IOException {
        // Toon het scherm in de VBox
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("add-showing-view.fxml"));
        VBox vBox = fxmlLoader.load();
        mainScreenVBox.getChildren().setAll(vBox);
        AddShowingController addShowingController = fxmlLoader.getController();
        addShowingController.giveData(this.database, show, screen);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Toon de voorstellingen in de tabel
        showingsTableInformationTebleView.setItems(this.shows);

        // Zorg ervoor dat de knoppen weizigen en verwijderen alleen werken als er een item is geselecteerd
        showingsTableInformationTebleView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                editShowingButton.setDisable(false);
                deleteShowingButton.setDisable(false);
            } else {
                editShowingButton.setDisable(true);
                deleteShowingButton.setDisable(true);
            }
        });
    }

}
