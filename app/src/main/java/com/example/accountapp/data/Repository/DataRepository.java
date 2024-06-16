package com.example.accountapp.data.Repository;

import android.content.Context;
import androidx.lifecycle.LiveData;
import com.example.accountapp.data.AccountData;
import com.example.accountapp.data.AppRoomDataBase;
import com.example.accountapp.data.Dao.AccountDao;
import com.example.accountapp.data.Dao.AccountListDao;
import com.example.accountapp.data.Entry.AccountDataItem;
import java.util.List;

// 数据存储库
// 抽象了对多个数据源的访问。存储库不是架构组件库的一部分，但它是代码分离和架构的最佳实践。
// 为应用程序其余部分的数据访问提供了干净的 API。
public class DataRepository {
    private final AccountDao accountDao;
    private final AccountListDao accountListDao;
    private AppRoomDataBase appRoomDataBase;

    public DataRepository(Context context) {
        appRoomDataBase = AppRoomDataBase.getDataBase(context);
        accountDao = appRoomDataBase.accountDao();
        accountListDao = appRoomDataBase.accountListDao();
    }

    /// 插入账单
    /// todo:如果要添加的这个账单时间日期和账单列表中的某一项相等，则将它的账单列表中的某一项的id赋值给这个账单的外键,
    /// todo:如果没有相等的，说明没有这个列表，则添加新的列表
    public void insertAccountItem(AccountDataItem accountDataItem) {
        appRoomDataBase.databaseWriteExecutor.execute(() -> accountDao.insertAccount(accountDataItem));
    }

    /// 插入账单列表
    public void insertList(AccountData accountData) {
        appRoomDataBase.databaseWriteExecutor.execute(() -> accountListDao.insertAccountList(accountData));
    }

    // 获取账单列表
    public LiveData<List<AccountData>> getListLiveData() {
        return accountListDao.getAllLiveDataList();
    }

    public LiveData<List<AccountDataItem>> getData() {
        return accountDao.getAllData();
    }

    public void deleteAllAccountItem() {
        appRoomDataBase.databaseWriteExecutor.execute(() -> accountDao.deleteAll());
    }

    public void deleteAllAccountList() {
        appRoomDataBase.databaseWriteExecutor.execute(() -> accountListDao.deleteAll());
    }

//    public void insertList(AccountData accountData) {
//        AppRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                accountListDao.insertAccountList(accountData);
//            }
//        });
//    }


//
    public interface DataLoadListener {
        void onDataLoaded(AccountData data);
    }

    public LiveData<List<AccountDataItem>> getDataByForId(int id) {
        return accountDao.getDataByForId(id);
    }

//    public AccountData getAccountByForId(int id) {
//        return accountListDao.byIdGetAccount(id);
//    }

    public void getAccountByForId(DataLoadListener listener,int id){
        AppRoomDataBase.databaseWriteExecutor.execute(() -> {
            if(listener != null){
                listener.onDataLoaded(accountListDao.byIdGetAccount(id));
            }
        });
    }
}
