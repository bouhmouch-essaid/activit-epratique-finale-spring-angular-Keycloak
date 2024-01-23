package com.keepllly.auth.config;

import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import tech.jhipster.config.JHipsterProperties;

@Configuration
public class MailConfig {

    private final ApplicationProperties applicationProperties;

    public MailConfig(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(applicationProperties.getMail().getHost());
        javaMailSender.setPort(applicationProperties.getMail().getPort());
        javaMailSender.setUsername(applicationProperties.getMail().getUsername());
        javaMailSender.setPassword(applicationProperties.getMail().getPassword());

        // Configure the JavaMailSender
        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.auth", applicationProperties.getMail().isAuth());
        mailProperties.put("mail.smtp.starttls.enable", applicationProperties.getMail().isStarttlsEnable());
        mailProperties.put("mail.smtp.ssl.trust", applicationProperties.getMail().getSslTrust());

        javaMailSender.setJavaMailProperties(mailProperties);
        return javaMailSender;
    }
}
