package com.keepllly.auth.service;

import com.keepllly.auth.config.MailConfig;
import com.keepllly.auth.domain.Subscriber;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Properties;
import java.util.function.BiConsumer;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import tech.jhipster.config.JHipsterProperties;

/**
 * Service for sending emails.
 * <p>
 * We use the {@link Async} annotation to send emails asynchronously.
 */
@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private final JHipsterProperties jHipsterProperties;
    private final JavaMailSender javaMailSender;
    private final MessageSource messageSource;
    private final SpringTemplateEngine templateEngine;
    private final String USER = "user";
    private final String BASE_URL = "baseUrl";
    private final MailConfig mailConfig;

    public MailService(
        JHipsterProperties jHipsterProperties,
        JavaMailSender javaMailSender,
        MessageSource messageSource,
        SpringTemplateEngine templateEngine,
        MailConfig mailConfig
    ) {
        this.jHipsterProperties = jHipsterProperties;
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
        this.mailConfig = mailConfig;
    }

    /**
     * Initializes and configures the JavaMailSender for sending emails.
     *
     * @return The configured {@link JavaMailSender}.
     */
    private JavaMailSender itializeMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587);
        javaMailSender.setUsername("etablissementfstg@gmail.com");
        javaMailSender.setPassword("fnsqcybjgcgkzumb");

        // Configure the JavaMailSender
        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.auth", "true");
        mailProperties.put("mail.smtp.starttls.enable", "true");
        mailProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        javaMailSender.setJavaMailProperties(mailProperties);
        return javaMailSender;
    }

    /**
     * Asynchronously sends an email using the configured JavaMailSender.
     *
     * @param to            The recipient's email address.
     * @param subject       The subject of the email.
     * @param content       The content of the email.
     * @param messageConfig A {@link BiConsumer} for configuring the MimeMessageHelper.
     */
    private void sendEmailAsync(String to, String subject, String content, BiConsumer<MimeMessageHelper, String> messageConfig) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, false, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setSubject(subject);
            messageConfig.accept(message, content);

            JavaMailSender mailSender = mailConfig.javaMailSender();

            // Send the email
            mailSender.send(mimeMessage);
        } catch (MailException | MessagingException e) {
            log.warn("Email could not be sent to user '{}'", to, e);
        }
    }

    /**
     * Gets a localized message based on the provided key and locale.
     *
     * @param key    The key to retrieve the message.
     * @param locale The {@link Locale} for localization.
     * @return The localized message.
     */
    private String getMessage(String key, Locale locale) {
        return messageSource.getMessage(key, null, locale);
    }

    /**
     * Creates a Thymeleaf context for email templates, including variables for the user and base URL.
     *
     * @param locale   The {@link Locale} for localization.
     * @param customer The {@link Subscriber} for whom the email is being created.
     * @return The configured {@link Context} for Thymeleaf templates.
     */
    private Context createContext(Locale locale, Subscriber customer) {
        Context context = new Context(locale);
        context.setVariable(USER, customer);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        return context;
    }

    /**
     * Sends a simple email.
     *
     * @param to      The recipient's email address.
     * @param subject The subject of the email.
     * @param content The content of the email.
     * @param isHtml  Indicates whether the content is in HTML format.
     */
    public void sendEmail(String to, String subject, String content, boolean isHtml) {
        sendEmailAsync(
            to,
            subject,
            content,
            (message, text) -> {
                try {
                    message.setText(text, isHtml);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            }
        );
    }

    /**
     * Sends an email using a Thymeleaf template for the specified customer.
     *
     * @param customer     The {@link Subscriber} for whom the email is being created.
     * @param templateName The name of the Thymeleaf template.
     * @param titleKey     The key for the email title in the message source.
     */
    public void sendEmailFromTemplate(Subscriber customer, String templateName, String titleKey) {
        if (customer.getEmail() == null) {
            log.debug("Email doesn't exist for user '{}'", customer.getEmail());
            return;
        }
        Locale locale = Locale.forLanguageTag("fr");
        Context context = createContext(locale, customer);
        String content = templateEngine.process(templateName, context);
        String subject = getMessage(titleKey, locale);
        sendEmail(customer.getEmail(), subject, content, true);
    }

    /**
     * Sends an activation email to the specified customer.
     *
     * @param customer The {@link Subscriber} for whom the activation email is being sent.
     */
    public void sendActivationEmail(Subscriber customer) {
        log.debug("Sending activation email to '{}'", customer.getEmail());
        sendEmailFromTemplate(customer, "mail/activationEmail", "email.activation.title");
    }

    /**
     * Sends a creation email to the specified customer.
     *
     * @param customer The {@link Subscriber} for whom the creation email is being sent.
     */
    public void sendCreationEmail(Subscriber customer) {
        log.debug("Sending creation email to '{}'", customer.getEmail());
        sendEmailFromTemplate(customer, "mail/creationEmail", "email.activation.title");
    }
}
