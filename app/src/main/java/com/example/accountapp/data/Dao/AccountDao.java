package com.example.accountapp.data.Dao;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.accountapp.data.Entry.AccountDataItem;
import java.util.List;

/// 账单项 Dao
@Dao
public interface AccountDao{
    @Insert
    void insertAccount(AccountDataItem accountDataItem);

    @Update
    void update(AccountDataItem AccountDataItem);

    // 删除一条数据
    @Delete
    void delete(AccountDataItem AccountDataItem);

    // 删除所有数据
    @Query("DELETE FROM AccountListItemTable")
    void deleteAll();

    @Query("SELECT * FROM AccountListItemTable ORDER BY datetime(data) DESC")
    LiveData<List<AccountDataItem>> getAllData();

    @Query("SELECT * FROM AccountListItemTable WHERE outList_id = :id ORDER BY datetime(data) DESC")
    LiveData<List<AccountDataItem>> getDataByForId(int id);

    @Query("SELECT * FROM AccountListItemTable WHERE outList_id = :out_id ORDER BY datetime(data) DESC")
    List<AccountDataItem> AccountListWithOutId(int out_id);
}
