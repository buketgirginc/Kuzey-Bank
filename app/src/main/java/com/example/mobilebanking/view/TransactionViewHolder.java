package com.example.mobilebanking.view;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilebanking.R;

public class TransactionViewHolder extends RecyclerView.ViewHolder {

    public TextView title, desc, amount;
    public TransactionViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.itemTitle);
        desc = itemView.findViewById(R.id.itemDesc);
        amount = itemView.findViewById(R.id.itemAmount);

    }
}
