package com.example.mobilebanking.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilebanking.R;
import com.example.mobilebanking.helper.DatabaseHelper;
import com.example.mobilebanking.model.Musteri;
import com.example.mobilebanking.utils.MyAlertDialog;

public class ProfileFragment extends Fragment {
    private Musteri musteri;
    private DatabaseHelper databaseHelper;
    public ProfileFragment(Musteri musteri, DatabaseHelper databaseHelper) {
        this.musteri = musteri;
        this.databaseHelper = databaseHelper;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView adSoyad = view.findViewById(R.id.adSoyadTextView);
        TextView musteriNo = view.findViewById(R.id.musteriNoTextView);

        adSoyad.setText(musteri.getMusteriFullname());
        musteriNo.setText("#" + String.valueOf(musteri.getMusteriNo()));

        Button sifreDegistirButon = view.findViewById(R.id.changePasswordButton);

        sifreDegistirButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText currentPasswordText = view.findViewById(R.id.currentPassword);
                EditText newPasswordText = view.findViewById(R.id.newPassword);

                String cp = currentPasswordText.getText().toString();
                String np = newPasswordText.getText().toString();

                if (cp.trim().length() != 6 || np.trim().length() != 6) {
                    MyAlertDialog.showAlertDialog(getContext(), "Hata", "Eski ve yeni şifre 6 haneli olmalıdır.");
                } else if (!cp.equals(musteri.getMusteriSifre())){
                    MyAlertDialog.showAlertDialog(getContext(), "Hata", "Mevcut şifre yanlış.");
                } else {
                    musteri.setMusteriSifre(np);
                    databaseHelper.updateMusteriSifre(musteri.getMusteriNo(), np);
                    Toast.makeText(getContext(), "Şifre güncellendi", Toast.LENGTH_LONG).show();
                    currentPasswordText.setText("");
                    newPasswordText.setText("");
                }
            }
        });

        return view;
    }
}