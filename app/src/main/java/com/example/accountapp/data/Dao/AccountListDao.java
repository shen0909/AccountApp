package com.example.accountapp.data.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.accountapp.data.AccountData;
import java.util.List;

/// 账单列表 Dao
@Dao
public interface AccountListDao {
    @Insert
    void insertAccountList(AccountData accountData);

    @Query("SELECT * FROM AccountDataTable")
    List<AccountData> getAllDataList();
    @Query("SELECT * FROM AccountDataTable ORDER BY createDate DESC")
    LiveData<List<AccountData>> getAllLiveDataList();
    @Query("SELECT * FROM AccountDataTable WHERE list_id = :id ORDER BY createDate DESC")
    AccountData byIdGetAccount(int id);

    @Query("DELETE FROM AccountDataTable")
    void deleteAll();
}
