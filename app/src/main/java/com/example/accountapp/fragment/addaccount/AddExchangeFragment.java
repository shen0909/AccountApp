package com.example.accountapp.fragment.addaccount;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.accountapp.R;

// 添加账单-转账Fragment
public class AddExchangeFragment extends Fragment implements View.OnClickListener {
    private LinearLayout linearLayout_out, linearLayout_in;
    private ViewGroup parentLayout;
    private ImageView exchange_icon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_exchange, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    public void initView() {
        linearLayout_out = getView().findViewById(R.id.exchange_out_layout);
        linearLayout_in = getView().findViewById(R.id.exchange_in_layout);
        parentLayout = (ViewGroup) linearLayout_out.getParent(); // 得到父容器
        exchange_icon = getView().findViewById(R.id.exchange_icon);
        exchange_icon.setOnClickListener(this);
        linearLayout_out.setOnClickListener(this);
        linearLayout_in.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.exchange_icon){
            changeAccount();
        } else if (id == R.id.exchange_out_layout) {
            System.out.println("点击了转账");

        } else if (id == R.id.exchange_in_layout) {
            System.out.println("点击了转出账户");
        }
    }

    public void changeAccount(){
        if (parentLayout != null && linearLayout_in != null && linearLayout_out != null) {
            int index1 = parentLayout.indexOfChild(linearLayout_out);
            int index2 = parentLayout.indexOfChild(linearLayout_in);
            parentLayout.removeViewInLayout(linearLayout_out);
            parentLayout.removeViewInLayout(linearLayout_in);

            if (index1 < index2) {
                parentLayout.addView(linearLayout_in, index1);
                parentLayout.addView(linearLayout_out, index2);
            } else {
                parentLayout.addView(linearLayout_out, index2);
                parentLayout.addView(linearLayout_in, index1);
            }
            parentLayout.requestLayout();
        }
    }
}