package com.example.mobilebanking.adapter;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilebanking.R;
import com.example.mobilebanking.model.Islem;
import com.example.mobilebanking.view.TransactionViewHolder;

import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<TransactionViewHolder> {

    Context context;
    public List<Islem> items;

    public ActivityAdapter(Context context, List<Islem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransactionViewHolder(LayoutInflater.from(context).inflate(R.layout.item_activity,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        holder.title.setText(String.format("%s", items.get(position).getIslemTipi().getItBaslik()));
        holder.desc.setText("Tarih");
        holder.amount.setText(String.format("%s %s",items.get(position).getIslemMiktar(), items.get(position).getHesap().getHesapDovizTipi().getName()));
        holder.amount.setTextColor(Color.parseColor(items.get(position).getIslemTipi().getItRenk()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
