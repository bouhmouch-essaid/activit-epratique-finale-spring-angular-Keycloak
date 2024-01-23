package com.keepllly.auth.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexValidator {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(
        "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
        Pattern.CASE_INSENSITIVE
    );
    public static final Pattern VALID_PHONE_REGEX = Pattern.compile(
        "^(\\d{9,15}|\\d{2}[-\\s]?\\d{2}[-\\s]?\\d{2}[-\\s]?\\d{2}[-\\s]?\\d{2})$",
        Pattern.CASE_INSENSITIVE
    );

    public static boolean validate(String emailStr, Pattern pattern) {
        Matcher matcher = pattern.matcher(emailStr);
        return matcher.find();
    }
}
