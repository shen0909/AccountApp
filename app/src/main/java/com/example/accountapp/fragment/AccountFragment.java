package com.example.accountapp.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.example.accountapp.R;
import com.example.accountapp.adapter.AccountRecyclAdapter;
import com.example.accountapp.data.AccountData;
import com.example.accountapp.data.AccountDataItem;
import java.util.ArrayList;
import java.util.List;


public class AccountFragment extends Fragment {

    private RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        // 设置适配器
        // 1.设置适配器的4大组成部分
        LinearLayout linearLayout = new LinearLayout(getContext());
        recyclerView = getView().findViewById(R.id.account_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        // 2.准备数据
        List<AccountData> list = new ArrayList<>();
        List<AccountDataItem> listItem = new ArrayList<>();

        listItem.add(new AccountDataItem("100","餐饮","买零食","2024.5.14 14:30",1));
        listItem.add(new AccountDataItem("200","餐饮","买零食","2024.5.14 15:30",1));
        listItem.add(new AccountDataItem("300","餐饮","买零食","2024.5.14 16:30",1));

        list.add(new AccountData("11",listItem));
        list.add(new AccountData("22",listItem));
        list.add(new AccountData("33",listItem));
        list.add(new AccountData("44",listItem));
        list.add(new AccountData("55",listItem));
        AccountRecyclAdapter accountRecyclAdapter = new AccountRecyclAdapter(list,getContext());
        recyclerView.setAdapter(accountRecyclAdapter);
    }
}