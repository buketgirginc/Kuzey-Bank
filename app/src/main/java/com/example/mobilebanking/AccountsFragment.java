package com.example.mobilebanking;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.example.mobilebanking.helper.DatabaseHelper;
import com.example.mobilebanking.model.Currency;
import com.example.mobilebanking.model.Hesap;
import com.example.mobilebanking.model.Musteri;

import java.util.ArrayList;
import java.util.List;


public class AccountsFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Hesap> items;
    private MyAdapter adapter;

    private Musteri musteri;

    private DatabaseHelper databaseHelper;

    public AccountsFragment(Musteri musteri) {
        this.musteri = musteri;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_accounts, container, false);

        recyclerView = view.findViewById(R.id.accountsRecyclerView);

        items = musteri.getHesaplar();
        databaseHelper = new DatabaseHelper(getContext());
        adapter = new MyAdapter(getContext(), items);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
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

    private void showAddAccountDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Yeni Hesap Ekle");

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_account, null);
        builder.setView(dialogView);

        final EditText accountNameEditText = dialogView.findViewById(R.id.accountNameEditText);
        final RadioGroup currencyRadioGroup = dialogView.findViewById(R.id.currencyRadioGroup);

        builder.setPositiveButton("Ekle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String accountName = accountNameEditText.getText().toString();
                Currency currency = getSelectedCurrency(currencyRadioGroup);
                Hesap yeniHesap = new Hesap();
                yeniHesap.setMusteriNo(musteri.getMusteriNo());
                yeniHesap.setHesapAdi(accountName);
                yeniHesap.setHesapBakiye(0.00f);
                yeniHesap.setHesapDovizTipi(currency);
                databaseHelper.addHesap(yeniHesap);
                musteri = databaseHelper.selectMusteri(String.valueOf(musteri.getMusteriNo()));
                items = musteri.getHesaplar();
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

    private Currency getSelectedCurrency(RadioGroup currencyRadioGroup) {
        int selectedId = currencyRadioGroup.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton selectedRadioButton = currencyRadioGroup.findViewById(selectedId);
            Currency currency = Currency.fromName(selectedRadioButton.getText().toString());
            return currency;
        }
        return null;
    }

}