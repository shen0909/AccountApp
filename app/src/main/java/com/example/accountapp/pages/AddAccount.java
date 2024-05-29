package com.example.accountapp.pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.accountapp.InterfaceCollection;
import com.example.accountapp.R;
import com.example.accountapp.data.Entry.AccountDataItem;
import com.example.accountapp.data.AddIconItemData;
import com.example.accountapp.data.DataRepository;
import com.example.accountapp.fragment.addaccount.AddExchangeFragment;
import com.example.accountapp.fragment.addaccount.AddInFragment;
import com.example.accountapp.fragment.addaccount.AddOutFragment;

// 添加账单Activity
public class AddAccount extends AppCompatActivity implements InterfaceCollection.ChooseIcon {
    private int tabIndex = 1; //当前选中的tab页 1.支出 2.收入 3.转账
    private TextView tab1;
    private TextView tab2;
    private TextView tab3;
    private View divider1,divider2;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        initView();
        // 动态加载 Fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();//开启事务
        fragmentTransaction.add(R.id.icon_frag,new AddOutFragment());
        fragmentTransaction.commit();
    }

    // 初始化view
    private void initView() {
        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
        tab3 = findViewById(R.id.tab3);
        divider1 = findViewById(R.id.divider1);
        divider2 = findViewById(R.id.divider2);
        TextView cancelTxt = findViewById(R.id.cancelTxt);
        cancelTxt.setOnClickListener(view -> finish());
    }

    // 切换 tab页 点击方法
    public void tabChange(View view) {
        int index = view.getId();
        if(index == R.id.tab1){
            System.out.println("支出");
            tabIndex = 1;
            divider1.setVisibility(View.INVISIBLE);
            divider2.setVisibility(View.VISIBLE);
            tab1.setBackgroundResource(R.drawable.selec_tab);
            tab3.setBackgroundResource(0);
            tab2.setBackgroundResource(0);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();//开启事务
            fragmentTransaction.replace(R.id.icon_frag,new AddOutFragment());
            fragmentTransaction.commit();
        }else if(index == R.id.tab2){
            System.out.println("收入");
            tabIndex = 2;
            divider1.setVisibility(View.INVISIBLE);
            divider2.setVisibility(View.INVISIBLE);
            tab2.setBackgroundResource(R.drawable.selec_tab);
            tab1.setBackgroundResource(0);
            tab3.setBackgroundResource(0);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();//开启事务
            fragmentTransaction.replace(R.id.icon_frag,new AddInFragment());
            fragmentTransaction.commit();
        }else{
            System.out.println("转账");
            tabIndex = 3;
            divider1.setVisibility(View.VISIBLE);
            divider2.setVisibility(View.INVISIBLE);
            tab3.setBackgroundResource(R.drawable.selec_tab);
            tab1.setBackgroundResource(0);
            tab2.setBackgroundResource(0);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();//开启事务
            fragmentTransaction.replace(R.id.icon_frag,new AddExchangeFragment());
            fragmentTransaction.commit();
        }
    }

    /// 处理从RecycleView中得到的数据
    public void showChoose(AddIconItemData chooseItem) {
        type = chooseItem.getTitle();
        System.out.println(type);
    }

    public void submit(View view) {
        DataRepository dataRepository = new DataRepository(getApplicationContext());
//        System.out.println("提交类别:"+type+"");
        dataRepository.insert(new AccountDataItem("789","服饰","第一条","2024-5-28",tabIndex));
    }
}