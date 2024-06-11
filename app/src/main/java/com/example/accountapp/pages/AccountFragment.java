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
    private List<AccountDataItem> accountDataItemList = new ArrayList<>();
    private AccountViewModel accountViewModel;
    private AccountRecyclerAdapter accountRecyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        accountViewModel = new AccountViewModel(getActivity().getApplication());
//        accountViewModel.getListLiveData().observe(getViewLifecycleOwner(), new Observer<List<AccountDataItem>>() {
//            @Override
//            public void onChanged(List<AccountDataItem> accountDataItems) {
//                accountDataItemList = accountDataItems;
//                updateRecycleView();
//                System.out.println("数据更新了"+accountDataItems.size());
//            }
//        });
        // todo：
        accountViewModel.getListLiveData().observe(getViewLifecycleOwner(), new Observer<List<AccountData>>() {
            @Override
            public void onChanged(List<AccountData> accountData) {
                System.out.println("数据更新了"+accountData.size());
                for (int i = 0; i < accountData.size() ; i++) {
                    System.out.println("账单数据"+accountData.get(i).toString());
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
        List<AccountData> list = new ArrayList<>();
        List<AccountDataItem> listItem = new ArrayList<>();

        listItem.add(new AccountDataItem("100","餐饮","买零食","2024.5.14 14:30",1));
        listItem.add(new AccountDataItem("200","餐饮","买零食","2024.5.14 15:30",1));
        listItem.add(new AccountDataItem("300","餐饮","买零食","2024.5.14 16:30",1));

        list.add(new AccountData("110",listItem));
        list.add(new AccountData("330",listItem));
        accountRecyclerAdapter = new AccountRecyclerAdapter(list,getContext());
        recyclerView.setAdapter(accountRecyclerAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
    public void updateRecycleView(){
        List<AccountData> list = new ArrayList<>();
        list.add(new AccountData("22",accountDataItemList));
        list.add(new AccountData("32",accountDataItemList));
        list.add(new AccountData("42",accountDataItemList));
        accountRecyclerAdapter.updateData(list);
        accountRecyclerAdapter.notifyDataSetChanged();
    }
}