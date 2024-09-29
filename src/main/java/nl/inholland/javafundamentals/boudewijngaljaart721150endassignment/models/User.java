package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models;

import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.enums.Role;

public class User {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    protected Role role;

    public User(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public Role getRole() {
        return this.role;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}
