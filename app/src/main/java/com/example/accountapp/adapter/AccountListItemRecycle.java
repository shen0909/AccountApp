package com.example.accountapp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.accountapp.R;
import com.example.accountapp.data.Entry.AccountDataItem;
import com.example.accountapp.utils.CommonTool;
import java.util.List;

public class AccountListItemRecycle extends RecyclerView.Adapter<AccountListItemRecycle.ViewHolder> {

    private List<AccountDataItem> accountDataItemList;
    private CommonTool commonTool = new CommonTool();

    public AccountListItemRecycle(List<AccountDataItem> accountDataItemList) {
        this.accountDataItemList = accountDataItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AccountDataItem accountDataItem = accountDataItemList.get(position);
        Log.d("账单显示页数据", "" + accountDataItem + "\t时间" + accountDataItem.getData());
        holder.detailMoney.setText(accountDataItem.getMoney());
        holder.type_tv.setText(accountDataItem.getType());
        holder.detail.setText(accountDataItem.getDetail());
        String detial_time = commonTool.dealDate(accountDataItem.getData(), 2);
        holder.detial_time.setText(detial_time);
        if (!accountDataItem.getDetail().isEmpty()){
            holder.timeDived.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        // 检查列表是否为 null
        if (accountDataItemList == null) {
            return 0;  // 如果列表为 null，则返回 0，表示没有项目要显示
        }
        return accountDataItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView detailMoney, type_tv, detail, detial_time, timeDived;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            detailMoney = itemView.findViewById(R.id.detial_money);
            type_tv = itemView.findViewById(R.id.head_name);
            detail = itemView.findViewById(R.id.detial);
            detial_time = itemView.findViewById(R.id.detial_time);
            timeDived = itemView.findViewById(R.id.timeDived);
        }
    }
}
