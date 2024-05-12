package com.example.mobilebanking.activity;

import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilebanking.databinding.ActivityLoginPageBinding;
import com.example.mobilebanking.helper.DatabaseHelper;
import com.example.mobilebanking.helper.SessionManager;
import com.example.mobilebanking.model.Musteri;
import com.example.mobilebanking.utils.MyAlertDialog;

public class LoginPageActivity extends AppCompatActivity {
    ActivityLoginPageBinding binding;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new DatabaseHelper(this);

        if (SessionManager.isLoggedIn(this)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        binding = ActivityLoginPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.login.setOnClickListener(v -> {
            String tc = binding.username.getText().toString();
            String password = binding.password.getText().toString();

            if (tc.trim().length() != 11 || password.trim().length() != 6) {
                MyAlertDialog.showAlertDialog(this, "Hata", "TC ve Şifre uygun format uzunlukta olmalı.");
                return;
            }

            Musteri currentMusteri = databaseHelper.selectMusteri(tc, password);

            if (currentMusteri == null) {
                MyAlertDialog.showAlertDialog(this, "Hata", "Müşteri bulunamadı");
                return;
            }

            SessionManager.login(this, String.valueOf(currentMusteri.getMusteriNo()));
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        binding.goToSignUp.setOnClickListener( v -> {
            Intent intent = new Intent(this, SignupPageActivity.class);
            startActivity(intent);
        });

    }
}