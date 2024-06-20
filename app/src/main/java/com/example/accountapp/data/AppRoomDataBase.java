package com.example.accountapp.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.accountapp.data.Dao.AccountDao;
import com.example.accountapp.data.Dao.AccountListDao;
import com.example.accountapp.data.Entry.AccountDataItem;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {AccountDataItem.class, AccountData.class},version = 7,exportSchema = false)
public abstract class AppRoomDataBase extends RoomDatabase {
    private static volatile AppRoomDataBase INSTANCE;
    public abstract AccountDao accountDao();
    public abstract AccountListDao accountListDao();

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);
    // 单例模式
    public static AppRoomDataBase getDataBase(Context context){
        if (INSTANCE == null) {
            synchronized (AppRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                   context.getApplicationContext(),
                                    AppRoomDataBase.class,
                                    "记账数据库"
                            )
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
