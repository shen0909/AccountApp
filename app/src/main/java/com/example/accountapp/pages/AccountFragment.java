package com.example.accountapp.pages;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.example.accountapp.R;
import com.example.accountapp.adapter.AccountRecyclerAdapter;
import com.example.accountapp.data.Model.AccountViewModel;
import com.example.accountapp.data.Entry.AccountData;
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
        // 使用 ViewModelProvider 来确保 ViewModel 的生命周期与 Fragment 相关联
        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        return inflater.inflate(R.layout.fragment_account, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        accountViewModel.getCombinedAccountData().observe(getViewLifecycleOwner(), new Observer<List<AccountData>>() {
            @Override
            public void onChanged(List<AccountData> accountData) {
                Log.e("合并多个LiveData","fragment 检查到了");
                currentDataList = accountData;
                updateRecycleView();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("销毁","销毁AccountFragment");
        // 清理 Glide 加载的图片资源
        Glide.get(requireContext()).clearMemory(); // 清理 Glide 内存缓存
        new Thread(() -> {
            Glide.get(requireContext()).clearDiskCache(); // 清理 Glide 磁盘缓存
        }).start();
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
//            accountRecyclerAdapter.notifyDataSetChanged();
        }
    }
}