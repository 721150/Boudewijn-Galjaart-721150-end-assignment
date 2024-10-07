module nl.inholland.javafundamentals.boudewijngaljaart721150endassignment {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;


    opens nl.inholland.javafundamentals.boudewijngaljaart721150endassignment to javafx.fxml;
    opens nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models to javafx.base;
    exports nl.inholland.javafundamentals.boudewijngaljaart721150endassignment;
    exports nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.controllers;
    opens nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.controllers to javafx.fxml;
}