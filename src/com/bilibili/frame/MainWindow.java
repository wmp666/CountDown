package com.bilibili.frame;

import com.bilibili.information.GetScreenSize;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainWindow extends JWindow{

    MainContainers mainContainer = new MainContainers();

    private int color;
    private Color[] colors = new Color[3];

    public MainWindow(MainContainers MainContainer,int color) throws IOException, InterruptedException {

        this.color = color;
        //color 0:黑色 1:白色

        this.mainContainer = MainContainer;

        colors[0] = mainContainer.getTimeColor();
        colors[1] = mainContainer.getTitleColor();
        colors[2] = mainContainer.getBackgroundColor();

        initFrame();

        JProgressBar timeProgressBar = initProgressBar();

        this.setLayout(null);



        mainContainer.setLocation((this.getWidth() - mainContainer.getWidth())/2,
                (this.getHeight() - mainContainer.getHeight())/2 - 40);

        //关闭按钮
        {
            JButton exitButton = new JButton("关闭");
            exitButton.setBounds(mainContainer.getX()
                    , mainContainer.getY() + mainContainer.getHeight() + 10,
                    mainContainer.getWidth(), 40);
            exitButton.setFont(new Font("微软雅黑", Font.BOLD, 23));

            //设置颜色
            {
                exitButton.setForeground(colors[1]);
                exitButton.setBackground(colors[2]);
            }

            exitButton.addActionListener(e -> System.exit(0));
            this.getContentPane().add(exitButton);
        }

        //设置背景
        {

            this.getContentPane().setBackground(colors[2]);
        }

        //标题标签
        {
            JLabel titleLabel = new JLabel(mainContainer.getTitle() + "倒计时");
            titleLabel.setBounds(mainContainer.getX() + 5,
                    mainContainer.getY() - 50,
                    300, 40);
            titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 23));

            //设置标题颜色
            {

                titleLabel.setForeground(colors[1]);
            }

            this.getContentPane().add(titleLabel);
        }

        this.getContentPane().add(mainContainer);

        this.setVisible(true);

        long startremainTime = mainContainer.getRemainTime();

        //刷新
        while (true) {
            //setThing();
            mainContainer.initContent();

            if (mainContainer.getRemainTime() < 0){
                timeProgressBar.setIndeterminate(true);
                Thread.sleep(1000);//反复刷新
            }else{

                //将进度条的数字设置为，倒计时剩余时间的百分比
                int startremainTime_Min = (int) startremainTime/1000 - 960 *60;
                int remainTime_Min = (int) mainContainer.getRemainTime()/1000 - 960*60;


                System.out.println("剩余时间-" + remainTime_Min);
                System.out.println("最初的剩余时间:" + startremainTime_Min);
                double result = ((double)remainTime_Min / (double) startremainTime_Min) *100;
                timeProgressBar.setValue((int)result);
                System.out.println("进度条百分比:" + result);
                System.out.println(timeProgressBar.getValue());


                mainContainer.repaint();
                Thread.sleep(300);//反复刷新
                mainContainer.removeAll();

            }



        }
    }


    private JProgressBar initProgressBar(){

        //初始化加载条
        JProgressBar progressBar = new JProgressBar();
        progressBar.setBounds(0, this.getHeight() - 40,
                this.getWidth(), 40);

        //设置颜色
        {
            Color c;
            Color c1;
            if (color == 1) {
                c = new Color(0x636363);
                c1 = new Color(0xD1D1D1);
            } else {
                c = new Color(0xD1D1D1);
                c1 = new Color(0x636363);
            }
            progressBar.setForeground(c);
            progressBar.setBackground(c1);
        }

        //去除边框
        progressBar.setBorderPainted(false);
        progressBar.setStringPainted(true);
        progressBar.setFont(new Font("微软雅黑", Font.BOLD, 23));
        progressBar.setIndeterminate(false);

        this.getContentPane().add(progressBar);
        return progressBar;

    }
    private void initFrame() {


        //获取屏幕大小
        int screenWidth = new GetScreenSize().getScreenWidth();
        int screenHeight = new GetScreenSize().getScreenHeight();

        this.setSize(screenWidth, screenHeight);//大小

        this.setLocation(0, 0);

        this.setAlwaysOnTop(mainContainer.getAlwaysOnTop());//置顶


    }
}
