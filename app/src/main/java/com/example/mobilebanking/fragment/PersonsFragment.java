package com.example.mobilebanking.fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.mobilebanking.R;
import com.example.mobilebanking.adapter.PersonAdapter;
import com.example.mobilebanking.helper.DatabaseHelper;
import com.example.mobilebanking.model.Alici;
import com.example.mobilebanking.model.Currency;
import com.example.mobilebanking.model.Hesap;
import com.example.mobilebanking.model.Musteri;

import java.util.ArrayList;
import java.util.List;

public class PersonsFragment extends Fragment {

    private Musteri musteri;
    private DatabaseHelper databaseHelper;

    public PersonsFragment(Musteri musteri) {
        this.musteri = musteri;
    }

    private List<Alici> items;

    private PersonAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void showAddAccountDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Yeni Alıcı Ekle");

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_person, null);
        builder.setView(dialogView);

        databaseHelper = new DatabaseHelper(getContext());

        final EditText personName = dialogView.findViewById(R.id.personName);
        final EditText accountNumber = dialogView.findViewById(R.id.accountNumber);

        builder.setPositiveButton("Ekle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String accountNumberStr = accountNumber.getText().toString();
                String personNameStr = personName.getText().toString();

                Alici yeniAlici = new Alici();
                yeniAlici.setAliciAdi(personNameStr);
                yeniAlici.setAliciHesapNo(Integer.valueOf(accountNumberStr));
                yeniAlici.setMusteriNo(musteri.getMusteriNo());

                databaseHelper.addAlici(yeniAlici);
                musteri = databaseHelper.selectMusteri(String.valueOf(musteri.getMusteriNo()));
                items = musteri.getAlicilar();
                adapter.items = items;
                adapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_persons, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.personsRecyclerView);

        items = musteri.getAlicilar();

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        adapter = new PersonAdapter(this.getContext(), items);

        recyclerView.setAdapter(adapter);

        Button addAccountButton = view.findViewById(R.id.addAccountButton);
        addAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddAccountDialog();
            }
        });
        return view;
    }
}