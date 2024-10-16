package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.controllers.enums.Screen;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.controllers.interfaces.Controller;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.data.Database;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.Show;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManageShowingsController implements Initializable, Controller {
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
        loadAddVBox(null, Screen.ADD);
    }

    @FXML
    protected void editShowingButtonClick(ActionEvent actionEvent) throws IOException {
        // Toon het scherm voor het bewerken van een voorstelling in de VBox
        loadAddVBox(getSelectedItem(), Screen.EDIT);
    }

    private void loadAddVBox(Show SelectedItem, Screen edit) throws IOException {
        FXMLLoader fxmlLoader = loadShowingsVBox(mainScreenVBox, "add-showing-view.fxml");
        AddShowingController addShowingController = fxmlLoader.getController();
        addShowingController.giveData(this.database, SelectedItem, edit);
    }

    @FXML
    protected void deleteShowingButtonClick(ActionEvent actionEvent) throws IOException {
        // Verwijder een voorstellign uit de VBox en verzameling (alleen indien geen kaarten zijn verkocht)
        Show show = getSelectedItem();
        if (show.getNumberOfSeatsLeft() == show.getTotalNumberOfSeats()) {
            this.database.deleteShow(show);
            this.shows.remove(show);
            showSuccessPopup("Show \"" + show.getTitle() + "\" successfully deleted");
        }
        else {
            // Toon het bericht dat een voorstelling niet kan worden verwijderd
            deleteFailMessage.setVisible(true);
        }
    }

    private Show getSelectedItem() {
        // Haal de geselecteerde voorstelling op uit de tabel
        return (Show) showingsTableInformationTebleView.getSelectionModel().getSelectedItem();
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
