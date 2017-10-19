package com.example.reminderapi.util;

public class Objects {

    public static boolean isNullOrEmpty(Object obj) {
        return obj == null || obj.toString() == null
                || obj.toString().trim().length() == 0;
    }

}
