package com.example.mobilebanking.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.example.mobilebanking.R;
import com.example.mobilebanking.model.Musteri;

public class TransferBetweenAccountsFragment extends Fragment {

    private Musteri musteri;

    public TransferBetweenAccountsFragment(Musteri musteri) {
        this.musteri = musteri;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transfer_between_accounts, container, false);
        Spinner senderSpinner = view.findViewById(R.id.spinnerSenderAccount);
        return view;
    }
}