package com.bilibili.information;

import java.util.Date;

public class GetTime {
    private long afterTime;
    private long nowTime;

    public GetTime() {
    }

    private void getAfterTime(Date afterTime) {
        /*String str = new CreativeRightTime(afterTime).getRightTime();
        //带参---SimpleDateFormat(格式要完全一样)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        //解析str
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }*/

        this.afterTime = afterTime.getTime() + 24 * 60 * 60 * 1000 - 8 * 60 * 60 * 1000;

        //System.out.println(this.afterTime);
    }

    private void getNowTime() {
        this.nowTime = new Date().getTime();
    }

    public long getRemainingTime(Date afterTime) {
        getNowTime();
        getAfterTime(afterTime);
        //(getTime.getRemainingTime()>0?getTime.getRemainingTime():0)


        long result = this.afterTime - this.nowTime;


        if ((result -
                (24 * 60 * 60 * 1000 - 8 * 60 * 60 * 1000)) < 0){
            //减去原加入一天的时间
            return result - (24 * 60 * 60 * 1000 - 8 * 60 * 60 * 1000);
        }
        return this.afterTime - this.nowTime;
    }

}
