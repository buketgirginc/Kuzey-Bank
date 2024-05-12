package com.example.mobilebanking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class AccountsFragment extends Fragment {

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

        RecyclerView recyclerView = view.findViewById(R.id.accountsRecyclerView);

        List<AccountItem> items = new ArrayList<AccountItem>();
        items.add(new AccountItem(1,"Buket","100"));


        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(new MyAdapter(this.getContext(), items));


        return view;
    }
}