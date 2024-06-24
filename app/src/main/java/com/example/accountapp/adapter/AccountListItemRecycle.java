package com.example.accountapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.accountapp.R;
import com.example.accountapp.data.Entry.AccountDataItem;
import com.example.accountapp.utils.CommonTool;
import java.util.List;

public class AccountListItemRecycle extends RecyclerView.Adapter<AccountListItemRecycle.ViewHolder> {

    private Context context;
    private List<AccountDataItem> accountDataItemList;
    private CommonTool commonTool = new CommonTool();
    private int showColor;

    public AccountListItemRecycle(List<AccountDataItem> accountDataItemList, Context context) {
        this.accountDataItemList = accountDataItemList;
        this.context = context;
        this.showColor = ContextCompat.getColor(context, R.color.money_green);
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
        if (!accountDataItem.getDetail().isEmpty()) {
            holder.timeDived.setVisibility(View.VISIBLE);
        }
        if (accountDataItem.getIn() == 1) {
            showColor = ContextCompat.getColor(context, R.color.money_red);
        }else if(accountDataItem.getIn() == 2){
            showColor = ContextCompat.getColor(context, R.color.money_green);
        }

        if (accountDataItem.getImageByte() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(accountDataItem.getImageByte(), 0, accountDataItem.getImageByte().length);
            // 使用 Glide 加载图片
            Glide.with(context).load(bitmap).into(holder.item_icon);
        } else {
            // 处理图片为空的情况，例如显示默认图片
             Glide.with(context).load(R.drawable.canyin).into(holder.item_icon);
        }
        holder.detailMoney.setText(accountDataItem.getMoney());
        holder.type_tv.setText(accountDataItem.getType());
        holder.detail.setText(accountDataItem.getDetail());
        holder.detial_time.setText(commonTool.dealDate(accountDataItem.getData(), 2));
        holder.accountItemIcon.setCardBackgroundColor(showColor);
        holder.detailMoney.setTextColor(showColor);
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
        private CardView accountItemIcon;
        private ImageView item_icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            detailMoney = itemView.findViewById(R.id.detial_money);
            type_tv = itemView.findViewById(R.id.head_name);
            detail = itemView.findViewById(R.id.detial);
            detial_time = itemView.findViewById(R.id.detial_time);
            timeDived = itemView.findViewById(R.id.timeDived);
            accountItemIcon = itemView.findViewById(R.id.accountItemIcon);
            item_icon = itemView.findViewById(R.id.item_icon);
        }
    }
}
