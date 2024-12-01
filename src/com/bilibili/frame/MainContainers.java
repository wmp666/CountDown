package com.bilibili.frame;

import com.bilibili.information.GetTime;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainContainers extends JPanel {

    Container container = this;
    private static int width = 310;
    private String title = " ";
    private Date startTime;//倒计时结束时间
    private int beginTime = 0;//倒计时持续时间


    //窗口信息
    //0-弹窗式 1-窗口式 2-全屏式 3-屏保式
    private int frameType;
    //true-可以关闭 false-不可以关闭
    private boolean isCanExit;
    //true-置顶 false-不置顶
    private boolean isCanTop;

    private Color timeColor;
    private Color titleColor;
    private Color backgroundColor;

    long dayTime = 0;
    private long remainTime = 0;

    static {
        System.out.println("加载中...");
        System.out.println("Version1.5.0");
    }

    public MainContainers() {
    }

    public MainContainers(String title,Date startTime,
                          int frameType, boolean isCanExit, boolean isCanTop,
                          Color timeColor, Color titleColor, Color backgroundColor) throws InterruptedException, IOException {


        this.title = title;
        this.startTime = startTime;

        this.timeColor = timeColor;
        this.titleColor = titleColor;
        this.backgroundColor = backgroundColor;
        this.frameType = frameType;
        this.isCanExit = isCanExit;
        this.isCanTop = isCanTop;


        //initThing();

        setTime();

        initFrame();

    }


/*
    //获取倒计时文件相关信息
    private void initThing() throws IOException {

        GetTimeThing getTimeThing = new GetTimeThing();


        title = getTimeThing.getTitle();
        startTime = getTimeThing.getAfterTime();
        beginTime = getTimeThing.getBeginTime();


    }*/

    //设置倒计时时间相关数据
    protected void initContent() {

        long[] times = setTime();
        setRightString(times[0], (int)times[1],
                this.timeColor,
                this.titleColor,
                this.backgroundColor);

    }

    private long[] setTime() {

        GetTime getTime = new GetTime();
        this.remainTime = getTime.getRemainingTime(startTime);

        dayTime = (remainTime) / 1000 / 60 / 60 / 24 - 1;//获取时间

        if (dayTime < 0) dayTime = 0;

        Integer a = (int) dayTime;

        //获取天数字符串长度
        int dayLength = ((a.toString().length()  + 1)* 81);

        int result = Math.max(dayLength,300);

        //System.out.println(titleLength + "/" + dayLength);
        //System.out.println("结果:" + result);

        //将组件的宽度设为result
        this.width = result;

        return new long[]{remainTime,dayTime};
    }

    //将数据显示到屏幕上
    private void setRightString(long time, int dayTime, Color timeColor, Color titleColor, Color backgroundColor) {

        //Color wordColor = new Color(72, 204, 255, 255);

        JLabel titleLabel = new JLabel();
        JLabel dayLabel = new JLabel();
        JLabel moreTimeLabel = new JLabel();

        //System.out.println(time);
        if (time < 0) {
            //System.out.println(dayTime);

          dayLabel.setText("倒计时已结束");


            dayLabel.setBounds(20, 20, 300, 110);
            dayLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 49));


            container.add(dayLabel);//添加
            //this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);//关闭模式
        } else {
            /*title+*/
            titleLabel.setText("距离" + title +"开始还剩");
            titleLabel.setBounds(5, 0, this.getWidth(), 25);
            titleLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 25));
            container.add(titleLabel);//打印黑色字


            dayLabel.setText(dayTime + "天");
            dayLabel.setBounds(5, 28, this.getWidth() - 5, 110);
            dayLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 100));

            container.add(dayLabel);//添加天数


            Date date2 = new Date(time);
            SimpleDateFormat sdf2 = new SimpleDateFormat("HH时mm分ss秒");

            String strTime = sdf2.format(date2);

            //(time / 1000 / 60) + "分钟"
            moreTimeLabel.setText(strTime);
            moreTimeLabel.setBounds(5, 130, 300, 48);
            moreTimeLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 38));

            container.add(moreTimeLabel);//添加秒数



        }

        //设置天数颜色
        dayLabel.setForeground(timeColor);
        moreTimeLabel.setForeground(timeColor);
        //设置背景颜色
        this.setBackground(backgroundColor);
        //设置标题颜色
        titleLabel.setForeground(titleColor);
    }

    private void initFrame() {

        Integer dayTime = (int)this.dayTime;

        this.setSize(this.width, 175);//大小

        container.setLayout(null);//取消默认居中

    }



    public String getTitle() {
        return title;
    }

    public int getStartSytle() {
        return frameType;
    }

    public boolean getCloseOperation() {
        return isCanExit;
    }

    public boolean getAlwaysOnTop() {
        return isCanTop;
    }

    public long getRemainTime() {
        return remainTime;
    }

    public Color getTimeColor() {
        return timeColor;
    }

    public Color getTitleColor() {
        return titleColor;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }
}
