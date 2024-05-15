package com.example.accountapp.data;

// 账单数据
public class AccountDataItem {
    String money; // 账单金额
    String type; // 消费类别 -餐饮类
    String detail; // 消费详情
    String data; //消费时间
    int in; // 1.收入 2.支出

    public AccountDataItem(String money, String type, String detail, String data, int in) {
        this.money = money;
        this.type = type;
        this.detail = detail;
        this.data = data;
        this.in = in;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getIn() {
        return in;
    }

    public void setIn(int in) {
        this.in = in;
    }
}
