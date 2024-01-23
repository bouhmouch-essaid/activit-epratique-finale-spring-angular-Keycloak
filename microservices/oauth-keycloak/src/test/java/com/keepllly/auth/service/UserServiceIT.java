package com.keepllly.auth.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.keepllly.auth.IntegrationTest;
import com.keepllly.auth.config.Constants;
import com.keepllly.auth.domain.User;
import com.keepllly.auth.repository.UserRepository;
import com.keepllly.auth.security.AuthoritiesConstants;
import com.keepllly.auth.service.dto.AdminUserDTO;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * Integration tests for {@link UserService}.
 */
@IntegrationTest
class UserServiceIT {

    private static final String DEFAULT_LOGIN = "johndoe";

    private static final String DEFAULT_EMAIL = "johndoe@localhost";

    private static final String DEFAULT_FIRSTNAME = "john";

    private static final String DEFAULT_LASTNAME = "doe";

    private static final String DEFAULT_IMAGEURL = "http://placehold.it/50x50";

    private static final String DEFAULT_LANGKEY = "dummy";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private User user;

    private Map<String, Object> userDetails;

    @BeforeEach
    public void init() {
        userRepository.deleteAll();
        user = new User();
        user.setLogin(DEFAULT_LOGIN);
        user.setActivated(true);
        user.setEmail(DEFAULT_EMAIL);
        user.setFirstName(DEFAULT_FIRSTNAME);
        user.setLastName(DEFAULT_LASTNAME);
        user.setImageUrl(DEFAULT_IMAGEURL);
        user.setLangKey(DEFAULT_LANGKEY);

        userDetails = new HashMap<>();
        userDetails.put("sub", DEFAULT_LOGIN);
        userDetails.put("email", DEFAULT_EMAIL);
        userDetails.put("given_name", DEFAULT_FIRSTNAME);
        userDetails.put("family_name", DEFAULT_LASTNAME);
        userDetails.put("picture", DEFAULT_IMAGEURL);
    }


    private OAuth2AuthenticationToken createMockOAuth2AuthenticationToken(Map<String, Object> userDetails) {
        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(AuthoritiesConstants.ANONYMOUS));
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
            "anonymous",
            "anonymous",
            authorities
        );
        usernamePasswordAuthenticationToken.setDetails(userDetails);
        OAuth2User user = new DefaultOAuth2User(authorities, userDetails, "sub");

        return new OAuth2AuthenticationToken(user, authorities, "oidc");
    }
}
