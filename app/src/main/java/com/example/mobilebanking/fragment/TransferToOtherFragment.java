package com.example.mobilebanking.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mobilebanking.R;
import com.example.mobilebanking.adapter.AccountSpinnerAdapter;
import com.example.mobilebanking.helper.DatabaseHelper;
import com.example.mobilebanking.model.Alici;
import com.example.mobilebanking.model.Currency;
import com.example.mobilebanking.model.Hesap;
import com.example.mobilebanking.model.Islem;
import com.example.mobilebanking.model.Musteri;

import java.util.ArrayList;
import java.util.List;

public class TransferToOtherFragment extends Fragment {

    private Musteri musteri;
    private DatabaseHelper databaseHelper;
    private Spinner spinnerReceiverAccount,spinnerSenderAccount;
    private List<Alici> alicilarList;
    private Float transferAmount;

    private List<Hesap> hesapList;

    public TransferToOtherFragment(Musteri musteri) {
        this.musteri = musteri;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transfer_to_other, container, false);

        spinnerSenderAccount = view.findViewById(R.id.spinnerSenderAccount);
        spinnerReceiverAccount = view.findViewById(R.id.spinnerReceiverAccount);


        databaseHelper = new DatabaseHelper(getContext());


        hesapList = musteri.getHesaplar();

        setupSenderAccountSpinner();

        // Transfer butonuna tıklandığında işlemi gerçekleştir
        Button buttonTransfer = view.findViewById(R.id.buttonTransfer);
        buttonTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transferMoney();
            }
        });

        return view;
    }

    private void loadAlicilar(Currency currency) {
        // Alicıları veritabanından getir
        List<Hesap> hesaplar = new ArrayList<>();
        for (Alici alici: musteri.getAlicilar()){
            Hesap aliciHesap = databaseHelper.getHesapByHesapNo(alici.getAliciHesapNo());
            if (aliciHesap != null && aliciHesap.getHesapDovizTipi() == currency)
            {
                hesaplar.add(aliciHesap);
            }

        }
        spinnerReceiverAccount.setAdapter(new AccountSpinnerAdapter(getContext(), hesaplar));

    }

    private void setupSenderAccountSpinner() {
        AccountSpinnerAdapter senderAdapter = new AccountSpinnerAdapter(getContext(), hesapList);
        spinnerSenderAccount.setAdapter(senderAdapter);

        spinnerSenderAccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               loadAlicilar(hesapList.get(position).getHesapDovizTipi());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No action needed
            }
        });
    }



    private void transferMoney() {
        Hesap senderAccount = (Hesap) spinnerSenderAccount.getSelectedItem();
        Hesap receiverAccount = (Hesap) spinnerReceiverAccount.getSelectedItem();

        EditText editTextAmount = getView().findViewById(R.id.editTextAmount);
        transferAmount = Float.parseFloat(editTextAmount.getText().toString());

        if (senderAccount.getHesapBakiye() >= transferAmount) {
            float senderNewBalance = senderAccount.getHesapBakiye() - transferAmount;
            float receiverNewBalance = receiverAccount.getHesapBakiye() + transferAmount;

            senderAccount.setHesapBakiye(senderNewBalance);
            receiverAccount.setHesapBakiye(receiverNewBalance);

            // Güncellenen hesap bilgilerini veritabanına kaydet
            DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
            databaseHelper.saveHesap(senderAccount);
            databaseHelper.saveHesap(receiverAccount);

            Islem islem = new Islem();
            islem.setHesap(senderAccount);
            islem.setIslemMiktar(transferAmount);
            islem.setIslemTipi(databaseHelper.getIslemTipi(6));

            Islem islem2 = new Islem();
            islem2.setHesap(receiverAccount);
            islem2.setIslemMiktar(transferAmount);
            islem2.setIslemTipi(databaseHelper.getIslemTipi(7));
            databaseHelper.addIslem(islem);
            databaseHelper.addIslem(islem2);


            setupSenderAccountSpinner();
            // Hesapların güncellenmiş halini göster
            showSuccessDialog();
        } else {
            showInsufficientBalanceDialog();
        }
    }

    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Transfer işlemi başarıyla gerçekleştirildi.")
                .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    private void showInsufficientBalanceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Yetersiz bakiye, transfer işlemi gerçekleştirilemedi.")
                .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }
}
