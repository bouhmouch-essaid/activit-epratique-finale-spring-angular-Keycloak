package com.keepllly.auth.web.rest.client.feign.keycloak;

import com.keepllly.auth.web.rest.vm.keycloak.user.Credential;
import com.keepllly.auth.web.rest.vm.keycloak.user.UserInfo;
import com.keepllly.auth.web.rest.vm.keycloak.user.UserVM;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class AdminClientFallback implements AdminClient{


    @Override
    public ResponseEntity<List<UserInfo>> getUserInfo(String bearerToken, String realm_id, String username) {
        return null;
    }

    @Override
    public ResponseEntity<List<UserInfo>> getUserInfoByEmail(String bearerToken, String realm_id, String email) {
        return null;
    }

    @Override
    public ResponseEntity<String> changePassword(String bearerToken, String realm_id, String user_id, Credential credential) {
        return null;
    }

    @Override
    public ResponseEntity<String> updateUser(String bearerToken, String realm_id, String user_id, UserVM userVM) {
        return null;
    }

    @Override
    public ResponseEntity<String> deleteUser(String bearerToken, String realm_id, String user_id) {
        return null;
    }

    @Override
    public void createUser(String bearerToken, String realm_id, UserVM userVM) {
    }
}
