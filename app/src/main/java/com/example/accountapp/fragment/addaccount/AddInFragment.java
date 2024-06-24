package com.example.accountapp.fragment.addaccount;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.accountapp.InterfaceCollection;
import com.example.accountapp.R;
import com.example.accountapp.adapter.AddAccountIcomRecycleyAdapter;
import com.example.accountapp.data.AddIconItemData;
import com.example.accountapp.data.AllData;
import com.example.accountapp.pages.AddAccount;

import java.util.List;

// 添加账单-收入Fragment
public class AddInFragment extends Fragment implements InterfaceCollection.ChooseIcon{

    @Override
    public void showChoose(AddIconItemData chooseItem) {
        Log.d("收入页点击图标","chooseItem");
        ((AddAccount)requireActivity()).receiveChooseIcon(chooseItem);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = getView().findViewById(R.id.in_icon_list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 5);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });

        recyclerView.setLayoutManager(gridLayoutManager); //设置布局
        AddAccountIcomRecycleyAdapter adapter = new AddAccountIcomRecycleyAdapter(new AllData().getAddIconInDataList(), getContext(), this,1);
        recyclerView.setAdapter(adapter); //设置适配器
    }
}