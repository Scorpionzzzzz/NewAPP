package com.example.appdefault;// MyApp.java
import android.app.Application;

public class MyApp extends Application {
    private static long currentUserId = -1;

    public static long getCurrentUserId() {
        return currentUserId;
    }

    public static void setCurrentUserId(long userId) {
        currentUserId = userId;
    }
}
