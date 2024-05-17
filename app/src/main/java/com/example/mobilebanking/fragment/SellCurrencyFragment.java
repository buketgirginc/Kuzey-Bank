package com.example.mobilebanking.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mobilebanking.R;
import com.example.mobilebanking.adapter.AccountAdapter;
import com.example.mobilebanking.adapter.AccountSpinnerAdapter;
import com.example.mobilebanking.helper.DatabaseHelper;
import com.example.mobilebanking.model.Currency;
import com.example.mobilebanking.model.Hesap;
import com.example.mobilebanking.model.Islem;
import com.example.mobilebanking.model.Musteri;
import com.example.mobilebanking.utils.MyAlertDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SellCurrencyFragment extends Fragment {

    private Spinner spinnerCurToBuy;
    private Spinner spinnerCurToSell;
    private EditText editTextAmount;
    private Button buttonBuyCurrency;
    private Musteri musteri;

    private DatabaseHelper databaseHelper;

    public SellCurrencyFragment(Musteri musteri, DatabaseHelper databaseHelper) {
        this.musteri = musteri;
        this.databaseHelper = databaseHelper;
    }

    List<Hesap> DovizHesaplar;
    List<Hesap> TLHesaplar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sell_currency, container, false);

        spinnerCurToBuy = view.findViewById(R.id.spinnerCurToSell);
        editTextAmount = view.findViewById(R.id.editTextAmount);
        buttonBuyCurrency = view.findViewById(R.id.buttonSellCurrency);
        spinnerCurToSell = view.findViewById(R.id.spinnerTLAccount);

        fetchAndPutAccounts();

        buttonBuyCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hesap currencyToSell = (Hesap) spinnerCurToBuy.getSelectedItem();
                Float allAmount = Float.parseFloat(editTextAmount.getText().toString());
                if (allAmount > currencyToSell.getHesapBakiye()){
                    MyAlertDialog.showAlertDialog(getContext(), "Hata","Seçtiğiniz hesabın bakiyesi yetersiz.");
                } else {
                    showTransactionDetailsDialog();
                }
            }
        });

        return view;
    }

    private void showTransactionDetailsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_sell_transaction_details, null);
        builder.setView(dialogView);

        TextView textViewCurToBuy = dialogView.findViewById(R.id.textViewCurAccount);
        TextView textViewCurToSell = dialogView.findViewById(R.id.textViewTLAccount);
        TextView textViewAmountValue = dialogView.findViewById(R.id.textViewAmount);

        Hesap currencyToBuy = (Hesap) spinnerCurToBuy.getSelectedItem();
        Hesap currencyToSell = (Hesap) spinnerCurToSell.getSelectedItem();
        Float buyAmount = Float.parseFloat(editTextAmount.getText().toString());
        Float allAmount = buyAmount * currencyToBuy.getHesapDovizTipi().getTLCurrency();

        textViewCurToBuy.setText("Kaynak : " + String.format("%s %s %s",currencyToBuy.getHesapAdi(), currencyToBuy.getHesapBakiye(), currencyToBuy.getHesapDovizTipi().getName()));
        textViewCurToSell.setText("Hedef : " + String.format("%s %s TL",currencyToSell.getHesapAdi(), currencyToSell.getHesapBakiye()));
        textViewAmountValue.setText("Hesaba Aktarılacak: " + String.format("%2f TL", allAmount));

        builder.setPositiveButton("Onayla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                performPurchase(currencyToBuy, currencyToSell, buyAmount, allAmount);
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

    private void fetchAndPutAccounts(){

        DovizHesaplar = musteri.getHesaplar().stream()
                .filter(hesap -> hesap.getHesapDovizTipi() != Currency.TL)
                .collect(Collectors.toList());

        TLHesaplar = musteri.getHesaplar().stream()
                .filter(hesap -> hesap.getHesapDovizTipi() == Currency.TL)
                .collect(Collectors.toList());

        spinnerCurToBuy.setAdapter(new AccountSpinnerAdapter(getContext(), DovizHesaplar));
        spinnerCurToSell.setAdapter(new AccountSpinnerAdapter(getContext(), TLHesaplar));
    }

    private void performPurchase(Hesap kaynak, Hesap hedef, Float doviz, Float tl) {
        kaynak.setHesapBakiye(kaynak.getHesapBakiye() - doviz);
        hedef.setHesapBakiye(hedef.getHesapBakiye() + tl);

        databaseHelper.saveHesap(kaynak);
        databaseHelper.saveHesap(hedef);

        Islem islemDovizAlis = new Islem();
        islemDovizAlis.setHesap(kaynak);
        islemDovizAlis.setIslemTipi(databaseHelper.getIslemTipi(9));
        islemDovizAlis.setIslemMiktar(doviz);
        databaseHelper.addIslem(islemDovizAlis);

        Islem islemTLSatis = new Islem();
        islemTLSatis.setHesap(hedef);
        islemTLSatis.setIslemTipi(databaseHelper.getIslemTipi(3));
        islemTLSatis.setIslemMiktar(tl);
        databaseHelper.addIslem(islemTLSatis);

        Toast.makeText(getContext(), "İşlem başarılı!", Toast.LENGTH_SHORT).show();

        fetchAndPutAccounts();

    }
}
