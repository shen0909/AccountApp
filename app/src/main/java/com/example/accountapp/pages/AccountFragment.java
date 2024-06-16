package com.example.accountapp.pages;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.accountapp.R;
import com.example.accountapp.adapter.AccountRecyclerAdapter;
import com.example.accountapp.data.Model.AccountViewModel;
import com.example.accountapp.data.AccountData;
import com.example.accountapp.data.Entry.AccountDataItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

// 账单 Fragment
public class AccountFragment extends Fragment {
    private List<AccountDataItem> currentItemList = new ArrayList<>();
    private List<AccountData> currentDataList = new ArrayList<>();
    private AccountViewModel accountViewModel;
    private AccountRecyclerAdapter accountRecyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        accountViewModel = new AccountViewModel(getActivity().getApplication(), this);
        accountViewModel.getListLiveData().observe(getViewLifecycleOwner(), new Observer<List<AccountData>>() {
            @Override
            public void onChanged(List<AccountData> accountData) {
                // 遍历，每一个列表，获取所有的适配的列表项列表
                for (int i = 0; i < accountData.size(); i++) {
                    int id = accountData.get(i).getId();
                    List<AccountDataItem> accountDataItemList1 = accountViewModel.getDataByForId(id);

                    // 为什么这里没有符合条件的列表项呢
                    if (accountDataItemList1.isEmpty() || accountDataItemList1 == null) {
                        Log.e("监听是否有符合条件的列表项", "没有");
                    }
                    // 有外键 = id的，添加到
                    else {
                        for (int j = 0; j < accountDataItemList1.size(); j++) {
                            currentItemList.add(accountDataItemList1.get(j));
                            Log.e("有符合条件的列表项", accountDataItemList1.get(j).toString());

                        }
                        accountData.get(i).setAccountList(currentItemList);
                    }
                }
                // 将获取到的值赋值给变量
                currentDataList = accountData;
                updateRecycleView();
            }
        });
        accountViewModel.getAccountItemLiveData().observe(getViewLifecycleOwner(), new Observer<List<AccountDataItem>>() {
            @Override
            public void onChanged(List<AccountDataItem> accountDataItems) {
                Log.d("数据更新", "账单列表项更新了,列表长度" + (currentDataList.size() + 1));
                List<AccountDataItem> combine = new ArrayList<>();
                for (int i = 0; i < currentDataList.size(); i++) {
                    int list_id = currentDataList.get(i).getId();
                    for (int j = 0; j < accountDataItems.size(); j++) {
                        Log.d("打印账单列表项", accountDataItems.get(j).toString());
                        if (list_id == accountDataItems.get(i).getOutList_id()) {
                            combine.add(accountDataItems.get(i));
                        }
                    }
                    currentDataList.get(i).setAccountList(combine);
                    updateRecycleView();
                }
            }
        });
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        initRecycleView();
        FloatingActionButton floatingActionButton = getView().findViewById(R.id.floatActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AddAccount.class));
            }
        });
    }

    private void initRecycleView() {
        // 设置适配器
        // 1.设置适配器的4大组成部分
        RecyclerView recyclerView = getView().findViewById(R.id.account_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        // 2.准备数据
        accountRecyclerAdapter = new AccountRecyclerAdapter(currentDataList, getContext());
        recyclerView.setAdapter(accountRecyclerAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    public void updateRecycleView() {
        if (currentDataList != null) {
            accountRecyclerAdapter.updateData(currentDataList);
            accountRecyclerAdapter.notifyDataSetChanged();
        }
    }
}