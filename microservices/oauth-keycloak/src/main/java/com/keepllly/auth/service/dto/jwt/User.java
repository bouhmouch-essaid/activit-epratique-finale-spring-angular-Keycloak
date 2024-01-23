package com.keepllly.auth.service.dto.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

public class User {

    public int exp;
    public int iat;
    public String jti;
    public String iss;

    @JsonIgnore
    public List<String> aud;

    public String sub;
    public String typ;
    public String azp;
    public String session_state;
    public String acr;
    public RealmAccess realm_access;
    public ResourceAccess resource_access;
    public String scope;
    public String sid;
    public boolean email_verified;
    public String name;
    public String preferred_username;
    public String given_name;
    public String family_name;
    public String email;
}
