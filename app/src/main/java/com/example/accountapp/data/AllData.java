package com.example.accountapp.data;

import com.example.accountapp.R;
import java.util.ArrayList;
import java.util.List;

// 放置所有数据
public class AllData {
    private final List<AddIconItemData> addIconOutDataList = new ArrayList<>();
    private final List<AddIconItemData> addIconInDataList = new ArrayList<>();

    public AllData() {
        //添加账单 - 支出图标
        addIconOutDataList.add(new AddIconItemData(R.drawable.canyin,R.drawable.canyin_unselect,"餐饮", false, false));
        addIconOutDataList.add(new AddIconItemData(R.drawable.fushi,R.drawable.fushi_unselect, "服饰", false, false));
        addIconOutDataList.add(new AddIconItemData(R.drawable.zhufang,R.drawable.zhufang_unselect, "住房", false, false));
        addIconOutDataList.add(new AddIconItemData(R.drawable.chuxing,R.drawable.chuxing_unselect, "出行", false, false));

        //添加账单 - 收入图标
        addIconInDataList.add(new AddIconItemData(R.drawable.gongzi,R.drawable.gongzi_unselect,"工资",false,false));
        addIconInDataList.add(new AddIconItemData(R.drawable.jiangjin,R.drawable.jiangjin_unselect,"奖金",false,false));
        addIconInDataList.add(new AddIconItemData(R.drawable.hongbao,R.drawable.hongbao_unselect,"红包",false,false));
        addIconInDataList.add(new AddIconItemData(R.drawable.gongzi,R.drawable.gongzi_unselect,"工资",false,false));
    }

    public List<AddIconItemData> getAddIconOutDataList() {
        return addIconOutDataList;
    }

    public List<AddIconItemData> getAddIconInDataList(){
        return addIconInDataList;
    }
}
