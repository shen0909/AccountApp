package com.example.accountapp.data;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.accountapp.data.Entry.AccountDataItem;
import java.util.List;

public class AccountViewModel extends AndroidViewModel {
    private final DataRepository dataRepository;
    private LiveData<List<AccountDataItem>> listLiveData;

    public AccountViewModel(@NonNull Application application) {
        super(application);
        dataRepository = new DataRepository(application);
        listLiveData = dataRepository.getData();
    }

    public LiveData<List<AccountDataItem>> getListLiveData() {
        return listLiveData;
    }
    public void insert(AccountDataItem accountDataItem){
        dataRepository.insert( accountDataItem);
    }
}
