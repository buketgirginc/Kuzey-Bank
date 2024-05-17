package com.example.mobilebanking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mobilebanking.R;
import com.example.mobilebanking.model.Hesap;

import java.util.List;

public class AccountSpinnerAdapter extends BaseAdapter {

    Context context;
    List<Hesap> items;
    LayoutInflater inflater;

    public AccountSpinnerAdapter(Context context, List<Hesap> items) {
        this.context = context;
        this.items = items;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.accountitem_view, parent, false);
        }

        TextView idView = convertView.findViewById(R.id.accId);
        TextView nameView = convertView.findViewById(R.id.accName);
        TextView balanceView = convertView.findViewById(R.id.accBalance);

        Hesap hesap = items.get(position);

        idView.setText("#" + hesap.getHesapNo());
        nameView.setText(hesap.getHesapAdi());
        balanceView.setText(hesap.getHesapBakiye() + hesap.getHesapDovizTipi().getName());

        return convertView;
    }
}
