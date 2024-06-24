package com.example.accountapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.accountapp.R;
import com.example.accountapp.data.Entry.AccountData;

import java.util.List;
// 帐单列表 RecyclerAdapter
public class AccountRecyclerAdapter extends RecyclerView.Adapter<AccountRecyclerAdapter.ViewHolder> {
    List<AccountData> list; //数据
    Context context;

    // 构造函数
    public AccountRecyclerAdapter(List<AccountData> list, Context context) {
        this.list = list;
        this.context = context;
    }
    public void updateData(List<AccountData> list){
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView date_tv; // 日期
        private TextView in_money,out_money;
        private RecyclerView recyclerView;
        private LinearLayout in_linear,out_linear;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date_tv = itemView.findViewById(R.id.date_tv);
            recyclerView = itemView.findViewById(R.id.recycle_account_item);
            in_linear = itemView.findViewById(R.id.in_linear);
            out_linear = itemView.findViewById(R.id.out_linear);
            in_money = itemView.findViewById(R.id.in_moeny);
            out_money = itemView.findViewById(R.id.out_moeny);
        }
    }
    @NonNull
    @Override
    public AccountRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountRecyclerAdapter.ViewHolder holder, int position) {
        AccountData currentAccountData = list.get(position);
        holder.date_tv.setText(currentAccountData.getCreateDate());
        // -设置列表金额
        if(currentAccountData.getInMoney() != null){
            holder.in_linear.setVisibility(View.VISIBLE);
            holder.in_money.setText(currentAccountData.getInMoney());
        }
        if (currentAccountData.getOutMoney() != null) {
            holder.out_linear.setVisibility(View.VISIBLE);
            holder.out_money.setText(currentAccountData.getOutMoney());
        }
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        AccountListItemRecycle accountListItemRecycle = new AccountListItemRecycle(list.get(position).getAccountList(),context);
        holder.recyclerView.setAdapter(accountListItemRecycle);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);
        holder.recyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
