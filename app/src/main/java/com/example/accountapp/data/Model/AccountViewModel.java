package com.example.accountapp.data.Model;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.accountapp.data.Entry.AccountData;
import com.example.accountapp.data.Repository.DataRepository;
import com.example.accountapp.data.Entry.AccountDataItem;
import java.util.List;

public class AccountViewModel extends AndroidViewModel {
    private final DataRepository dataRepository;
    private LiveData<List<AccountData>> combineList;
    private LiveData<List<AccountData>> accountList;
    private LiveData<List<AccountDataItem>> accountItemList;


    public AccountViewModel(@NonNull Application application) {
        super(application);
        dataRepository = new DataRepository(application);
        this.combineList = dataRepository.backCombineList();
        this.accountItemList = dataRepository.getAccoutnItemList();
        this.accountList = dataRepository.getAccountList();
    }
    public LiveData<List<AccountDataItem>> backItemList(){
        return accountItemList;
    }

    public LiveData<List<AccountData>> getListLiveData(){
        return accountList;
    }
    public LiveData<List<AccountData>> getCombineList(){
//        dataRepository.dealBackData();
        System.out.println("这里也更新了");
        return combineList;
    }

    /// 插入账单item数据
    public void insertAccountItem(AccountDataItem accountDataItem) {
        Log.d(TAG, "添加的Item账单数据: " + accountDataItem.toString());
        dataRepository.dealInsert(accountDataItem);
    }

    public void deleteAllList() {
        dataRepository.deleteAllAccountItem();
        dataRepository.deleteAllAccountList();
    }

}
