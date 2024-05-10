package com.example.mobilebanking;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
                    Intent intent = new Intent(MainActivity.this, LoginPage.class);
                    startActivity(intent);
            }
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.bottomNavigationView);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // İlk olarak ana sayfayı aç
        openHomeFragment();
    }
    private void openHomeFragment() {
        openFragment(new PartialMainFragment());
    }

    private void openCurrencyFragment() {
        openFragment(new CurrencyTableFragment());
    }

    private void openProfileFragment() {
        openFragment(new ProfileFragment());
    }

    private void openFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}