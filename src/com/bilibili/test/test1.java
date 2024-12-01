package com.bilibili.test;

import java.awt.*;

public class test1 {
    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        System.out.println("width:" + width + "英寸");
        System.out.println("height:" + height + "英寸");


        int i = (int)((10 - 1)<0?1:2);
        System.out.println(i);
    }
}
