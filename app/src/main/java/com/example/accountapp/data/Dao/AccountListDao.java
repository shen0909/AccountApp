package com.example.accountapp.data.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.accountapp.data.Entry.AccountData;
import java.util.List;

/// 账单列表 Dao
@Dao
public interface AccountListDao {
    @Insert
    void insertAccountList(AccountData accountData);

    @Query("SELECT * FROM AccountDataTable ORDER BY createDate DESC")
    List<AccountData> getAllDataList();

    @Update()
    void upDateAccount(AccountData accountData);

    @Query("SELECT * FROM AccountDataTable ORDER BY createDate DESC")
    LiveData<List<AccountData>> getAllLiveDataList();
    @Query("SELECT * FROM AccountDataTable WHERE list_id = :id ORDER BY createDate DESC")
    AccountData byIdGetAccount(int id);

    @Query("DELETE FROM AccountDataTable")
    void deleteAll();
}
