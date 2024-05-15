package com.example.accountapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.accountapp.R;
import com.example.accountapp.data.AccountDataItem;

import java.util.List;

public class AccountListItemRecycle extends RecyclerView.Adapter<AccountListItemRecycle.ViewHolder> {

    private List<AccountDataItem> accountDataItemList;

    public AccountListItemRecycle(List<AccountDataItem> accountDataItemList) {
        this.accountDataItemList = accountDataItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.detailMoney.setText(accountDataItemList.get(position).getMoney());
    }

    @Override
    public int getItemCount() {
        return accountDataItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView detailMoney;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            detailMoney = itemView.findViewById(R.id.detial_money);
        }
    }
}
