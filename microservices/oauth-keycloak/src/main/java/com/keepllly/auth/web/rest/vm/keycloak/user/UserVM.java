package com.keepllly.auth.web.rest.vm.keycloak.user;

import java.util.ArrayList;

public class UserVM {

    private String id;
    private String username;
    private boolean enabled = true;
    private String firstName;
    private String lastName;
    private String email;
    private ArrayList<Credential> credentials = new ArrayList<Credential>();

    public UserVM() {}

    public UserVM(String id, String username, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.credentials.add(new Credential(password));
    }

    public UserVM(String username, String firstName, String lastName, String email, String password) {
        this.username = username;
        this.enabled = true;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.credentials.add(new Credential(password));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Credential> getCredentials() {
        return credentials;
    }

    public void setCredentials(ArrayList<Credential> credentials) {
        this.credentials = credentials;
    }
}
