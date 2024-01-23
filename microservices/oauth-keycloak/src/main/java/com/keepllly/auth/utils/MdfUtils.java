package com.keepllly.auth.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MdfUtils {
    public static String hashCode(String password) {

        try {
            // Create a MessageDigest object using MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Add the string bytes to the MessageDigest
            md.update(password.getBytes());

            // Get the hashed bytes
            byte[] hashedBytes = md.digest();

            // Convert the hashed bytes to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            String hashedString = hexString.toString();

            System.out.println("Hashed String: " + hashedString);
            return hashedString;
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getLocalizedMessage());
        }
        return password;
    }
}
