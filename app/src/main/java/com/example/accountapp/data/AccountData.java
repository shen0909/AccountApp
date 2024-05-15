package com.example.accountapp.data;

import java.util.List;

public class AccountData {
    String time;
    double outMoney; //支出
    double inMoney; //收入
    List<AccountDataItem> accountList;

    public AccountData(String time, List<AccountDataItem> accountList) {
        this.time = time;
        this.accountList = accountList;
        getMoney();
    }

    // 获得总额
    private void getMoney() {
        for (int i = 0; i < accountList.size(); i++) {
            AccountDataItem accountDataItem = this.accountList.get(i);
            // 当前是收入金额
            if(accountDataItem.in == 0){
                this.inMoney += Double.parseDouble(accountDataItem.money);
            }else {
                this.outMoney += Double.parseDouble(accountDataItem.money);
            }
        }
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
}
