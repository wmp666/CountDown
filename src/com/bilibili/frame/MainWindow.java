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

        //JProgressBar timeProgressBar = initProgressBar();

        this.setLayout(null);



        mainContainer.setLocation((this.getWidth() - mainContainer.getWidth())/2,
                (this.getHeight() - mainContainer.getHeight())/2 - 40);

        //关闭按钮
        {
            JButton exitButton = new JButton("关闭");
            exitButton.setBounds(mainContainer.getX()
                    , mainContainer.getY() + mainContainer.getHeight() + 10,
                    mainContainer.getWidth(), 40);
            exitButton.setFont(new Font("Microsoft YaHei", Font.BOLD, 23));

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
            titleLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 23));

            //设置标题颜色
            {

                titleLabel.setForeground(colors[1]);
            }

            this.getContentPane().add(titleLabel);
        }

        this.getContentPane().add(mainContainer);

        this.setVisible(true);

        //刷新

        TimeThread timeThread = new TimeThread(mainContainer);
        timeThread.start();
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
