package com.example.accountapp.data.Repository;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.accountapp.data.AccountData;
import com.example.accountapp.data.AppRoomDataBase;
import com.example.accountapp.data.Dao.AccountDao;
import com.example.accountapp.data.Dao.AccountListDao;
import com.example.accountapp.data.Entry.AccountDataItem;
import com.example.accountapp.utils.CommonTool;
import java.util.ArrayList;
import java.util.List;

// 数据存储库
// 抽象了对多个数据源的访问。存储库不是架构组件库的一部分，但它是代码分离和架构的最佳实践。
// 为应用程序其余部分的数据访问提供了干净的 API。
public class DataRepository {
    private final AccountDao accountDao;
    private final AccountListDao accountListDao;
    private final CommonTool commonTool = new CommonTool();
    private MutableLiveData<List<AccountData>> combineDataList = new MutableLiveData<>();

    public DataRepository(Context context) {
        AppRoomDataBase appRoomDataBase = AppRoomDataBase.getDataBase(context);
        accountDao = appRoomDataBase.accountDao();
        accountListDao = appRoomDataBase.accountListDao();
        dealBackData();
    }

    /// 插入账单
    /// todo:如果要添加的这个账单时间日期和账单列表中的某一项相等，则将它的账单列表中的某一项的id赋值给这个账单的外键,
    /// todo:如果没有相等的，说明没有这个列表，则添加新的列表
    public void dealInsert(AccountDataItem accountDataItem) {
        AppRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<AccountData> accountDataList = accountListDao.getAllDataList();
                boolean isFindMatchingAccount = false;
                for (int i = 0; i < accountDataList.size(); i++) {
                    AccountData nowAccountData = accountDataList.get(i);
                    // 有：直接取出id,设置给当前列表项再插入
                    if (accountDataItem.getData().contains(nowAccountData.getCreateDate())) {
                        Log.e("当前有列表数据", "日期相等：列表日期-" + nowAccountData + "\t列表项日期-" + commonTool.dealDate(accountDataItem.getData(), 1));
                        accountDataItem.setOutList_id(nowAccountData.getId());
                        nowAccountData.setLength(nowAccountData.getLength() + 1);
                        accountDao.insertAccount(accountDataItem);
                        accountListDao.upDateAccount(nowAccountData);
                        isFindMatchingAccount = true;
                        break;
                    }
                }
                if (!isFindMatchingAccount) {
                    createNewList(accountDataList.size(), accountDataItem, 1);
                }
            }
        });
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
        accountListDao.insertAccountList(newAccountData);
        accountDao.insertAccount(accountDataItem);
    }

    // 处理返回列表
    public void dealBackData() {
        Log.d("eventbus", "接受更新");
        AppRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Log.d("eventbus", "接受更新——————————————");
                List<AccountData> newAccountList = new ArrayList<>();
                // 获取列表,根据每个列表的id获取列表项
                List<AccountData> oldAccountList = accountListDao.getAllDataList();
                for (int i = 0; i < oldAccountList.size(); i++) {
                    AccountData oldAccountData = oldAccountList.get(i);
                    Log.d("eventbus", oldAccountData.toString());
                    List<AccountDataItem> accountDataItemList = accountDao.AccountListWithOutId(oldAccountData.getId());
                    oldAccountData.setAccountList(accountDataItemList);
//                    oldAccountData.setLength(accountDataItemList.size());
                }
                combineDataList.postValue(oldAccountList);
            }
        });
    }

    public LiveData<List<AccountData>> backCombineList() {
        System.out.println("这里更新了");
        return combineDataList;
    }

    public LiveData<List<AccountDataItem>> getAccoutnItemList(){
        return accountDao.getAllData();
    }

    public LiveData<List<AccountData>> getAccountList(){
        return accountListDao.getAllLiveDataList();
    }

    //---------------------删除表

    public void deleteAllAccountItem() {
        AppRoomDataBase.databaseWriteExecutor.execute(() -> accountDao.deleteAll());
    }

    public void deleteAllAccountList() {
        AppRoomDataBase.databaseWriteExecutor.execute(() -> accountListDao.deleteAll());
    }
}
