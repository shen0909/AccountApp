package com.example.accountapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.accountapp.pages.AccountFragment;
import com.example.accountapp.pages.MoneyFragment;
import com.example.accountapp.pages.SaveMoneyFragment;
import com.example.accountapp.pages.SumAllMoney;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private LinearLayout bt_Nv;
    private ImageView zd_icon, zc_icon, cq_icon, tj_icon;

    // 接收来自 Androidsutdio App的广播
    // 创建广播接收器
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, intent.getExtras().getString("data_global"), Toast.LENGTH_LONG).show();
            Log.d("接受全局广播", intent.getExtras().getString("data_global"));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setViewPage();

        // 注册广播接收器
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("send_global_action");
        registerReceiver(broadcastReceiver, intentFilter);
        getContentProviderData();

    }

    // 接收来自其他app的数据
    public void getContentProviderData() {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = Uri.parse("content://com.example.androidstudiostydy.provider/STUTDY_SQLITE_TABLE");

        @SuppressLint("Recycle")
        // 检查 Cursor 是否为空
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor != null) {
            while(cursor.moveToNext()){
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String phone = cursor.getString(2);
                String pwd = cursor.getString(3);
                Log.d("contentprovider", id + "\t" + name + "\t" + phone + "\t" + pwd);
            }
            cursor.close();
        } else {
            Toast.makeText(this, "无法获取内容提供者数据", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 解除广播接收器
        unregisterReceiver(broadcastReceiver);
    }

    private void setViewPage() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new AccountFragment());
        fragmentList.add(new MoneyFragment());
        fragmentList.add(new SaveMoneyFragment());
        fragmentList.add(new SumAllMoney());
        viewPager2.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getItemCount() {
                return fragmentList.size();
            }
        });
        viewPager2.setOffscreenPageLimit(4);

        // 注册 ViewPager2.OnPageChangeCallback 监听器，监听页面切换事件。当页面切换时，调用 changeBottomTab() 方法更新底部导航栏图标的状态
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                //viewPage 选择item，对应的bottonNavigation也切换位序
                changeBottomTab(position);
            }
        });
    }

    private void initView() {
        bt_Nv = findViewById(R.id.bt_Nv);
        viewPager2 = findViewById(R.id.viewPager2);
        zd_icon = findViewById(R.id.zd_icon);
        zc_icon = findViewById(R.id.zc_icon);
        cq_icon = findViewById(R.id.cq_icon);
        tj_icon = findViewById(R.id.tj_icon);
    }

    /// 点击底部导航栏切换页面
    public void changeView(View view) {
        int id = view.getId();
        if (id == R.id.zd_icon) {
            viewPager2.setCurrentItem(0);
        } else if (id == R.id.zc_icon) {
            viewPager2.setCurrentItem(1);
        } else if (id == R.id.cq_icon) {
            viewPager2.setCurrentItem(2);
        } else if (id == R.id.tj_icon) {
            viewPager2.setCurrentItem(3);
        }
    }

    // 切换导航栏效果
    public void changeBottomTab(int position) {
        if (position == 0) {
            zd_icon.setImageResource(R.drawable.zhangdan_select);
            zc_icon.setImageResource(R.drawable.zichan);
            cq_icon.setImageResource(R.drawable.cunqian);
            tj_icon.setImageResource(R.drawable.tongji);
        } else if (position == 1) {
            zd_icon.setImageResource(R.drawable.zhangdan);
            zc_icon.setImageResource(R.drawable.zichan_select);
            cq_icon.setImageResource(R.drawable.cunqian);
            tj_icon.setImageResource(R.drawable.tongji);
        } else if (position == 2) {
            zd_icon.setImageResource(R.drawable.zhangdan);
            zc_icon.setImageResource(R.drawable.zichan);
            cq_icon.setImageResource(R.drawable.cunqian_select);
            tj_icon.setImageResource(R.drawable.tongji);
        } else if (position == 3) {
            zd_icon.setImageResource(R.drawable.zhangdan);
            zc_icon.setImageResource(R.drawable.zichan);
            cq_icon.setImageResource(R.drawable.cunqian);
            tj_icon.setImageResource(R.drawable.tongji_select);
        }

    }
}