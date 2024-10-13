package nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models;

import nl.inholland.javafundamentals.boudewijngaljaart721150endassignment.models.enums.Role;

import java.io.Serializable;


public class User extends Person implements Serializable {
    private String username;
    private String password;
    protected Role role;

    public User(String firstName, String lastName, String username, String password) {
        super(firstName, lastName);
        this.username = username;
        this.password = password;
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
}
