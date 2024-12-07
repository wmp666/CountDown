package com.bilibili.main;

import com.bilibili.frame.MainContainers;
import com.bilibili.frame.MainDialog;
import com.bilibili.frame.MainWindow;
import com.bilibili.information.GetScreenSize;
import com.bilibili.information.InformationLib;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
//import com.bilibili.frame.MainPro;

public class Main {
    static Window win;
    public static void main(String[] args) throws InterruptedException, IOException, ParseException {
        System.out.println("Hello world!");

        InformationLib inf = new InformationLib();



        MainContainers mainContainer = new MainContainers(
                inf.getTitle(),
                inf.getStartTime(),
                inf.getFrameType(),
                inf.isCanExit(),
                inf.isCanTop(),
                inf.getNumColor(),
                inf.getTitleColor(),
                inf.getBGColor());

        //获取屏幕大小
        int screenWidth = new GetScreenSize().getScreenWidth();
        int screenHeight = new GetScreenSize().getScreenHeight();

        buildWin(mainContainer);

    }

    public static void reload(InformationLib inf) throws IOException, InterruptedException {

        MainContainers mainContainer = new MainContainers(
                inf.getTitle(),
                inf.getStartTime(),
                inf.getFrameType(),
                inf.isCanExit(),
                inf.isCanTop(),
                inf.getNumColor(),
                inf.getTitleColor(),
                inf.getBGColor());

        win.setVisible(false);
        buildWin(mainContainer);
    }
    private static void buildWin(MainContainers mainContainer) throws IOException, InterruptedException {
        switch (mainContainer.getStartSytle()){
            case 0 ->{
                win = new MainDialog(mainContainer);
            }
            case 1 ->{
                win = new MainWindow(mainContainer,1);
            }
            case 2 ->{
                win = new MainWindow(mainContainer,0);
            }
            default -> JOptionPane.showMessageDialog(null,
                    "窗口类型缺失或错误",
                    "error",
                    JOptionPane.ERROR_MESSAGE);

        }
    }
}