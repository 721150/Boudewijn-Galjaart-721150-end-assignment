package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.controllers.LoginController;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.data.Database;

import java.io.*;

public class StartApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Maak een nieuwe database aan, haal de gegevens op uit het opgegeven bestand
        Database database = new Database();
        database.loadDatabase("database.dat");

        // Open het inlogscherm
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        LoginController loginController = fxmlLoader.getController();
        loginController.giveData(database);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();

        // Laat de data opslaan in een bestand bij het sluiten van de applicatie
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            database.saveDatabase("database.dat");
        }));
    }

    public static void main(String[] args) {
        launch();
    }
}