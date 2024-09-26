module nl.inholland.javafundamentals.boudewijngaljaart721150endassignment {
    requires javafx.controls;
    requires javafx.fxml;


    opens nl.inholland.javafundamentals.boudewijngaljaart721150endassignment to javafx.fxml;
    exports nl.inholland.javafundamentals.boudewijngaljaart721150endassignment;
    exports nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.controllers;
    opens nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.controllers to javafx.fxml;
}