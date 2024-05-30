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
import com.example.accountapp.data.AccountData;
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
        private RecyclerView recyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date_tv = itemView.findViewById(R.id.date_tv);
            recyclerView = itemView.findViewById(R.id.recycle_account_item);
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
        holder.date_tv.setText(list.get(position).getCreateDate());

        LinearLayout linearLayout = new LinearLayout(context);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        AccountListItemRecycle accountListItemRecycle = new AccountListItemRecycle(list.get(position).getAccountList());
        holder.recyclerView.setAdapter(accountListItemRecycle);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);
        holder.recyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
