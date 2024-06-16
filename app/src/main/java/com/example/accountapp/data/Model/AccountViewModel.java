package com.example.accountapp.data.Model;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import com.example.accountapp.data.AccountData;
import com.example.accountapp.data.Repository.DataRepository;
import com.example.accountapp.data.Entry.AccountDataItem;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountViewModel extends AndroidViewModel {
    private final DataRepository dataRepository;
    private LiveData<List<AccountDataItem>> accountItemLiveData;
    private LiveData<List<AccountData>> listLiveData;
    private LifecycleOwner lifecycleOwner;
    private List<AccountDataItem> accountDataItemList;


    public AccountViewModel(@NonNull Application application, LifecycleOwner lifecycleOwner) {
        super(application);
        dataRepository = new DataRepository(application);
        accountItemLiveData = dataRepository.getData();
        listLiveData = dataRepository.getListLiveData();
        this.lifecycleOwner = lifecycleOwner;
    }

    /// 获取账单列表数据 -- LiveData
    public LiveData<List<AccountData>> getListLiveData() {
        return listLiveData;
    }

    /// 根据外键获取合适的列表
    public List<AccountDataItem> getDataByForId(int id) {
        List<AccountDataItem> resultItemList = new ArrayList<>();
        accountItemLiveData.observe(lifecycleOwner, new Observer<List<AccountDataItem>>() {
            @Override
            public void onChanged(List<AccountDataItem> accountDataItems) {
                for (int i = 0; i < accountDataItems.size(); i++) {
                    AccountDataItem accountDataItem = accountDataItems.get(i);
                    if (accountDataItem.getOutList_id() == id) {
                        resultItemList.add(accountDataItem);
                    }
                }
                accountDataItemList = resultItemList;
            }
        });
        return resultItemList;
    }

    /// 获取账单item数据
    public LiveData<List<AccountDataItem>> getAccountItemLiveData() {
        return accountItemLiveData;
    }

    /// 插入账单列表数据
    public void insertList(AccountData accountData) {
        dataRepository.insertList(accountData);
    }

    public String dealDate(String dealDate) {
        // 设置日期
        SimpleDateFormat fullDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = fullDateFormat.parse(dealDate);
            return dateFormat.format(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /// 创建一个新的列表
    private void createNewAccount(AccountDataItem accountDataItem, int length) {
        AccountData newAccountData = new AccountData();
        newAccountData.setId(length);
        newAccountData.setCreateDate(dealDate(accountDataItem.getData()));
        // 设置金额 1-收入 2-支出
        if (accountDataItem.getIn() == 1) {
            newAccountData.setInMoney(accountDataItem.getMoney());
        } else {
            newAccountData.setOutMoney(accountDataItem.getMoney());
        }
        Log.e("新建数据", "列表:" + newAccountData.toString() + "\t列表项:" + accountDataItem.toString());
        accountDataItem.setOutList_id(length);
        Log.d(TAG, "添加的Item账单数据agin: " + accountDataItem.toString());
        insertList(newAccountData);
        dataRepository.insertAccountItem(accountDataItem);
    }

    /// 插入账单item数据
    public void insertAccountItem(AccountDataItem accountDataItem, LifecycleOwner lifecycleOwner) {
        Log.d(TAG, "添加的Item账单数据: " + accountDataItem.toString());
        getListLiveData().observe(lifecycleOwner, new Observer<List<AccountData>>() {
            @Override
            public void onChanged(List<AccountData> accountData) {
                //todo:当前没有列表数据，创建一个列表数据，并将列表数据的主键赋值给item的外键
                if (accountData.size() <= 0) {
                    Log.e("新建数据", "当前没有任何列表数据，需要新建列表");
                    createNewAccount(accountDataItem, 1);
                }
                // todo:此时有列表数据，比对日期是否相等，相等则将列表主键赋值给item的外键，不相等则新建一条主列表
                else {
                    for (int i = 0; i < accountData.size(); i++) {
                        if (accountDataItem.getData().contains(accountData.get(i).getCreateDate())) {
                            Log.e("当前有列表数据", "日期相等：列表日期-" + accountData.get(i).getCreateDate() + "\t列表项日期-" + dealDate(accountDataItem.getData()));
                            Log.d(TAG, "添加的Item账单数据agin: " + accountDataItem.toString());
                            accountDataItem.setOutList_id(accountData.get(i).getId());
                            dataRepository.insertAccountItem(accountDataItem);
                        } else {
                            Log.e("tixing", "日期不相等：列表日期-" + accountData.get(i).getCreateDate() + "\t列表项日期-" + dealDate(accountDataItem.getData()));
                            // 此时日期不相等，说明当前列表不存在，所以需要新建
                            createNewAccount(accountDataItem, accountData.size() + 1);
                        }
                    }
                }
            }
        });

    }

    public void deleteAllList() {
        dataRepository.deleteAllAccountItem();
        dataRepository.deleteAllAccountList();
    }

}
