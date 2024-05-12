package com.example.mobilebanking.view;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilebanking.R;

public class PersonViewHolder extends RecyclerView.ViewHolder {

    public TextView nameView, accountNumberView;
    public PersonViewHolder(@NonNull View itemView) {
        super(itemView);
        nameView = itemView.findViewById(R.id.nameView);
        accountNumberView = itemView.findViewById(R.id.accountNumberView);

    }
}
