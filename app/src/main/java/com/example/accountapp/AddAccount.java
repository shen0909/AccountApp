package com.example.accountapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AddAccount extends AppCompatActivity {
    private int tabIndex = 1; //当前选中的tab页
    private TextView tab1,tab2,tab3,cancelTxt;
    private View divider1,divider2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        initView();
    }

    // 初始化view
    private void initView() {
        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
        tab3 = findViewById(R.id.tab3);
        divider1 = findViewById(R.id.divider1);
        divider2 = findViewById(R.id.divider2);
        cancelTxt = findViewById(R.id.cancelTxt);
        cancelTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // 切换 tab页 点击方法
    public void tabChange(View view) {
        int index = view.getId();
        if(index == R.id.tab1){
            System.out.println("支出");
            divider1.setVisibility(View.INVISIBLE);
            divider2.setVisibility(View.VISIBLE);
            tab1.setBackgroundResource(R.drawable.selec_tab);
            tab3.setBackgroundResource(0);
            tab2.setBackgroundResource(0);
        }else if(index == R.id.tab2){
            System.out.println("收入");
            divider1.setVisibility(View.INVISIBLE);
            divider2.setVisibility(View.INVISIBLE);
            tab2.setBackgroundResource(R.drawable.selec_tab);
            tab1.setBackgroundResource(0);
            tab3.setBackgroundResource(0);
        }else{
            System.out.println("转账");
            divider1.setVisibility(View.VISIBLE);
            divider2.setVisibility(View.INVISIBLE);
            tab3.setBackgroundResource(R.drawable.selec_tab);
            tab1.setBackgroundResource(0);
            tab2.setBackgroundResource(0);
        }
    }
}