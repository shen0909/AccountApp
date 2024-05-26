package com.example.accountapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.accountapp.InterfaceCollection;
import com.example.accountapp.R;
import com.example.accountapp.data.AddIconItemData;
import java.util.List;

// 添加账单的图标 RecyclerView的Adapter
public class AddAccountIcomRecycleyAdapter extends RecyclerView.Adapter<AddAccountIcomRecycleyAdapter.ViewHolder> {

    private final List<AddIconItemData> addIconItemDataList;
    private final Context context;
    private int selecPosition = 0; //当前选中的位序
    private final InterfaceCollection.ChooseIcon chooseIcon;

    public AddAccountIcomRecycleyAdapter(List<AddIconItemData> addIconItemDataList, Context context,InterfaceCollection.ChooseIcon chooseIcon) {
        this.addIconItemDataList = addIconItemDataList;
        this.context = context;
        this.chooseIcon = chooseIcon;
    }

    @NonNull
    @Override
    public AddAccountIcomRecycleyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_icon_recycle_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.icon.setOnClickListener(view1 -> {
            selecPosition = holder.getAdapterPosition(); // 传出点击位置
            chooseIcon.showChoose(addIconItemDataList.get(selecPosition));
            notifyDataSetChanged();
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddAccountIcomRecycleyAdapter.ViewHolder holder, int position) {

        holder.icon_title.setText(addIconItemDataList.get(position).getTitle());

        // 当前是否被选中
        if (position == selecPosition) {
            holder.icon.setImageResource(addIconItemDataList.get(position).getIconPath());
            holder.card_icon.setCardBackgroundColor(ContextCompat.getColor(context, R.color.money_green));
        } else {
            holder.icon.setImageResource(addIconItemDataList.get(position).getUnselectIconPath());
            holder.card_icon.setCardBackgroundColor(ContextCompat.getColor(context, R.color.back_grey));
        }
    }

    @Override
    public int getItemCount() {
        return addIconItemDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView icon, more_icon;
        private final TextView icon_title;
        private final CardView card_icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            more_icon = itemView.findViewById(R.id.more_icon);
            icon_title = itemView.findViewById(R.id.icon_title);
            card_icon = itemView.findViewById(R.id.card_icon);
        }
    }
}
