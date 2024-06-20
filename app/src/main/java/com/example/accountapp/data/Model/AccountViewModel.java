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
import com.example.accountapp.utils.CommonTool;
import java.util.ArrayList;
import java.util.List;

public class AccountViewModel extends AndroidViewModel {
    private final DataRepository dataRepository;
    private LiveData<List<AccountData>> combineList;


    public AccountViewModel(@NonNull Application application, LifecycleOwner lifecycleOwner) {
        super(application);
        dataRepository = new DataRepository(application);
        this.combineList = dataRepository.dealBackData();
    }

    public LiveData<List<AccountData>> getCombineList(){
        return combineList;
    }

    /// 插入账单item数据
    public void insertAccountItem(AccountDataItem accountDataItem, LifecycleOwner lifecycleOwner) {
        Log.d(TAG, "添加的Item账单数据: " + accountDataItem.toString());
        dataRepository.dealInsert(accountDataItem);
    }

    public void deleteAllList() {
        dataRepository.deleteAllAccountItem();
        dataRepository.deleteAllAccountList();
    }

}
