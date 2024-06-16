package com.example.accountapp.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import com.example.accountapp.data.Entry.AccountDataItem;
import com.example.accountapp.data.Model.AccountViewModel;

import java.util.List;

// 账单列表
@Entity(tableName = "AccountDataTable")
//@Entity(tableName = "AccountDataTable", foreignKeys = @ForeignKey(entity = AccountDataItem.class, childColumns = "item_id", parentColumns = "id"))
public class AccountData {
    // 主键：如果日期一样，那就是添加不是修改
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "list_id")
    private int id; //主键
    private String createDate; //账单创建时间
    private String outMoney; //支出金额
    private String inMoney; //收入金额

    //    @Relation(parentColumn = "id", entityColumn = "item_id")
    //    @Embedded
    @Ignore
    private List<AccountDataItem> accountList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AccountData() {
    }

    public AccountData(String time, List<AccountDataItem> accountList) {
        this.createDate = time;
        this.accountList = accountList;
    }


    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getOutMoney() {
        return outMoney;
    }

    public void setOutMoney(String outMoney) {
        this.outMoney = outMoney;
    }

    public String getInMoney() {
        return inMoney;
    }

    public void setInMoney(String inMoney) {
        this.inMoney = inMoney;
    }

    public List<AccountDataItem> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<AccountDataItem> accountList) {
        this.accountList = accountList;
    }

    @NonNull
    @Override
    public String toString() {
        return "ID:" + getId() + "\t日期：" + getCreateDate() + "\t收入金额：" + getInMoney() + "\t支出金额：" + getOutMoney();
    }
}
