package com.example.accountapp.data.Model;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import com.example.accountapp.data.Entry.AccountData;
import com.example.accountapp.data.Repository.DataRepository;
import com.example.accountapp.data.Entry.AccountDataItem;
import com.example.accountapp.utils.CommonTool;
import java.util.ArrayList;
import java.util.List;

public class AccountViewModel extends AndroidViewModel {
    private final DataRepository dataRepository;
    private MediatorLiveData<List<AccountData>> combineData = new MediatorLiveData<>();
    private final CommonTool commonTool = new CommonTool();

    public AccountViewModel(@NonNull Application application) {
        super(application);
        dataRepository = new DataRepository(application);
        dealAccountData();
    }

    // 处理数据
    private void dealAccountData(){
        LiveData<List<AccountData>> accountData = dataRepository.getAccountList();
        LiveData<List<AccountDataItem>> accountDataItems = dataRepository.getAccoutnItemList();
        combineData.addSource(accountData, new Observer<List<AccountData>>() {
            @Override
            public void onChanged(List<AccountData> accountData) {
                dealCombineData(accountData,accountDataItems.getValue());
            }
        });
        combineData.addSource(accountDataItems, new Observer<List<AccountDataItem>>() {
            @Override
            public void onChanged(List<AccountDataItem> accountDataItems) {
                dealCombineData(accountData.getValue(),accountDataItems);
            }
        });
    }

    // 处理数据 Action
    private void dealCombineData(List<AccountData> accountData, List<AccountDataItem> accountDataItems){
        if (accountData == null || accountDataItems == null) {
            return;
        }
        for (int i = 0; i < accountData.size(); i++) {
            List<AccountDataItem> combine = new ArrayList<>();
            int list_id = accountData.get(i).getId();
            for (int j = 0; j < accountDataItems.size(); j++) {
                if (list_id == accountDataItems.get(j).getOutList_id()) {
                    combine.add(accountDataItems.get(j));
                }
            }
            accountData.get(i).setAccountList(combine);
        }
        combineData.setValue(accountData);
    }

    public LiveData<List<AccountData>> getCombinedAccountData() {
        return combineData;
    }

    /// 插入账单item数据
    /// todo:如果要添加的这个账单时间日期和账单列表中的某一项相等，则将它的账单列表中的某一项的id赋值给这个账单的外键,
    /// todo:如果没有相等的，说明没有这个列表，则添加新的列表
    public void insertAccountItem(AccountDataItem accountDataItem) {
        Log.d(TAG, "添加的Item账单数据: " + accountDataItem.toString());
        LiveData<List<AccountData>> accountData = dataRepository.getAccountList();

        // 避免直接使用 getValue()：LiveData 的 getValue() 方法可能在数据未准备好时返回 null。使用观察者模式可以确保在数据准备好时才进行操作。
        // List<AccountData> accountDataList = accountData.getValue();

        accountData.observeForever(new Observer<List<AccountData>>() {
            @Override
            public void onChanged(List<AccountData> accountDataList) {
                boolean isFindMatchingAccount = false;
                if(accountDataList == null){
                    Log.e("插入账单","失败--未找到账单");
                    createNewList(0, accountDataItem, 1);
                }
                for (int i = 0; i < accountDataList.size(); i++) {
                    AccountData nowAccountData = accountDataList.get(i);
                    // 有：直接取出id,设置给当前列表项再插入
                    if (accountDataItem.getData().contains(nowAccountData.getCreateDate())) {
                        Log.e("当前有列表数据", "日期相等：列表日期-" + nowAccountData + "\t列表项日期-" + commonTool.dealDate(accountDataItem.getData(), 1));
                        accountDataItem.setOutList_id(nowAccountData.getId());
                        nowAccountData.setLength(nowAccountData.getLength() + 1);
                        if (accountDataItem.getIn() == 1) {
                            String oldMoney = "0.00";
                            if(nowAccountData.getInMoney() != null){
                                oldMoney = nowAccountData.getInMoney();
                            }
                            Double inMoney = Double.parseDouble(oldMoney) + Double.parseDouble(accountDataItem.getMoney());
                            nowAccountData.setInMoney(inMoney.toString());
                        } else {
                            String oldMoney = "0.00";
                            if(nowAccountData.getOutMoney() != null){
                                oldMoney = nowAccountData.getOutMoney();
                            }
                            Double outMoney = Double.parseDouble(oldMoney) + Double.parseDouble(accountDataItem.getMoney());
                            nowAccountData.setOutMoney(outMoney.toString());
                        }
                        dataRepository.insertAccountItem(accountDataItem);
                        dataRepository.updateAccount(nowAccountData);
                        isFindMatchingAccount = true;
                        break;
                    }
                }
                if (!isFindMatchingAccount) {
                    createNewList(accountDataList.size(), accountDataItem, 1);
                }
                accountData.removeObserver(this);
            }
        });
//        dataRepository.dealInsert(accountDataItem);
    }

    private void createNewList(int length, AccountDataItem accountDataItem, int subLength) {
        AccountData newAccountData = new AccountData();
        newAccountData.setId(length + 1);
        newAccountData.setCreateDate(commonTool.dealDate(accountDataItem.getData(), 1));
        newAccountData.setLength(1);
        // 设置金额 1-收入 2-支出
        if (accountDataItem.getIn() == 1) {
            newAccountData.setInMoney(accountDataItem.getMoney());
        } else {
            newAccountData.setOutMoney(accountDataItem.getMoney());
        }
        accountDataItem.setOutList_id(length + 1);
        Log.e("新建一条列表", "列表:" + newAccountData.toString() + "\n列表项:" + accountDataItem.toString());
        dataRepository.insertAccount(newAccountData);
        dataRepository.insertAccountItem(accountDataItem);
    }

    public void deleteAllList() {
        dataRepository.deleteAllAccountItem();
        dataRepository.deleteAllAccountList();
    }

}
