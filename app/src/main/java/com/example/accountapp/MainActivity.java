package com.example.accountapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.accountapp.fragment.AccountFragment;
import com.example.accountapp.fragment.MoneyFragment;
import com.example.accountapp.fragment.SaveMoneyFragment;
import com.example.accountapp.fragment.SumAllMoney;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bt_Nv;
    private ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setViewPage();
        setBtNv();

    }

    private void setBtNv() {
        bt_Nv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.bt_zd) {
                    viewPager2.setCurrentItem(0);
                } else if (id == R.id.bt_zc) {
                    viewPager2.setCurrentItem(1);
                } else if (id == R.id.bt_cq) {
                    viewPager2.setCurrentItem(2);
                } else if (id == R.id.bt_tj) {
                    viewPager2.setCurrentItem(3);
                }
                return true;
            }
        });
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
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                //viewPage 选择item，对应的bottonNavigation也切换位序
                btNvonPageSelected(position);
//                bt_Nv.setSelectedItemId(position);
            }
        });
    }

    private void btNvonPageSelected(int position) {
        switch (position) {
            case 0:
                bt_Nv.setSelectedItemId(R.id.bt_zd);
                break;
            case 1:
                bt_Nv.setSelectedItemId(R.id.bt_zc);
                break;
            case 2:
                bt_Nv.setSelectedItemId(R.id.bt_cq);
                break;
            case 3:
                bt_Nv.setSelectedItemId(R.id.bt_tj);
                break;
        }
    }

    private void initView() {
        bt_Nv = findViewById(R.id.bt_Nv);
        viewPager2 = findViewById(R.id.viewPager2);
    }
}