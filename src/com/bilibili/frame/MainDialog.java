package com.bilibili.frame;

import com.bilibili.information.GetScreenSize;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainDialog extends JDialog{

    MainContainers mainContainer = new MainContainers();

    public MainDialog(MainContainers mainContainer)  throws IOException, InterruptedException  {

        this.mainContainer = mainContainer;

        initFrame();

        this.getContentPane().add(mainContainer);

        this.setVisible(true);

        //刷新

        new TimeThread(mainContainer).start();
    }


    private void initFrame() {
        this.setTitle(mainContainer.getTitle() + "倒计时");//标题

        //Integer dayTime = (int)this.dayTime;

        this.setSize(mainContainer.getWidth() + 10,
                mainContainer.getHeight() + 40);//大小

        this.setAlwaysOnTop(mainContainer.getAlwaysOnTop());//置顶
        //this.setLocationRelativeTo(null);//窗口居中

        //获取屏幕大小
        int screenWidth = new GetScreenSize().getScreenWidth();
        int screenHeight = new GetScreenSize().getScreenHeight();

        this.setLocation(screenWidth - this.getWidth(), 0);

        this.setResizable(false);//设置窗口无法进行调节
        //设置任务栏的图标
        setIconImage(Toolkit.getDefaultToolkit().getImage("lib\\image\\icon.png"));

        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

    }

}
