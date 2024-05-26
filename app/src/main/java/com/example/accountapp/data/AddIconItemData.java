package com.example.accountapp.data;

// 添加账单- 图标数据类
public class AddIconItemData {
    int iconPath;

    public int getUnselectIconPath() {
        return unselectIconPath;
    }

    int unselectIconPath;
    String title;
    boolean hasMore;
    boolean isChecked;

    public int getIconPath() {
        return iconPath;
    }

    public String getTitle() {
        return title;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIconPath(int iconPath) {
        this.iconPath = iconPath;
    }

    public void setUnSelectIconPath(int selectIconPath) {
        this.unselectIconPath = selectIconPath;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public AddIconItemData(int iconPath, int selectIconPath, String title, boolean hasMore, boolean isChecked) {
        this.iconPath = iconPath;
        this.unselectIconPath = selectIconPath;
        this.title = title;
        this.hasMore = hasMore;
        this.isChecked = isChecked;
    }
}

