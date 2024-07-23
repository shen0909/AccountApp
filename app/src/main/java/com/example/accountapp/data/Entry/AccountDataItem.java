package com.example.accountapp.data.Entry;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Arrays;

// 账单数据类
// 关联 AccountData.class，主键是
@Entity(tableName = "AccountListItemTable")
public class AccountDataItem {
    // 主键
    @PrimaryKey(autoGenerate = true)
    private int item_id = 0;

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    private int outList_id; //外层列表的主键

    public int getOutList_id() {
        return outList_id;
    }

    public void setOutList_id(int outList_id) {
        this.outList_id = outList_id;
    }

    private String money; // 账单金额
    private String type; // 消费类别 -餐饮类
    private String detail; // 消费详情(备注)
    private String data; //消费时间
    private int in; // 1.收入 2.支出
    private byte[] imageByte;

    public AccountDataItem(String money, String type, String detail, String data, int in) {
        this.money = money;
        this.type = type;
        this.detail = detail;
        this.data = data;
        this.in = in;
    }
    public AccountDataItem(){};

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

    public byte[] getImageByte() {
        return imageByte;
    }

    public void setImageByte(byte[] imageByte) {
        this.imageByte = imageByte;
    }

    @NonNull
    @Override
    public String toString() {
        return "ID：" + getItem_id() + "\t备注：" + getDetail() + "\t金额：" + getMoney() + "\t类型：" + getType() + "\t方向：" + (getIn() == 1 ? "收入" : "支出" )+ "\t外键" + getOutList_id()+ "\t图片字节数组" + Arrays.toString(getImageByte());
    }
}
