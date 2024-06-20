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
    private List<AccountData> currentDataList = new ArrayList<>();
    private AccountViewModel accountViewModel;
    private AccountRecyclerAdapter accountRecyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        accountViewModel = new AccountViewModel(getActivity().getApplication(), this);
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

        // 观察 ViewModel中已组合好的数据
        accountViewModel.getCombineList().observe(getViewLifecycleOwner(), new Observer<List<AccountData>>() {
            @Override
            public void onChanged(List<AccountData> accountData) {
                Log.e("监听组合数据",""+accountData.size());
                currentDataList = null;
                for (int i = 0; i < accountData.size(); i++) {
                    Log.e("监听组合数据第"+(i+1)+"项",""+accountData.toString());
                    List<AccountDataItem> a = accountData.get(i).getAccountList();
                    for (int j = 0; j < a.size(); j++) {
                        Log.e("监听组合数据第"+(i+1)+"项的第"+(j+1)+"项tiem",""+a.get(j).toString());
                    }
                }
                currentDataList = accountData;
                updateRecycleView();
            }
        });
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