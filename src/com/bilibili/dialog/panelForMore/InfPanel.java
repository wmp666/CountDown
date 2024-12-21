package com.bilibili.dialog.panelForMore;

import javax.swing.*;

public class InfPanel extends JPanel {
    public InfPanel(int X,int Y,int width, int height) {

        JButton button = new JButton("关于");
        button.setBounds(0,0,100,30);

        this.add(button);

        this.setBounds(X,Y,width,height);
        this.setLayout(null);

    }

}
