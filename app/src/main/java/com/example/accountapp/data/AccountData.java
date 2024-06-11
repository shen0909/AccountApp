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
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "list_id")
    private int id; //主键
    private String createDate; //账单创建时间
    private double outMoney; //支出金额
    private double inMoney; //收入金额

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
        getMoney();
    }

    // 获得总额
    private void getMoney() {
        this.inMoney = 100;
        this.outMoney = 0;
//        for (int i = 0; i < accountList.size(); i++) {
//            AccountDataItem accountDataItem = this.accountList.get(i);
//            // 当前是收入金额
//            if(accountDataItem.getIn() == 0){
//                this.inMoney += Double.parseDouble(accountDataItem.getMoney());
//            }else {
//                this.outMoney += Double.parseDouble(accountDataItem.getMoney());
//            }
//        }
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public double getOutMoney() {
        return outMoney;
    }

    public void setOutMoney(double outMoney) {
        this.outMoney = outMoney;
    }

    public double getInMoney() {
        return inMoney;
    }

    public void setInMoney(double inMoney) {
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
        if (getAccountList() == null || getAccountList().size() == 0) {
            return "AccountData{" +
                    "id='" + id + '\'' +
                    '}';
        } else {
            return "AccountData {" +
                    "id='" + id + '\'' +
                    ", accountDataItemList=" + getAccountList().toString() +
                    '}';
        }
//        System.out.println("ID:" + getId() + "\t日期" + getCreateDate() + "\t具体账单" + getAccountList().size());
//        return super.toString();
    }
}
