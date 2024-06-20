package com.example.accountapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonTool {
    // 处理日期格式
    public String dealDate(String oldDate,int changeType) {
        // 日期格式
        SimpleDateFormat fullDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat hourAndMinutes = new SimpleDateFormat("HH:mm");
        Date newDate = null;
        try {
            newDate = fullDateFormat.parse(oldDate);
            if(changeType == 1){
                return dateFormat.format(newDate);
            }else if(changeType == 2){
                return hourAndMinutes.format(newDate);
            }else if(changeType == 3){
                return fullDateFormat.format(newDate);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return oldDate;
    }
}
