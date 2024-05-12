package com.example.mobilebanking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mobilebanking.helper.DatabaseHelper;
import com.example.mobilebanking.helper.SessionManager;
import com.example.mobilebanking.model.Hesap;
import com.example.mobilebanking.model.Musteri;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            musteriGuncelle();

            switch (item.getItemId()) {
                case R.id.menuHome:
                    // Ana sayfayı aç
                    openHomeFragment();
                    return true;
                case R.id.menuCurrency:
                    // Kur sayfasını aç
                    openCurrencyFragment();
                    return true;
                case R.id.menuProfile:
                    // Profil sayfasını aç
                    openProfileFragment();
                    return true;

                default:
                    SessionManager.logout(MainActivity.this);
                    Intent intent = new Intent(MainActivity.this, LoginPage.class);
                    startActivity(intent);
                    finish();
            }
            return false;
        }
    };

    private void musteriGuncelle(){
        musteri = databaseHelper.selectMusteri(String.valueOf(musteri.getMusteriNo()));
    }

    private Musteri musteri;
    private Hesap anaHesap;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!SessionManager.isLoggedIn(this)) {
            Intent intent = new Intent(this, LoginPage.class);
            startActivity(intent);
            finish();
            return;
        }


        musteri = SessionManager.getUser(this);
        anaHesap = musteri.getHesaplar().get(0);
        databaseHelper = new DatabaseHelper(this);

        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.bottomNavigationView);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // İlk olarak ana sayfayı aç
        openHomeFragment();

        FloatingActionButton fb = findViewById(R.id.accountsButton);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musteriGuncelle();
                openAccountsFragment();
            }
        });
    }
    private void openHomeFragment() {
        openFragment(new PartialMainFragment(anaHesap, musteri));
    }

    private void openCurrencyFragment() {
        openFragment(new CurrencyTableFragment());
    }

    private void openProfileFragment() {
        openFragment(new ProfileFragment(musteri, databaseHelper));
    }

    private void openAccountsFragment() {
        openFragment(new AccountsFragment(musteri));
    }

    private void openFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}