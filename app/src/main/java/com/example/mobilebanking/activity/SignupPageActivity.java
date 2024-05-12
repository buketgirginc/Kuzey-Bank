package com.example.mobilebanking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilebanking.databinding.ActivitySignupPageBinding;
import com.example.mobilebanking.helper.DatabaseHelper;
import com.example.mobilebanking.model.Musteri;
import com.example.mobilebanking.utils.MyAlertDialog;

public class SignupPageActivity extends AppCompatActivity {
    ActivitySignupPageBinding binding;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new DatabaseHelper(this);
        binding = ActivitySignupPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = binding.fullname.getText().toString();
                String tc = binding.username.getText().toString();
                String sifre = binding.password.getText().toString();

                if (fullname.trim().length() < 5 || fullname.trim().length() > 30){
                    MyAlertDialog.showAlertDialog(v.getContext(), "Hata","İsminiz 5-30 karakter arası olmalı");
                    return;
                }

                if (tc.trim().length() != 11){
                    MyAlertDialog.showAlertDialog(v.getContext(), "Hata","TC 11 karakter olmalı");
                    return;
                }

                if (sifre.trim().length() != 6){
                    MyAlertDialog.showAlertDialog(v.getContext(), "Hata","Sifre 6 karakter olmalı");
                    return;
                }

                Musteri musteri = new Musteri(fullname, tc, sifre);

                if (databaseHelper.addMusteri(musteri) != -1){
                    Toast.makeText(SignupPageActivity.this, "Kayıt başarılı.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignupPageActivity.this, LoginPageActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SignupPageActivity.this, "Kayıt olunamadı.", Toast.LENGTH_LONG).show();
                }
            }
        });

        binding.goToLogIn.setOnClickListener( v -> {
            Intent intent = new Intent(this, LoginPageActivity.class);
            startActivity(intent);
        });
    }
}