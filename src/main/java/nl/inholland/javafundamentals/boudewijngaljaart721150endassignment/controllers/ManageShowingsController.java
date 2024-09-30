package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.StartApplication;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.data.Database;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManageShowingsController implements Initializable {
    @FXML
    private VBox mainScreenVBox;

    @FXML
    private TableView showingsTableInformationTebleView;

    private User user;

    private Database database;

    private ObservableList<User> users; // datatype nog aanpassen

    public void giveData(User user, Database database) {
        this.user = user;
        this.database = database;
        this.users = FXCollections.observableArrayList(this.database.getUsers()); // datatype nog aanpassen
    }

    @FXML
    protected void addShowingButtonClick(ActionEvent actionEvent) throws IOException {
        // Toon het scherm voor het toevoegen van een voorstelling in de VBox
        FXMLLoader fxmlLoader = loadShowingsVBox("add-showing-view.fxml");
    }

    private FXMLLoader loadShowingsVBox(String name) throws IOException {
        // Toon het scherm in de VBox
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource(name));
        VBox vBox = fxmlLoader.load();
        mainScreenVBox.getChildren().setAll(vBox);
        return fxmlLoader;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showingsTableInformationTebleView.setItems(this.users); // datatype nog aanpassen
    }
}
