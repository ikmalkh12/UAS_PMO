package com.example.uts;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "login_pref";
    private static final String KEY_USER_ID = "user_id";

    private static SessionManager instance;
    private SharedPreferences prefs;

    private SessionManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SessionManager getInstance(Context context) {
        if (instance == null) instance = new SessionManager(context);
        return instance;
    }

    public void saveUserId(int userId) {
        prefs.edit().putInt(KEY_USER_ID, userId).apply();
    }

    public int getLoggedInUserId() {
        return prefs.getInt(KEY_USER_ID, -1);
    }

    public void clearSession() {
        prefs.edit().clear().apply();
    }
}
