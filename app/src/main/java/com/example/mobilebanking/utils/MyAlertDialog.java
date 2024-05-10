package com.example.mobilebanking.utils;

import android.app.AlertDialog;
import android.content.Context;

public class MyAlertDialog {

    public static void showAlertDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("Tamam", null) // Sadece tamam butonu ekleniyor
                .show();
    }
}
