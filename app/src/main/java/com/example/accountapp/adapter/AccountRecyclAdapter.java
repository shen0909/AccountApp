package com.example.accountapp.adapter;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.accountapp.R;
import com.example.accountapp.data.AccountData;
import com.example.accountapp.data.AccountDataItem;

import java.util.List;
import java.util.zip.Inflater;

public class AccountRecyclAdapter extends RecyclerView.Adapter<AccountRecyclAdapter.ViewHolder> {
    List<AccountData> list; //数据
    Context context;

    // 构造函数
    public AccountRecyclAdapter(List<AccountData> list,Context context) {
        this.list = list;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView date_tv; // 日期
        private RecyclerView recyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date_tv = itemView.findViewById(R.id.date_tv);
            recyclerView = itemView.findViewById(R.id.recycle_account_item);
        }
    }
    @NonNull
    @Override
    public AccountRecyclAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountRecyclAdapter.ViewHolder holder, int position) {
        holder.date_tv.setText(list.get(position).getTime());

        LinearLayout linearLayout = new LinearLayout(context);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        AccountListItemRecycle accountListItemRecycle = new AccountListItemRecycle(list.get(position).getAccountList());
        holder.recyclerView.setAdapter(accountListItemRecycle);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
