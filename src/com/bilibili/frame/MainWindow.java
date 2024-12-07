package com.bilibili.frame;

import com.bilibili.information.GetScreenSize;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainWindow extends JWindow{

    MainContainers mainContainer = new MainContainers();

    private Color[] colors = new Color[3];

    public MainWindow(MainContainers MainContainer,int type) throws IOException, InterruptedException {


        this.mainContainer = MainContainer;

        colors[0] = mainContainer.getTimeColor();
        colors[1] = mainContainer.getTitleColor();
        colors[2] = mainContainer.getBackgroundColor();

        initFrame(type);

        //JProgressBar timeProgressBar = initProgressBar();

        this.setLayout(null);



        mainContainer.setLocation(0, 0);

        /*//关闭按钮
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
*/

        //设置背景
        {

            this.getContentPane().setBackground(colors[2]);
        }

        /*//标题标签
        {
            JLabel titleLabel = new JLabel(mainContainer.getTitle() + "倒计时");
            titleLabel.setBounds(0,
                    0,
                    300, 40);
            titleLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 23));

            //设置标题颜色
            {

                titleLabel.setForeground(colors[1]);
            }

            this.getContentPane().add(titleLabel);
        }*/

        this.getContentPane().add(mainContainer);

        this.setVisible(true);

        if (type == 0){
            mainContainer.setLocation((this.getWidth() - mainContainer.getWidth())/2,
                        (this.getHeight()- mainContainer.getHeight())/2);
        }

        //刷新

        TimeThread timeThread = new TimeThread(mainContainer);
        timeThread.start();
    }

    private void initFrame(int type) {


        //获取屏幕大小
        int screenWidth = new GetScreenSize().getScreenWidth();
        int screenHeight = new GetScreenSize().getScreenHeight();

        System.out.println(1);
        if (type == 0) {//全屏
            System.out.println(2);
            this.setSize(screenWidth, screenHeight);//大小

            this.setLocation(0, 0);

        }else{//小窗
            System.out.println(3);
            this.setLocation(screenWidth - mainContainer.getWidth(), 2);
            this.setSize(mainContainer.getWidth(),
                    mainContainer.getHeight());//大小
        }

        //this.setAlwaysOnTop(mainContainer.getAlwaysOnTop());//置顶


    }
}
