package com.example.accountapp.CallBck;

import android.util.Log;
import androidx.recyclerview.widget.DiffUtil;
import com.example.accountapp.data.Entry.AccountData;
import java.util.List;

public class AccountDataDiffCallback extends DiffUtil.Callback {

    private List<AccountData> oldList;
    private List<AccountData> newList;

    public AccountDataDiffCallback(List<AccountData> oldList, List<AccountData> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        // 比较两个数据项是否相同
        // 为true时，则判断他们的内容是否相同
        boolean isSame = oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
        Log.d("diffUitl","两个数据项是否为同一个？"+"--"+isSame+"\nold--"+oldList.get(oldItemPosition).toString()+",\tnew--"+newList.get(newItemPosition).toString());
        return isSame;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        // 比较两个数据项的内容是否相同
        boolean isSame = oldList.get(oldItemPosition).equals(newList.get(newItemPosition));

        Log.d("diffUitl","两个数据项是否内容相同？"+"--"+isSame+"\nold--"+oldList.get(oldItemPosition).toString()+",\tnew--"+newList.get(newItemPosition).toString());
        return isSame;
    }
}
