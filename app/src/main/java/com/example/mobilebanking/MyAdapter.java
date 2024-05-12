package com.example.mobilebanking;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<AccountItem> items;

    public MyAdapter(Context context, List<AccountItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.accountitem_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.idView.setText(String.valueOf(items.get(position).getAccId()));
        holder.nameView.setText(items.get(position).getAccName());
        holder.balanceView.setText(items.get(position).getBalance());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
