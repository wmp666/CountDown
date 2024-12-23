package com.bilibili.frame;

import com.bilibili.dialog.SettingsDialog;
import com.bilibili.information.GetTime;
import com.bilibili.information.InformationLib;
import com.bilibili.main.Main;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class MainContainers extends JPanel {

    private Container container = this;
    private static int width = 300;
    private String title = " ";
    private Date startTime;//倒计时结束时间
    //private int beginTime = 0;//倒计时持续时间


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
        System.out.println("Version1.5.4");
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

        setTime();

        initFrame();

        initButton();


    }

    public void initButton() {
        //按钮集合
        ArrayList<JButton> buttonList = new ArrayList<>();
        buttonList.add(new JButton("关闭"));
        buttonList.add(new JButton("刷新"));
        buttonList.add(new JButton("设置"));
        //buttonList.add(new JButton("更多"));

        //初始化按钮默认数据
        for (int i = 0; i < buttonList.size(); i++) {
            JButton button = buttonList.get(i);
            button.setBackground(this.backgroundColor);
            button.setForeground(this.titleColor);
            button.setFont(new Font("Microsoft YaHei", Font.BOLD, 20));
            button.setBorder(null);//去除边框
            button.setFocusPainted(false);//去除焦点
            button.setOpaque(false);//去除背景
        }

        {
            JButton close = buttonList.get(0);
            close.setForeground(Color.red);
            //设置按钮是否可以使用

            close.addActionListener(e -> {

                if (isCanExit) {
                    int i = JOptionPane.showConfirmDialog(null,
                            "确定关闭？",
                            "询问",
                            JOptionPane.YES_NO_OPTION);
                    if (i == 0) {
                        System.exit(0);
                    }
                }else{
                    JOptionPane.showMessageDialog(null,
                            "用户禁止关闭",
                            "通知",
                            JOptionPane.ERROR_MESSAGE);
                }

            });
        }

        //刷新
        {
            JButton reload = buttonList.get(1);
            reload.addActionListener(e -> {
                System.out.println("刷新");

                try {
                    Main.reload(new InformationLib());
                } catch (IOException | ParseException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

            });
        }

        //设置
        {
            JButton setting = buttonList.get(2);
            setting.addActionListener(e -> {

                new SettingsDialog();
                /*Random r = new Random();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < 6; i++) {
                    sb.append(r.nextInt(10));
                }
                String code = " ";
                code = sb.toString();
                String password = JOptionPane.showInputDialog(null,
                        "请输入密码" + code,
                        "密码询问",
                        JOptionPane.INFORMATION_MESSAGE);

                try {
                    StringBuilder sb1 = new StringBuilder();

                    for (int i = code.length() - 1; i >= 0; i--) {
                        sb1.append(code.charAt(i));
                    }
                    System.out.println(sb1.toString());
                    if (password.equals(sb1.toString())){
                        System.out.println("密码正确");
                        new SettingsDialog();
                    }else {
                        JOptionPane.showMessageDialog(null,
                                "密码错误",
                                "密码询问",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (HeadlessException ex) {
                    JOptionPane.showMessageDialog(null,
                            "密码错误",
                            "密码询问",
                            JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException(ex);
                }*/

            });
        }

        //更多
        /*{
            JButton more = buttonList.get(3);
            more.addActionListener(e -> {
                MoreDialog moreDialog = new MoreDialog();

            });
        }*/


        for (int i = 0; i < buttonList.size(); i++) {
            JButton button = buttonList.get(i);

            button.setBounds(i * (this.getWidth() / buttonList.size()), 178,
                    this.getWidth() / buttonList.size(), 35);

            container.add(buttonList.get(i));

        }
    }

    //设置倒计时时间相关数据
    public void initContent() {

        container.removeAll();

        long[] times = setTime();
        initButton();
        setRightString(times[0], (int)times[1],
                this.timeColor,
                this.titleColor,
                this.backgroundColor);

    }

    private long[] setTime() {

        GetTime getTime = new GetTime();
        this.remainTime = getTime.getRemainingTime(startTime);

        dayTime = (remainTime + 8 * 60 * 60 * 1000) / 1000 / 60 / 60 / 24 - 1;//获取时间

        if (dayTime < 0) dayTime = 0;

        Integer a = (int) dayTime;

        //获取天数字符串长度
        int dayLength = ((a.toString().length()  + 1)* 81);

        int result = Math.max(dayLength,300);

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
            titleLabel.setText("距离" + title +"还剩");
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

        this.setSize(this.width, 210);//大小

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MainContainers that = (MainContainers) o;
        return frameType == that.frameType && isCanExit == that.isCanExit && isCanTop == that.isCanTop && dayTime == that.dayTime && getRemainTime() == that.getRemainTime() && Objects.equals(getTitle(), that.getTitle()) && Objects.equals(startTime, that.startTime) && Objects.equals(getTimeColor(), that.getTimeColor()) && Objects.equals(getTitleColor(), that.getTitleColor()) && Objects.equals(getBackgroundColor(), that.getBackgroundColor());
    }

    @Override
    public int hashCode() {
        return Objects.hash( getTitle(), startTime, frameType, isCanExit, isCanTop, getTimeColor(), getTitleColor(), getBackgroundColor(), dayTime, getRemainTime());
    }
}
