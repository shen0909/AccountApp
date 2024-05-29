package com.example.accountapp.data;

import android.content.Context;
import androidx.lifecycle.LiveData;
import com.example.accountapp.data.Dao.AccountDao;
import com.example.accountapp.data.Entry.AccountDataItem;
import java.util.List;

// 数据存储库
// 抽象了对多个数据源的访问。存储库不是架构组件库的一部分，但它是代码分离和架构的最佳实践。
// 为应用程序其余部分的数据访问提供了干净的 API。
public class DataRepository {
    private final AccountDao accountDao;

    public DataRepository(Context context) {
        AppRoomDataBase appRoomDataBase = AppRoomDataBase.getDataBase(context);
        accountDao = appRoomDataBase.accountDao();
    }

    public void insert(AccountDataItem accountDataItem) {
        AppRoomDataBase.databaseWriteExecutor.execute(() -> accountDao.insertAccount(accountDataItem));
    }

    public LiveData<List<AccountDataItem>> getData() {
        return accountDao.getAllData();
    }

//    public void insertList(AccountData accountData) {
//        AppRoomDataBase.databaseWriteExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                accountListDao.insertAccountList(accountData);
//            }
//        });
//    }

    //    public void getDataList(ListDataLoadListener listener){
//        AppRoomDataBase.databaseWriteExecutor.execute(() -> {
//            List<AccountData> accountDataList = new ArrayList<>();
//            accountDataList.addAll(accountListDao.getAllData());
//            if(listener != null){
//                listener.onDataLoaded(accountDataList);
//            }
//        });
//    }
//    public interface ListDataLoadListener {
//        void onDataLoaded(List<AccountData> data);
//    }
//
//    public interface DataLoadListener {
//        void onDataLoaded(List<AccountDataItem> data);
//    }
//    public void getData(DataLoadListener listener){
//        AppRoomDataBase.databaseWriteExecutor.execute(() -> {
//            List<AccountDataItem> accountDataItems = new ArrayList<>();
//            accountDataItems.addAll(accountDao.getAllData());
//            if(listener != null){
//                listener.onDataLoaded(accountDataItems);
//            }
//        });
//    }
}
