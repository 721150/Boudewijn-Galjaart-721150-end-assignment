package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.data.Database;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.Show;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.User;

import java.net.URL;
import java.util.ResourceBundle;

public class SellTicketsController implements Initializable {
    @FXML
    private TableView showingsTableInformationTebleView;

    @FXML
    private Button selectSeatsButton;

    private User user;

    private Database database;

    private ObservableList<Show> shows = FXCollections.observableArrayList();

    public void giveData(User user, Database database) {
        this.user = user;
        this.database = database;
        this.shows.setAll(this.database.getCurrentShows());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Toon de voorstellingen in de tabel
        showingsTableInformationTebleView.setItems(this.shows);

        // Zorg ervoor dat de knoppen weizigen en verwijderen alleen werken als er een item is geselecteerd
        showingsTableInformationTebleView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectSeatsButton.setDisable(false);
            } else {
                selectSeatsButton.setDisable(true);
            }
        });
    }
}
