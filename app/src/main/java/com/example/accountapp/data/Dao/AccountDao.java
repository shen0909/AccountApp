package com.example.accountapp.data.Dao;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.accountapp.data.Entry.AccountDataItem;
import java.util.List;

@Dao
public interface AccountDao{
    @Insert
    void insertAccount(AccountDataItem AccountDataItem);

    @Update
    void update(AccountDataItem AccountDataItem);

    @Delete
    void delete(AccountDataItem AccountDataItem);

    @Query("SELECT * FROM AccountListItemTable")
    LiveData<List<AccountDataItem>> getAllData();
}
