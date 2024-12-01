package com.bilibili.information;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreativeRightTime {
    private String rightTime;

    public CreativeRightTime (String time){
        Date newtime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(time);//06-17
        rightTime = sdf.format(newtime);
    }

    public String getRightTime() {

        return rightTime;
    }

}
