package com.example.accountapp.data.Repository;

import android.content.Context;
import androidx.lifecycle.LiveData;
import com.example.accountapp.data.Entry.AccountData;
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

    public DataRepository(Context context) {
        AppRoomDataBase appRoomDataBase = AppRoomDataBase.getDataBase(context);
        accountDao = appRoomDataBase.accountDao();
        accountListDao = appRoomDataBase.accountListDao();
    }

    /// 插入账单项
    public void insertAccountItem(AccountDataItem accountDataItem){
        AppRoomDataBase.databaseWriteExecutor.execute(() ->  accountDao.insertAccount(accountDataItem));
    }

    /// 插入账单列表
    public void insertAccount(AccountData accountData){
        AppRoomDataBase.databaseWriteExecutor.execute(() -> accountListDao.insertAccountList(accountData));
    }

    /// 更新账单列表
    public void updateAccount(AccountData accountData){
        AppRoomDataBase.databaseWriteExecutor.execute(() -> accountListDao.upDateAccount(accountData));
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
