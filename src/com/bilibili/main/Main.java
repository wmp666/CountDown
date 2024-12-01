package com.bilibili.main;

import com.bilibili.frame.MainContainers;
import com.bilibili.frame.MainDialog;
import com.bilibili.frame.MainFrame;
import com.bilibili.frame.MainWindow;
import com.bilibili.information.GetScreenSize;
import com.bilibili.information.InformationLib;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
//import com.bilibili.frame.MainPro;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException, ParseException {
        System.out.println("Hello world!");

        InformationLib inf = new InformationLib(new File("information.set"));



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

        switch (mainContainer.getStartSytle()){
            case 0 ->{
                new MainDialog(mainContainer);
            }
            case 1 ->{
                new MainFrame(mainContainer);
            }
            case 2 ->{
                new MainWindow(mainContainer,1);
            }
            case 3 ->{
                new MainWindow(mainContainer,0);
            }
            default -> JOptionPane.showMessageDialog(null,
                    "窗口类型缺失或错误",
                    "error",
                    JOptionPane.ERROR_MESSAGE);

        }

    }
}