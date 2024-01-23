package com.keepllly.auth.web.rest.vm.keycloak.user;

import java.util.ArrayList;

public class UserInfo {

    private String id;
    private long createdTimestamp;
    private String username;
    private boolean enabled;
    private boolean totp;
    private boolean emailVerified;
    private String firstName;
    private String lastName;
    private String email;
    private ArrayList<Object> disableableCredentialTypes;
    private ArrayList<Object> requiredActions;
    private int notBefore;
    private Access access;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(long createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
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

    public boolean isTotp() {
        return totp;
    }

    public void setTotp(boolean totp) {
        this.totp = totp;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
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

    public ArrayList<Object> getDisableableCredentialTypes() {
        return disableableCredentialTypes;
    }

    public void setDisableableCredentialTypes(ArrayList<Object> disableableCredentialTypes) {
        this.disableableCredentialTypes = disableableCredentialTypes;
    }

    public ArrayList<Object> getRequiredActions() {
        return requiredActions;
    }

    public void setRequiredActions(ArrayList<Object> requiredActions) {
        this.requiredActions = requiredActions;
    }

    public int getNotBefore() {
        return notBefore;
    }

    public void setNotBefore(int notBefore) {
        this.notBefore = notBefore;
    }

    public Access getAccess() {
        return access;
    }

    public void setAccess(Access access) {
        this.access = access;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
            "id='" + id + '\'' +
            ", createdTimestamp=" + createdTimestamp +
            ", username='" + username + '\'' +
            ", enabled=" + enabled +
            ", totp=" + totp +
            ", emailVerified=" + emailVerified +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", disableableCredentialTypes=" + disableableCredentialTypes +
            ", requiredActions=" + requiredActions +
            ", notBefore=" + notBefore +
            ", access=" + access +
            '}';
    }
}
