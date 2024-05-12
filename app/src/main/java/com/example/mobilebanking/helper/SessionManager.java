package com.example.mobilebanking.helper;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.mobilebanking.model.Musteri;

public class SessionManager {
    private static final String PREF_NAME = "MyAppPreferences";
    private static final String KEY_SESSION_ID = "sessionId";

    public static boolean isLoggedIn(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.contains(KEY_SESSION_ID);
    }

    public static void login(Context context, String sessionId) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        preferences.edit().putString(KEY_SESSION_ID, sessionId).apply();
    }

    public static void logout(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        preferences.edit().remove(KEY_SESSION_ID).apply();
    }

    public static String getSessionId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(KEY_SESSION_ID, null);
    }

    public static Musteri getUser(Context context) {
        if (!isLoggedIn(context)) {
            return null;
        }

        // Oturum açık ise, oturum kimliğini al
        String sessionId = getSessionId(context);

        // Şimdi, SQLite veritabanından kullanıcıyı müşteri numarasına göre getir
        DatabaseHelper helper = new DatabaseHelper(context);

        return helper.selectMusteri(getSessionId(context));
    }
}
