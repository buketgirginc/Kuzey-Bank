package com.example.mobilebanking.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mobilebanking.R;

public class BuyCurrencyFragment extends Fragment {

    private Spinner spinnerCurToBuy;
    private Spinner spinnerCurToSell;
    private EditText editTextAmount;
    private Button buttonBuyCurrency;

    public BuyCurrencyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buy_currency, container, false);

        spinnerCurToBuy = view.findViewById(R.id.spinnerCurToBuy);
        editTextAmount = view.findViewById(R.id.editTextAmount);
        buttonBuyCurrency = view.findViewById(R.id.buttonBuyCurrency);

        buttonBuyCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTransactionDetailsDialog();
            }
        });

        return view;
    }

    private void showTransactionDetailsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_buy_transaction_details, null);
        builder.setView(dialogView);

        TextView textViewCurToBuy = dialogView.findViewById(R.id.textViewCurAccount);
        TextView textViewCurToSell = dialogView.findViewById(R.id.textViewTLAccount);
        TextView textViewAmountValue = dialogView.findViewById(R.id.textViewAmount);

        String currencyToBuy = "";//spinnerCurToBuy.getSelectedItem().toString();
        String currencyToSell = "";//spinnerCurToSell.getSelectedItem().toString();
        String amount = editTextAmount.getText().toString();

        textViewCurToBuy.setText("Alınacak Döviz: " + currencyToBuy);
        textViewCurToSell.setText("TL Hesabı Seçimi: " + currencyToSell);
        textViewAmountValue.setText("Tutar Girişi: " + amount);

        builder.setPositiveButton("Onayla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                performPurchase();
            }
        });

        builder.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void performPurchase() {
        // Perform purchase action here
        // This method is called when user clicks "Onayla" button in the dialog
    }
}
