package com.project.userservice.utils;

import java.util.regex.Pattern;

public class EmailUtils {

    private static Pattern emailPattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    public static boolean isValid(String email) {
        return emailPattern.matcher(email).matches();
    }
}
