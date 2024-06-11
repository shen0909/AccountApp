package com.example.accountapp.data.Model;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.accountapp.data.AccountData;
import com.example.accountapp.data.Repository.DataRepository;
import com.example.accountapp.data.Entry.AccountDataItem;
import java.util.List;

public class AccountViewModel extends AndroidViewModel {
    private final DataRepository dataRepository;
    private LiveData<List<AccountDataItem>> accountItemLiveData;
    private LiveData<List<AccountData>> listLiveData;

    public AccountViewModel(@NonNull Application application) {
        super(application);
        dataRepository = new DataRepository(application);
        accountItemLiveData = dataRepository.getData();
        listLiveData = dataRepository.getListData();
    }

    /// 获取账单列表数据
    public LiveData<List<AccountData>> getListLiveData() {
        return listLiveData;
    }

    /// 获取账单数据
    public LiveData<List<AccountDataItem>> getAccountItemLiveData() {
        return accountItemLiveData;
    }

    /// 插入账单列表数据
    public void insertList(AccountData accountData){
        dataRepository.insertList( accountData);
    }

    /// 插入账单列表数据
    public void insertAccountItem(AccountDataItem accountDataItem){
        dataRepository.insertAccountItem( accountDataItem);
    }
}
