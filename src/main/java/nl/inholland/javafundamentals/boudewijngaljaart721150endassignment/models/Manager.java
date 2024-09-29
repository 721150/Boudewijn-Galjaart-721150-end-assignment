package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models;

import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.enums.Role;

public class Manager extends User {
    public Manager(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password);
        super.role = Role.MANAGEMENT;
    }
}
