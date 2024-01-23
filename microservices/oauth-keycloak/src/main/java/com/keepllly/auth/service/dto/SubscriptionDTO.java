package com.keepllly.auth.service.dto;

import javax.validation.constraints.NotNull;

import com.keepllly.auth.web.rest.vm.keycloak.AuthTokenResponse;
import com.keeplly.utils.Avatar;
import com.keeplly.utils.data.dto.IndividualDTO;
import com.keeplly.utils.data.dto.OrganizationDTO;
import com.keeplly.utils.enums.GenderEnum;

import java.util.Objects;

public class SubscriptionDTO extends OrganizationDTO {

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String firstname;

    @NotNull
    private String lastname;

    @NotNull
    private GenderEnum gender;

    @NotNull
    private String jobPosition;

    private String ownerCountry;

    public SubscriptionDTO() {
    }

    public SubscriptionDTO(String username, String password, String firstname, String lastname, GenderEnum gender, String jobPosition) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.jobPosition = jobPosition;
    }

    public SubscriptionDTO(String username, String password, String firstname, String lastname, GenderEnum gender, String jobPosition, String ownerCountry) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.jobPosition = jobPosition;
        this.ownerCountry = ownerCountry;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public String getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }

    public boolean isHasCollectionTemplateScript() {
        return hasCollectionTemplateScript;
    }

    public void setHasCollectionTemplateScript(boolean hasCollectionTemplateScript) {
        this.hasCollectionTemplateScript = hasCollectionTemplateScript;
    }

    public String getOwnerCountry() {
        return ownerCountry;
    }

    public void setOwnerCountry(String ownerCountry) {
        this.ownerCountry = ownerCountry;
    }
    public SubscriptionDTO fillUserInfo(AuthTokenResponse user){
        setId(Objects.requireNonNull(user).getId());
        setOwnerRef("SYSTEM");
        setCreatedBy("SYSTEM");
        return this;
    }
    public SubscriptionDTO fillIndividualInfo(IndividualDTO individualDTO){
        setOwnerRef(individualDTO.getUid());
        setCreatedBy(individualDTO.getUid());
        setId(null);
        setSearchable(false);
        setAvatar(Avatar.create(getName()));
        return this;
    }

    @Override
    public String toString() {
        return "SubscriptionDTO{" +
            "username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", firstname='" + firstname + '\'' +
            ", lastname='" + lastname + '\'' +
            ", gender=" + gender +
            ", jobPosition='" + jobPosition + '\'' +
            ", ownerCountry='" + ownerCountry + '\'' +
            '}';
    }
}
