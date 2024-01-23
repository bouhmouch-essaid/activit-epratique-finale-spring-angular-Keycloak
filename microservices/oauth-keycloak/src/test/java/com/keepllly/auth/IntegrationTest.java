package com.keepllly.auth;

import com.keepllly.auth.KeepllyauthApp;
import com.keepllly.auth.config.AsyncSyncConfiguration;
import com.keepllly.auth.config.EmbeddedMongo;
import com.keepllly.auth.config.TestSecurityConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { KeepllyauthApp.class, AsyncSyncConfiguration.class, TestSecurityConfiguration.class })
@EmbeddedMongo
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public @interface IntegrationTest {
}
