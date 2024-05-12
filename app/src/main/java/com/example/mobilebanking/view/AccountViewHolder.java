package com.example.mobilebanking.view;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilebanking.R;

public class AccountViewHolder extends RecyclerView.ViewHolder {

    public TextView idView, nameView, balanceView;
    public AccountViewHolder(@NonNull View itemView) {
        super(itemView);
        idView = itemView.findViewById(R.id.accId);
        nameView = itemView.findViewById(R.id.accName);
        balanceView = itemView.findViewById(R.id.accBalance);

    }
}
