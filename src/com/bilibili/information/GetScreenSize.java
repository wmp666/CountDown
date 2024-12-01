package com.bilibili.information;

import java.awt.*;

public class GetScreenSize {
    private int screenWidth;
    private int screenHeight;
    public GetScreenSize() {
        //设置屏幕大小
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.screenWidth = (int) screenSize.getWidth();
        this.screenHeight =(int) screenSize.getHeight();

    }


    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }
}
