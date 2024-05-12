package com.example.mobilebanking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mobilebanking.model.Hesap;

public class PartialMainFragment extends Fragment {


    private Hesap hesap;

    public PartialMainFragment(Hesap hesap) {
        // Required empty public constructor
        this.hesap = hesap;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_partial_main, container, false);
        TextView bakiye = view.findViewById(R.id.bakiye);
        TextView hesapNo = view.findViewById(R.id.hesapNo);
        TextView isim = view.findViewById(R.id.isim);

        bakiye.setText(hesap.getHesapBakiye() + hesap.getHesapDovizTipi().getName());
        hesapNo.setText(String.valueOf(hesap.getHesapNo()));
        isim.setText(hesap.getHesapAdi());

        return view;

    }
}