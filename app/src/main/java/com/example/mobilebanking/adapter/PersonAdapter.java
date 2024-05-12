package com.example.mobilebanking.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilebanking.R;
import com.example.mobilebanking.model.Alici;
import com.example.mobilebanking.view.PersonViewHolder;

import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonViewHolder> {

    Context context;
    public List<Alici> items;

    public PersonAdapter(Context context, List<Alici> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PersonViewHolder(LayoutInflater.from(context).inflate(R.layout.personitem_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        holder.nameView.setText(items.get(position).getAliciAdi());
        holder.accountNumberView.setText("#" + String.valueOf(items.get(position).getAliciHesapNo()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
