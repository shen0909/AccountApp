package com.example.accountapp.data;

import com.example.accountapp.R;
import java.util.ArrayList;
import java.util.List;

// 放置所有数据
public class AllData {
    private final List<AddIconItemData> addIconItemDataList  = new ArrayList<>();

    public AllData() {
        addIconItemDataList.add(new AddIconItemData(R.drawable.canyin,R.drawable.canyin_unselect,"餐饮", false, false));
        addIconItemDataList.add(new AddIconItemData(R.drawable.fushi,R.drawable.fushi_unselect, "服饰", false, false));
        addIconItemDataList.add(new AddIconItemData(R.drawable.zhufang,R.drawable.zhufang_unselect, "住房", false, false));
        addIconItemDataList.add(new AddIconItemData(R.drawable.chuxing,R.drawable.chuxing_unselect, "出行", false, false));
    }

    public List<AddIconItemData> getAddIconItemDataList() {
        return addIconItemDataList;
    }
}
