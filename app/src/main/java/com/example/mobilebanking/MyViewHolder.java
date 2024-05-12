package com.example.mobilebanking;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView idView, nameView, balanceView;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        idView = itemView.findViewById(R.id.accId);
        nameView = itemView.findViewById(R.id.accName);
        balanceView = itemView.findViewById(R.id.accBalance);

    }
}
