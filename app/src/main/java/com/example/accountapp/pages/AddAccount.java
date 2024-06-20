package com.example.accountapp.pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.accountapp.R;
import com.example.accountapp.data.Entry.AccountDataItem;
import com.example.accountapp.data.AddIconItemData;
import com.example.accountapp.data.Model.AccountViewModel;
import com.example.accountapp.fragment.addaccount.AddExchangeFragment;
import com.example.accountapp.fragment.addaccount.AddInFragment;
import com.example.accountapp.fragment.addaccount.AddOutFragment;
import com.example.accountapp.utils.CommonTool;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

// 添加账单 Activity
public class AddAccount extends AppCompatActivity {
    private int tabIndex = 2; //当前选中的tab页 1.收入 2.支出 3.转账
    private TextView tab1, tab2, tab3;
    private TextView money, finish, save_continue, node, reduce_chu, add_x;
    private LinearLayout clear;
    private EditText remark_txt;
    private View divider1, divider2;
    private String type = "餐饮";
    private final List<TextView> num_list = new ArrayList<>();
    private Boolean isAdd = false; // 初始是加号
    private Boolean isReduce = false;
    private AccountViewModel accountViewModel;
    private String selectDateTime = new Date(System.currentTimeMillis()).toString() ; // 选中的时间和日期 - 默认当前


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        initView();
        // 动态加载 Fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();//开启事务
        fragmentTransaction.add(R.id.icon_frag, new AddOutFragment());
        fragmentTransaction.commit();
        accountViewModel = new AccountViewModel(getApplication(),this);
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

        for (int i = 0; i <= 9; i++) {
            int textViewId = getResources().getIdentifier("num_" + i, "id", getPackageName());
            TextView textView = findViewById(textViewId);
            num_list.add(textView);
        }
        money = findViewById(R.id.money);
        finish = findViewById(R.id.finish);
        save_continue = findViewById(R.id.save_continue);
        remark_txt = findViewById(R.id.remark_txt);
        node = findViewById(R.id.node);
        clear = findViewById(R.id.clear);
        add_x = findViewById(R.id.add_x);
        reduce_chu = findViewById(R.id.reduce_chu);
    }

    // 切换 tab页 点击方法
    public void tabChange(View view) {
        int index = view.getId();
        if (index == R.id.tab1) {
            System.out.println("支出");
            tabIndex = 2;
            divider1.setVisibility(View.INVISIBLE);
            divider2.setVisibility(View.VISIBLE);
            tab1.setBackgroundResource(R.drawable.selec_tab);
            tab3.setBackgroundResource(0);
            tab2.setBackgroundResource(0);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();//开启事务
            fragmentTransaction.replace(R.id.icon_frag, new AddOutFragment());
            fragmentTransaction.commit();
        } else if (index == R.id.tab2) {
            System.out.println("收入");
            tabIndex = 1;
            divider1.setVisibility(View.INVISIBLE);
            divider2.setVisibility(View.INVISIBLE);
            tab2.setBackgroundResource(R.drawable.selec_tab);
            tab1.setBackgroundResource(0);
            tab3.setBackgroundResource(0);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();//开启事务
            fragmentTransaction.replace(R.id.icon_frag, new AddInFragment());
            fragmentTransaction.commit();
        } else {
            System.out.println("转账");
            tabIndex = 3;
            divider1.setVisibility(View.VISIBLE);
            divider2.setVisibility(View.INVISIBLE);
            tab3.setBackgroundResource(R.drawable.selec_tab);
            tab1.setBackgroundResource(0);
            tab2.setBackgroundResource(0);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();//开启事务
            fragmentTransaction.replace(R.id.icon_frag, new AddExchangeFragment());
            fragmentTransaction.commit();
        }
    }

    // money == 0.00 归零  不等于 0.00则相加
    public void keyboardClick(View view) {
        int id = view.getId();
        String text = (String) money.getText();
        if (Objects.equals(text, "0.00") || Objects.equals(text, "0")) {
            money.setText("");
        }
        if (id == num_list.get(0).getId()) {
            setMoney((String) num_list.get(0).getText());
        } else if (id == num_list.get(1).getId()) {
            setMoney((String) num_list.get(1).getText());
        } else if (id == num_list.get(2).getId()) {
            setMoney((String) num_list.get(2).getText());
        } else if (id == num_list.get(3).getId()) {
            setMoney((String) num_list.get(3).getText());
        } else if (id == num_list.get(4).getId()) {
            setMoney((String) num_list.get(4).getText());
        } else if (id == num_list.get(5).getId()) {
            setMoney((String) num_list.get(5).getText());
        } else if (id == num_list.get(6).getId()) {
            setMoney((String) num_list.get(6).getText());
        } else if (id == num_list.get(7).getId()) {
            setMoney((String) num_list.get(7).getText());
        } else if (id == num_list.get(8).getId()) {
            setMoney((String) num_list.get(8).getText());
        } else if (id == num_list.get(9).getId()) {
            setMoney((String) num_list.get(9).getText());
        } else if (id == node.getId()) {
            setMoney(node.getText().toString());
        } else if (id == clear.getId()) {
            String newMoney = cancelEndChar(money.getText().toString());
            if (newMoney.isEmpty()) {
                money.setText("0.00");
            } else {
                money.setText(newMoney);
            }
        }
        // 保存再记操作
        else if (id == finish.getId()) {
            submit(selectDateTime);
            finish();
        } else if (id == save_continue.getId()) {
            saveAndContinue();
        }
    }
    public void saveAndContinue(){
        submit(selectDateTime);
        money.setText("0.00");
    }

    /// 点击数字键盘修改金钱
    public void setMoney(String addMoney) {
        money.setText(money.getText() + addMoney);
    }

    // todo:如何才能在连续点两次的情况下只输入一次？连续点两次加，会添加×
    public void calculateShow(View view) {
        int id = view.getId();
        char endChar = money.getText().charAt(money.getText().length() - 1);
        String newMoney = money.getText().toString();

        /// 如果最后一位是运算符，则修改成相反运算符，否则加上运算符
        // 1.第一次点击此方法或者前面是以为是数字，运算符都该是初始状态，如何判断第一次？newMoeny的值是否有变化
        /* 有变化：说明不是第一次，说明前面有运算符且点击了运算符
         * 没有变化说明是第一次，运算符状态初始化*/
        if (endChar == '+' || endChar == '×' || endChar == '-' || endChar == '÷') {
            newMoney = cancelEndChar(money.getText().toString());
            System.out.println("最后一位是运算符");
        } else if (newMoney == money.getText()) {
            System.out.println("第一次，或者前面一位是数字");
            isAdd = false;
            isReduce = false;
        }
        if (id == add_x.getId()) {
            isAdd = !isAdd;
            money.setText(newMoney + (isAdd ? "+" : "×"));
        } else if (id == reduce_chu.getId()) {
            isReduce = !isReduce;
            money.setText(newMoney + (isReduce ? "-" : "÷"));
        }
    }

    /// 删除最后一个字符
    public String cancelEndChar(String string) {
        int length = string.length() - 1;
        return string.subSequence(0, length).toString();
    }

    /// 接受 ReycleView选择的icon
    public void receiveChooseIcon(AddIconItemData chooseItem) {
        type = chooseItem.getTitle();

    }

    // 提交账单
    public void submit(String submitDateTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat_list = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String create_date_item = simpleDateFormat.format(date);

        AccountDataItem accountDataItem = new AccountDataItem();
        accountDataItem.setType(type);
        accountDataItem.setMoney(money.getText().toString());
        accountDataItem.setDetail(remark_txt.getText().toString());
        accountDataItem.setData(create_date_item);
        accountDataItem.setIn(tabIndex);
        accountViewModel.insertAccountItem(accountDataItem,this);

    }
}