package com.example.sms.util;

public class Validator {
    public static boolean isNonEmpty(String s) {
        return s != null && !s.trim().isEmpty();
    }

    public static boolean isPositiveInt(int x) {
        return x > 0;
    }
}
