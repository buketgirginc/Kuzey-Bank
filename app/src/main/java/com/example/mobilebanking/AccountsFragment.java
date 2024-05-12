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



import java.util.ArrayList;
import java.util.List;


public class AccountsFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<AccountItem> items;
    private MyAdapter adapter;

    public AccountsFragment() {
        // Required empty public constructor
    }

    public static AccountsFragment newInstance(String param1, String param2) {
        AccountsFragment fragment = new AccountsFragment();
        Bundle args = new Bundle();
        return fragment;
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

        items = new ArrayList<AccountItem>();
        items.add(new AccountItem(1, "Buket", "100"));

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
                String currency = getSelectedCurrency(currencyRadioGroup);
                items.add(new AccountItem(items.size() + 1, accountName, currency));
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

    private String getSelectedCurrency(RadioGroup currencyRadioGroup) {
        int selectedId = currencyRadioGroup.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton selectedRadioButton = currencyRadioGroup.findViewById(selectedId);
            return selectedRadioButton.getText().toString();
        }
        return ""; // Default olarak boş bir döviz tipi döndürülebilir
    }

}