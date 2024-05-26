package com.example.accountapp.fragment.addaccount;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.accountapp.R;
import com.example.accountapp.adapter.AddAccountIcomRecycleyAdapter;
import com.example.accountapp.data.AddIconItemData;
import com.example.accountapp.data.AllData;
import java.util.List;

// 添加账单-支出Fragment
public class AddOutFragment extends Fragment {
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_out, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.out_icon_list);
        gridLayoutManager = new GridLayoutManager(getContext(),5);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager); //设置布局
        List<AddIconItemData> addIconItemDataList = new AllData().getAddIconItemDataList();
        System.out.println(addIconItemDataList.size());

        AddAccountIcomRecycleyAdapter adapter = new AddAccountIcomRecycleyAdapter(new AllData().getAddIconItemDataList(),getContext());
        recyclerView.setAdapter(adapter); //设置适配器
    }
}