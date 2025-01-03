package com.example.mobilebanking.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilebanking.view.AccountViewHolder;
import com.example.mobilebanking.R;
import com.example.mobilebanking.model.Hesap;

import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountViewHolder> {

    Context context;
    public List<Hesap> items;

    public AccountAdapter(Context context, List<Hesap> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AccountViewHolder(LayoutInflater.from(context).inflate(R.layout.accountitem_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        holder.idView.setText("#" + String.valueOf(items.get(position).getHesapNo()));
        holder.nameView.setText(items.get(position).getHesapAdi());
        holder.balanceView.setText(String.valueOf(items.get(position).getHesapBakiye() + items.get(position).getHesapDovizTipi().getName()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
