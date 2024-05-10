package com.example.mobilebanking;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilebanking.databinding.ActivitySignupPageBinding;
import com.example.mobilebanking.helper.DatabaseHelper;
import com.example.mobilebanking.model.Musteri;

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
                System.out.println("Tıklandı");
                String fullname = binding.fullname.toString();
                String tc = binding.username.toString();
                String sifre = binding.password.toString();
                System.out.println("fname = " + fullname);
                System.out.println("tc = " + tc );
                System.out.println("sifre = " + sifre);

                Musteri musteri = new Musteri(fullname, tc, sifre);

                if (databaseHelper.addMusteri(musteri) != -1){
                    System.out.println("Eklendi");
                } else {
                    System.out.println("Hata");
                }
            }
        });
    }
}