package com.example.accountapp.pages;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.example.accountapp.data.Entry.AccountData;
import com.example.accountapp.data.Entry.AccountDataItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

// 账单 Fragment
public class AccountFragment extends Fragment {
    private List<AccountData> currentDataList = new ArrayList<>();
    private AccountViewModel accountViewModel;
    private AccountRecyclerAdapter accountRecyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        accountViewModel = new AccountViewModel(getActivity().getApplication());
        return inflater.inflate(R.layout.fragment_account, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        accountViewModel.getListLiveData().observe(getViewLifecycleOwner(), accountData -> updateDataList(accountData,accountViewModel.backItemList().getValue()));
        accountViewModel.backItemList().observe(getViewLifecycleOwner(), accountDataItems -> {
            updateDataList(accountViewModel.getListLiveData().getValue(), accountDataItems);
        });
    }

    /// 传入最近的账单列表和列表项列表，取出合适的放入
    private void updateDataList(List<AccountData> accountData, List<AccountDataItem> accountDataItems) {
        if (accountData == null || accountDataItems == null) {
            return;
        }
        currentDataList = null;
        Log.d("数据更新", "账单列表项更新了,长度="+accountDataItems.size()+"\t列表长度" + accountData.size());
        for (int i = 0; i < accountData.size(); i++) {
            List<AccountDataItem> combine = new ArrayList<>();
            int list_id = accountData.get(i).getId();
            for (int j = 0; j < accountDataItems.size(); j++) {
                if (list_id == accountDataItems.get(j).getOutList_id()) {
                    combine.add(accountDataItems.get(j));
                }
            }
            accountData.get(i).setAccountList(combine);
        }
        currentDataList = accountData;
        updateRecycleView();
    }

    private void initView() {
        initRecycleView();
        FloatingActionButton floatingActionButton = getView().findViewById(R.id.floatActionButton);
        floatingActionButton.setOnClickListener(view -> startActivity(new Intent(getContext(), AddAccount.class)));
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