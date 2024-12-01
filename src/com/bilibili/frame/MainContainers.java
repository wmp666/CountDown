package com.bilibili.frame;

import com.bilibili.information.GetTime;
import com.bilibili.information.InformationLib;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainContainers extends JPanel {

    Container container = this;
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
        System.out.println("Version1.5.1");
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

    private void initButton() {
        //按钮集合
        ArrayList<JButton> buttonList = new ArrayList<>();
        buttonList.add(new JButton("刷新"));
        buttonList.add(new JButton("设置"));

        //初始化按钮默认数据
        for (int i = 0; i < buttonList.size(); i++) {
            JButton button = buttonList.get(i);
            button.setBackground(this.backgroundColor);
            button.setForeground(this.titleColor);
            button.setFont(new Font("Microsoft YaHei", Font.BOLD, 20));
            //button.setBorder(null);//去除边框
            button.setFocusPainted(false);//去除焦点
            button.setOpaque(false);//去除背景
        }

        JButton reload = buttonList.get(0);
        reload.addActionListener(e -> {
            System.out.println("刷新");

            try {
                reload();
            } catch (IOException | ParseException ex) {
                throw new RuntimeException(ex);
            }

        });

        JButton setting = buttonList.get(1);
        setting.addActionListener(e -> {

            JDialog settingsDialog = new JDialog();
            settingsDialog.setTitle("设置");

            settingsDialog.setLayout(null);
            //居中
            settingsDialog.setLocationRelativeTo(null);
            //大小
            settingsDialog.setSize(470, 340);
            //设置图标
            settingsDialog.setIconImage(Toolkit.getDefaultToolkit().getImage("lib\\image\\icon.png"));

            SettingsPanel settingsPanel;
            try {
                settingsPanel = new SettingsPanel();
                settingsDialog.getContentPane().add(settingsPanel.getPanel());
            } catch (IOException | ParseException ex) {
                throw new RuntimeException(ex);
            }

            settingsDialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {


                    int i = JOptionPane.showConfirmDialog(null,
                            "将数据保存,并刷新？",
                            "询问",
                            JOptionPane.YES_NO_OPTION);
                    if (i == 0) {
                        InformationLib informationLib;
                        try {
                            informationLib = new InformationLib();
                            //输入新数据
                            informationLib.addInf(settingsPanel.getAllThing());
                        } catch (IOException | ParseException ex) {
                            throw new RuntimeException(ex);
                        }

                        try {
                            reload();
                        } catch (IOException | ParseException ex) {
                            throw new RuntimeException(ex);
                        }

                    }
                }

            });

            settingsDialog.setAlwaysOnTop(true);
            settingsDialog.setResizable(false);
            settingsDialog.setVisible(true);


        });

        for (int i = 0; i < buttonList.size(); i++) {
            JButton button = buttonList.get(i);
            button.setBounds(i * (this.getWidth() / buttonList.size()), 178,
                    this.getWidth() / buttonList.size(), 35);
            container.add(buttonList.get(i));
        }
    }

    private void reload() throws IOException, ParseException {


        //刷新所有数据

        container.removeAll();

        InformationLib informationLib;

            informationLib = new InformationLib();


        this.title = informationLib.getTitle();
        this.startTime = informationLib.getStartTime();

        this.timeColor = informationLib.getNumColor();
        this.titleColor = informationLib.getTitleColor();
        this.backgroundColor = informationLib.getBGColor();
        this.frameType = informationLib.getFrameType();
        this.isCanExit = informationLib.isCanExit();
        this.isCanTop = informationLib.isCanTop();

        setTime();

        initFrame();

        container.repaint();
    }

    //设置倒计时时间相关数据
    protected void initContent() {

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

        dayTime = (remainTime) / 1000 / 60 / 60 / 24 - 1;//获取时间

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
}
