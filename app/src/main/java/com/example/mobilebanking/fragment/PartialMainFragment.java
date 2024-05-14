package com.example.mobilebanking.fragment;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mobilebanking.R;
import com.example.mobilebanking.model.Hesap;
import com.example.mobilebanking.model.Musteri;

import java.text.DecimalFormat;

public class PartialMainFragment extends Fragment {

    private Hesap hesap;
    private Musteri musteri;

    public PartialMainFragment(Hesap hesap, Musteri musteri) {
        this.hesap = hesap;
        this.musteri = musteri;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_partial_main, container, false);
        View actions = view.findViewById(R.id.partialAction);

        TextView bakiye = view.findViewById(R.id.bakiye);
        TextView hesapNo = view.findViewById(R.id.hesapNoText);
        TextView isim = view.findViewById(R.id.isim);

        DecimalFormat formatter = new DecimalFormat("###,###.00");
        String bicimliSayi = formatter.format(hesap.getHesapBakiye());
        bakiye.setText(String.format("%s %s", bicimliSayi, hesap.getHesapDovizTipi().getName()));
        hesapNo.setText(String.valueOf(hesap.getHesapNo()));
        isim.setText(hesap.getHesapAdi());
        Button kopyalaButon = view.findViewById(R.id.copyButton);

        kopyalaButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyToClipboard(String.valueOf(hesap.getHesapNo()));
            }
        });


        ImageButton alicilarBtn = actions.findViewById(R.id.imageButton3);
        ImageButton transferBtn = actions.findViewById(R.id.imageButton);

        alicilarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new PersonsFragment(musteri));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        transferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTransferDialog();
            }
        });

        return view;
    }


    private void showTransferDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_transfer_type, null);
        builder.setView(dialogView);


        Button hesaplarArasiTransferBtn = dialogView.findViewById(R.id.hesaplarArasiTransferBtn);
        Button baskaHesabaHavaleBtn = dialogView.findViewById(R.id.baskaHesabaHavaleBtn);


        hesaplarArasiTransferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Hesaplar Arası Transfer seçildi.", Toast.LENGTH_SHORT).show();

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new TransferBetweenAccountsFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        baskaHesabaHavaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Başka Hesaba Havale seçildi.", Toast.LENGTH_SHORT).show();

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new TransferToOtherFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void copyToClipboard(String metin) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(getContext(), ClipboardManager.class);
        ClipData clipData = ClipData.newPlainText("Hesap Numarası", metin);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(getContext(), "Hesap no kopyalandı", Toast.LENGTH_SHORT).show();
    }
}