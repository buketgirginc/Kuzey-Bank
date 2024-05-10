package com.example.mobilebanking;

import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilebanking.databinding.ActivityLoginPageBinding;

public class LoginPage extends AppCompatActivity {
    ActivityLoginPageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.goToSignUp.setOnClickListener( v -> {
            Intent intent = new Intent(this, SignupPageActivity.class);
            startActivity(intent);
        });

    }
}