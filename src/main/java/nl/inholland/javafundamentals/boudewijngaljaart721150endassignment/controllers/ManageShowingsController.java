package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.controllers;

import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.data.Database;
import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.User;

public class ManageShowingsController {
    private User user;

    private Database database;

    public void giveData(User user, Database database) {
        this.user = user;
        this.database = database;
    }
}
